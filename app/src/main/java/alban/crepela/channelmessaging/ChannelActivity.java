package alban.crepela.channelmessaging;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ChannelActivity extends AppCompatActivity {

    final private int PICTURE_REQUEST_CODE = 1;
    private ListView messages;
    public static HashMap<String, Bitmap> listBitmaps = new HashMap<>();



    private Button btnSend;
    private EditText txtSend;
    private FloatingActionButton btnPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);


        messages = (ListView) findViewById(R.id.listViewMessages);

        SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
        String accesstoken = settings.getString("accesstoken","");

        final HashMap<String, String> params = new HashMap<String, String>();
        params.put("accesstoken",accesstoken);
        params.put("channelid", getIntent().getStringExtra("channelID"));
        final Handler handler = new Handler();

        final Runnable r = new Runnable() {
            public void run() {


                Connexion connexion = new Connexion(getApplicationContext(), params, "http://www.raphaelbischof.fr/messaging/?function=getmessages");


                connexion.setOnDownloadCompleteListener(new OnDownloadCompleteListener() {
                    @Override
                    public void onDownloadCompleted(String content) {
                        //déserialisation
                        Gson gson = new Gson();
                        MessagesContainer obj = gson.fromJson(content, MessagesContainer.class);
                        // save index and top position

                        int index = messages.getFirstVisiblePosition();

                        View v = messages.getChildAt(0);
                        int top = (v == null) ? 0 : (v.getTop() - messages.getPaddingTop());

                        MessageArrayAdapter adapter = new MessageArrayAdapter(getApplicationContext(), obj.getMessages());
                        messages.setAdapter(adapter);

                        // restore index and position

                        messages.setSelectionFromTop(index, top);

                    }
                });
                connexion.execute();



                handler.postDelayed(this, 1000);
            }
        };

        handler.postDelayed(r, 1000);


        messages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Message message = (Message)messages.getItemAtPosition(position);
                new AlertDialog.Builder(ChannelActivity.this)
                        .setTitle("Ajouter en ami")
                        .setMessage("Voulez vous ajouter "+message.getName()+ " en ami ?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                UserDataSource db = new UserDataSource(ChannelActivity.this);
                                db.open();
                                Friend friend = db.createFriend(message.getUserID(), message.getName(), message.getImageUrl());
                                System.out.println(friend);
                                db.close();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();

            }
        });




        btnSend = (Button)findViewById(R.id.btnSend);
        txtSend = (EditText)findViewById(R.id.txtSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
                String accesstoken = settings.getString("accesstoken","");

                HashMap<String, String> params = new HashMap<String, String>();
                params.put("accesstoken",accesstoken);
                params.put("channelid", getIntent().getStringExtra("channelID"));
                params.put("message", txtSend.getText().toString());

                Connexion connexion = new Connexion(getApplicationContext(), params, "http://www.raphaelbischof.fr/messaging/?function=sendmessage");
                connexion.execute();

                txtSend.setText("");

            }
        });





        btnPhoto = (FloatingActionButton)findViewById(R.id.btnGPS);
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File test = new File(Environment.getExternalStorageDirectory()+"/img.jpg");
                try {
                    test.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
// Android a depuis Android Nougat besoin d'un provider pour donner l'accès à un répertoire pour une autre app, cf : http://stackoverflow.com/questions/38200282/android-os-fileuriexposedexception-file-storage-emulated-0-test-txt-exposed
                Uri uri = FileProvider.getUriForFile(ChannelActivity.this, ChannelActivity.this.getApplicationContext().getPackageName() + ".provider", test);;
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //Création de l’appelà l’application appareil photo pour récupérer une image
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri); //Emplacement de l’image stockée
                startActivityForResult(intent, PICTURE_REQUEST_CODE);


            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println(data);
        switch (requestCode)
        {


            case PICTURE_REQUEST_CODE :

                SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_NAME, 0);

                try {
                    resizeFile(new File(Environment.getExternalStorageDirectory()+"/img.jpg"), ChannelActivity.this);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                List<NameValuePair> values = new ArrayList<NameValuePair>();
                values.add(new BasicNameValuePair("accesstoken",settings.getString("accesstoken","")));
                values.add(new BasicNameValuePair("channelid",getIntent().getStringExtra("channelID")));

                UploadFileToServer upload = new UploadFileToServer(ChannelActivity.this, new File(Environment.getExternalStorageDirectory()+"/img.jpg").getPath(), values, new UploadFileToServer.OnUploadFileListener() {
                    @Override
                    public void onResponse(String result) {
                        Toast.makeText(getApplicationContext(),"reponse",Toast.LENGTH_LONG);
                    }

                    @Override
                    public void onFailed(IOException error) {
                        Toast.makeText(getApplicationContext(),"failde",Toast.LENGTH_LONG);
                    }
                });
                upload.execute();

        }

    }


    private void resizeFile(File f,Context context) throws IOException {
        //Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(new FileInputStream(f),null,o);

        //The new size we want to scale to
        final int REQUIRED_SIZE=400;

        //Find the correct scale value. It should be the power of 2.
        int scale=1;
        while(o.outWidth/scale/2>=REQUIRED_SIZE && o.outHeight/scale/2>=REQUIRED_SIZE)
            scale*=2;

        //Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize=scale;
        Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        int i = getCameraPhotoOrientation(context, Uri.fromFile(f),f.getAbsolutePath());
        if (o.outWidth>o.outHeight)
        {
            Matrix matrix = new Matrix();
            matrix.postRotate(i); // anti-clockwise by 90 degrees
            bitmap = Bitmap.createBitmap(bitmap , 0, 0, bitmap .getWidth(), bitmap .getHeight(), matrix, true);
        }
        try {
            f.delete();
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getCameraPhotoOrientation(Context context, Uri imageUri, String imagePath) throws IOException {
        int rotate = 0;
        context.getContentResolver().notifyChange(imageUri, null);
        File imageFile = new File(imagePath);
        ExifInterface exif = new ExifInterface(
                imageFile.getAbsolutePath());
        int orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_270:
                rotate = 270;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                rotate = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                rotate = 90;
                break;
        }
        return rotate;
    }
}

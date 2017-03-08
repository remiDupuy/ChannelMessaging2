package alban.crepela.channelmessaging.fragments;

import android.support.v4.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import alban.crepela.channelmessaging.ChannelActivity;
import alban.crepela.channelmessaging.Connexion;
import alban.crepela.channelmessaging.Friend;
import alban.crepela.channelmessaging.LoginActivity;
import alban.crepela.channelmessaging.Message;
import alban.crepela.channelmessaging.MessageArrayAdapter;
import alban.crepela.channelmessaging.MessagesContainer;
import alban.crepela.channelmessaging.OnDownloadCompleteListener;
import alban.crepela.channelmessaging.R;
import alban.crepela.channelmessaging.UserDataSource;

public class MessageFragment extends Fragment {

    final private int PICTURE_REQUEST_CODE = 1;
    private ListView messages;
    public static HashMap<String, Bitmap> listBitmaps = new HashMap<>();
    public String channelid;


    private Button btnSend;
    private EditText txtSend;
    private FloatingActionButton btnPhoto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_message_fragment, container);

        if(getActivity().getIntent().getStringExtra("channelID") == null) {
            channelid = "1";
        } else {
            channelid = getActivity().getIntent().getStringExtra("channelID");
        }

        messages = (ListView)v.findViewById(R.id.listViewMessages);

        SharedPreferences settings = getActivity().getSharedPreferences(LoginActivity.PREFS_NAME, 0);
        String accesstoken = settings.getString("accesstoken","");

        final HashMap<String, String> params = new HashMap<>();
        params.put("accesstoken",accesstoken);



        final Handler handler = new Handler();

        final Runnable r = new Runnable() {
            public void run() {
                if(getActivity() != null) {
                    params.put("channelid", channelid);
                    Connexion connexion = new Connexion(getActivity().getApplicationContext(), params, "http://www.raphaelbischof.fr/messaging/?function=getmessages");


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

                            if(getActivity() != null) {
                                MessageArrayAdapter adapter = new MessageArrayAdapter(getActivity().getApplicationContext(), obj.getMessages());
                                messages.setAdapter(adapter);
                            }


                            // restore index and position

                            messages.setSelectionFromTop(index, top);

                        }
                    });
                    connexion.execute();

                    handler.postDelayed(this, 1000);
                }







            }
        };

        handler.postDelayed(r, 1000);


        messages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Message message = (Message)messages.getItemAtPosition(position);
                new AlertDialog.Builder(getActivity())
                        .setTitle("Ajouter en ami")
                        .setMessage("Voulez vous ajouter "+message.getName()+ " en ami ?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                UserDataSource db = new UserDataSource(getActivity());
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




        btnSend = (Button)v.findViewById(R.id.btnSend);
        txtSend = (EditText)v.findViewById(R.id.txtSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences settings = getActivity().getSharedPreferences(LoginActivity.PREFS_NAME, 0);
                String accesstoken = settings.getString("accesstoken","");

                HashMap<String, String> params = new HashMap<String, String>();
                params.put("accesstoken",accesstoken);
                params.put("channelid", channelid);
                params.put("message", txtSend.getText().toString());

                Connexion connexion = new Connexion(getActivity().getApplicationContext(), params, "http://www.raphaelbischof.fr/messaging/?function=sendmessage");
                connexion.execute();

                txtSend.setText("");

            }
        });





        btnPhoto = (FloatingActionButton)v.findViewById(R.id.btnGPS);
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
                Uri uri = FileProvider.getUriForFile(getActivity(), getActivity().getApplicationContext().getPackageName() + ".provider", test);;
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //Création de l’appelà l’application appareil photo pour récupérer une image
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri); //Emplacement de l’image stockée
                startActivityForResult(intent, PICTURE_REQUEST_CODE);


            }
        });



        return v;

    }

}

package alban.crepela.channelmessaging;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;


public class ChannelActivity extends AppCompatActivity {

    private ListView messages;
    public static HashMap<String, Bitmap> listBitmaps = new HashMap<>();


    private Button btnSend;
    private EditText txtSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);


        final Handler handler = new Handler();

        final Runnable r = new Runnable() {
            public void run() {

                messages = (ListView) findViewById(R.id.listViewMessages);

                SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
                String accesstoken = settings.getString("accesstoken","");

                HashMap<String, String> params = new HashMap<String, String>();
                params.put("accesstoken",accesstoken);
                params.put("channelid", getIntent().getStringExtra("channelID"));

                Connexion connexion = new Connexion(getApplicationContext(), params, "http://www.raphaelbischof.fr/messaging/?function=getmessages");


                connexion.setOnDownloadCompleteListener(new OnDownloadCompleteListener() {
                    @Override
                    public void onDownloadCompleted(String content) {
                        //déserialisation
                        Gson gson = new Gson();
                        MessagesContainer obj = gson.fromJson(content, MessagesContainer.class);

                        MessageArrayAdapter adapter = new MessageArrayAdapter(getApplicationContext(), obj.getMessages());
                        messages.setAdapter(adapter);

                    }
                });

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



                connexion.execute();


                handler.postDelayed(this, 1000);
            }
        };

        handler.postDelayed(r, 1000);





    }
}

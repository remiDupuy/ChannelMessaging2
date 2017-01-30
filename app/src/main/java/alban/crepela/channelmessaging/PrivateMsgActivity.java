package alban.crepela.channelmessaging;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PrivateMsgActivity extends AppCompatActivity {

    public static HashMap<String, Bitmap> listBitmapsMP = new HashMap<>();
    private ListView privateMsgs;
    private Button btnSendMP;
    private EditText txtSendMP;
    private List<PrivateMsg> messagesBackUp = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_msg);

        privateMsgs = (ListView) findViewById(R.id.listViewMp);


        final Handler handler = new Handler();

        final Runnable r = new Runnable() {
            public void run() {


                SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
                String accesstoken = settings.getString("accesstoken", "");

                HashMap<String, String> params = new HashMap<String, String>();
                params.put("accesstoken", accesstoken);

                params.put("userid", getIntent().getStringExtra("userid"));

                Connexion connexion = new Connexion(getApplicationContext(), params, "http://www.raphaelbischof.fr/messaging/?function=getmessages");
                final PrivateMsgArrayAdapter adapter = new PrivateMsgArrayAdapter(getApplicationContext(), messagesBackUp);
                privateMsgs.setAdapter(adapter);
                connexion.setOnDownloadCompleteListener(new OnDownloadCompleteListener() {
                    @Override
                    public void onDownloadCompleted(String result) {
                        //déserialisation
                        Gson gson = new Gson();
                        PrivateMsgContainer obj = gson.fromJson(result, PrivateMsgContainer.class);

                        if(!obj.getMessages().equals(messagesBackUp)) {
                            adapter.clear();
                            adapter.addAll(obj.getMessages());
                            adapter.notifyDataSetChanged();
                        }

                    }
                });

                connexion.execute();

                btnSendMP = (Button) findViewById(R.id.btnSendMp);
                txtSendMP = (EditText) findViewById(R.id.txtSendMp);

                btnSendMP.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
                        String accesstoken = settings.getString("accesstoken", "");

                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("accesstoken", accesstoken);

                        params.put("userid", getIntent().getStringExtra("userid"));

                        params.put("message", txtSendMP.getText().toString());

                        Connexion connect = new Connexion(getApplicationContext(), params, "http://www.raphaelbischof.fr/messaging/?function=sendmessage");

                        connect.execute();
                    }
                });
                handler.postDelayed(this, 1000);
            }
        };

        handler.postDelayed(r, 1000);
    }
}

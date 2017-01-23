package alban.crepela.channelmessaging;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.HashMap;

public class ChannelActivity extends AppCompatActivity {

    private ListView messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);

        messages = (ListView) findViewById(R.id.listViewMessages);

        SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
        String accesstoken = settings.getString("accesstoken","");

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("accesstoken",accesstoken);
        params.put("channelid", getIntent().getStringExtra("channelId"));

        Connexion connexion = new Connexion(getApplicationContext(), params, "http://www.raphaelbischof.fr/messaging/?function=getmessages");
        connexion.execute();

        connexion.setOnDownloadCompleteListener(new OnDownloadCompleteListener() {
            @Override
            public void onDownloadCompleted(String content) {
                System.out.println(content);

                //déserialisation
                Gson gson = new Gson();
                MessagesContainer obj = gson.fromJson(content, MessagesContainer.class);


                MessageArrayAdapter adapter = new MessageArrayAdapter(getApplicationContext(), obj.getMessages());
                messages.setAdapter(adapter);
            }
        });
    }
}

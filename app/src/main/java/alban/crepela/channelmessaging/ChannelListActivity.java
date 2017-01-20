package alban.crepela.channelmessaging;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;

public class ChannelListActivity extends AppCompatActivity {
    private ListView channels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_list);

        channels = (ListView) findViewById(R.id.listViewChannels);

        SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
        String accesstoken = settings.getString("accesstoken","");

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("accesstoken",accesstoken);

        Connexion connexion = new Connexion(getApplicationContext(), params, "http://www.raphaelbischof.fr/messaging/?function=getchannels");
        connexion.execute();

        connexion.setOnDownloadCompleteListener(new  OnDownloadCompleteListener() {
            @Override
            public void onDownloadCompleted(String result) {
                System.out.println(result);

                //déserialisation
                Gson gson = new Gson();
                ChannelsContainer obj = gson.fromJson(result, ChannelsContainer.class);

                System.out.println(obj);

                channels.setAdapter(new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, obj.getChannels()));
                /*
                if(obj.getResponse().equals("Ok")){
                    Toast.makeText(getApplicationContext(), "Connecté", Toast.LENGTH_SHORT).show();
                    Intent myIntent = new Intent(getApplicationContext(),ChannelListActivity.class);
                    startActivity(myIntent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Erreur de connexion", Toast.LENGTH_SHORT).show();
                }
                */
            }
        });
    }
}

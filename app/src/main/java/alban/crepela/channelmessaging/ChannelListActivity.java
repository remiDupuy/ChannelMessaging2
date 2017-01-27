package alban.crepela.channelmessaging;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;

public class ChannelListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView channels;
    private Button btnAmis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_list);

        channels = (ListView) findViewById(R.id.listViewChannels);
        channels.setOnItemClickListener(this);

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


                ChannelArrayAdapter adapter = new ChannelArrayAdapter(getApplicationContext(), obj.getChannels());
                channels.setAdapter(adapter);

            }
        });

        btnAmis = (Button)findViewById(R.id.btnAmis);

        btnAmis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent friendIntent = new Intent(getApplicationContext(), AmisListActivity.class);
                startActivity(friendIntent);
            }
        });


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Channel channel = (Channel)channels.getItemAtPosition(position);
        Intent intentMsg = new Intent(getApplicationContext(),ChannelActivity.class);
        intentMsg.putExtra("channelID", channel.getChannelID());
        startActivity(intentMsg);
    }



}

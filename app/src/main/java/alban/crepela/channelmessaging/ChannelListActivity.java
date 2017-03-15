package alban.crepela.channelmessaging;

import android.graphics.Color;
import android.support.v4.app.Fragment;
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

import alban.crepela.channelmessaging.fragments.ChannelListFragment;
import alban.crepela.channelmessaging.fragments.MessageFragment;

public class ChannelListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_list);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        ChannelListFragment frag1 = (ChannelListFragment)getSupportFragmentManager().findFragmentById(R.id.fragmentChannelList);
        MessageFragment frag2 = (MessageFragment)getSupportFragmentManager().findFragmentById(R.id.fragmentMessages);
        if(frag2 == null ||!frag2.isInLayout()) {
            Channel channel = (Channel)frag1.channels.getItemAtPosition(position);
            Intent intentMsg = new Intent(getApplicationContext(),ChannelActivity.class);
            intentMsg.putExtra("channelID", channel.getChannelID());
            startActivity(intentMsg);
        } else {
            Channel channel = (Channel)frag1.channels.getItemAtPosition(position);
            frag2.channelid = channel.getChannelID();
        }

    }



}

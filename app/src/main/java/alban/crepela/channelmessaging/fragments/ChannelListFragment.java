package alban.crepela.channelmessaging.fragments;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

import alban.crepela.channelmessaging.AmisListActivity;
import alban.crepela.channelmessaging.Channel;
import alban.crepela.channelmessaging.ChannelActivity;
import alban.crepela.channelmessaging.ChannelArrayAdapter;
import alban.crepela.channelmessaging.ChannelListActivity;
import alban.crepela.channelmessaging.ChannelsContainer;
import alban.crepela.channelmessaging.Connexion;
import alban.crepela.channelmessaging.LoginActivity;
import alban.crepela.channelmessaging.OnDownloadCompleteListener;
import alban.crepela.channelmessaging.R;

/**
 * Created by dupuyr on 27/02/2017.
 */
public class ChannelListFragment extends Fragment {
    public ListView channels;
    private Button btnAmis;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.channellistfragment, container);



        channels = (ListView)v.findViewById(R.id.listViewChannelsFragment);
        channels.setOnItemClickListener((ChannelListActivity)getActivity());

        SharedPreferences settings = getActivity().getSharedPreferences(LoginActivity.PREFS_NAME, 0);
        String accesstoken = settings.getString("accesstoken","");

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("accesstoken",accesstoken);

        Connexion connexion = new Connexion(getActivity().getApplicationContext(), params, "http://www.raphaelbischof.fr/messaging/?function=getchannels");
        connexion.execute();

        connexion.setOnDownloadCompleteListener(new  OnDownloadCompleteListener() {
            @Override
            public void onDownloadCompleted(String result) {
                System.out.println(result);

                //déserialisation
                Gson gson = new Gson();
                ChannelsContainer obj = gson.fromJson(result, ChannelsContainer.class);


                ChannelArrayAdapter adapter = new ChannelArrayAdapter(getActivity().getApplicationContext(), obj.getChannels());
                channels.setAdapter(adapter);

            }
        });

        btnAmis = (Button)v.findViewById(R.id.btnAmis);

        btnAmis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent friendIntent = new Intent(getActivity().getApplicationContext(), AmisListActivity.class);
                startActivity(friendIntent);
            }
        });


        return v;

    }

}

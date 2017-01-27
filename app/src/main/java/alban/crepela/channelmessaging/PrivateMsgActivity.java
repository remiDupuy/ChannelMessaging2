package alban.crepela.channelmessaging;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;

public class PrivateMsgActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_msg);

        SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
        String accesstoken = settings.getString("accesstoken","");

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("accesstoken",accesstoken);
        params.put("userid", getIntent().getStringExtra("userid"));

        Connexion connexion = new Connexion(getApplicationContext(), params, "http://www.raphaelbischof.fr/messaging/?function=getmessages");

        connexion.setOnDownloadCompleteListener(new  OnDownloadCompleteListener() {
            @Override
            public void onDownloadCompleted(String result) {
                //déserialisation
                Gson gson = new Gson();
                PrivateMsgContainer obj = gson.fromJson(result, PrivateMsgContainer.class);


            }
        });
        connexion.execute();
    }
}

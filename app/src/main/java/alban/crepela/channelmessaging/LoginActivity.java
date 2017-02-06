package alban.crepela.channelmessaging;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    private EditText id;
    private EditText mdp;
    private Button btnValider;
    private FloatingActionButton btnGPS;

    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnValider = (Button) findViewById(R.id.btnValider);
        id = (EditText) findViewById(R.id.editTextId);
        mdp = (EditText) findViewById(R.id.editTextMdp);

        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("username",id.getText().toString());
                params.put("password",mdp.getText().toString());

                Connexion connexion = new Connexion(getApplicationContext(), params, "http://www.raphaelbischof.fr/messaging/?function=connect");

                connexion.setOnDownloadCompleteListener(new  OnDownloadCompleteListener() {
                    @Override
                    public void onDownloadCompleted(String result) {
                        //déserialisation
                        Gson gson = new Gson();
                        Reponse obj = gson.fromJson(result, Reponse.class);

                        if(obj.getResponse().equals("Ok")){
                            Toast.makeText(getApplicationContext(), "Connecté", Toast.LENGTH_SHORT).show();

                            //Shared preferences
                            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString("accesstoken", obj.getAccesstoken());
                            // Commit the edits!
                            editor.commit();

                            Intent myIntent = new Intent(getApplicationContext(),ChannelListActivity.class);
                            startActivity(myIntent);
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Erreur de connexion", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                connexion.execute();
            }
        });

        btnGPS = (FloatingActionButton)findViewById(R.id.btnGPS);
        btnGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), GPSActivity.class);
                startActivity(myIntent);
            }
        });

    }
}

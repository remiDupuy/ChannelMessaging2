package alban.crepela.channelmessaging;

import android.app.ActivityOptions;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.gson.Gson;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    private EditText id;
    private EditText mdp;
    private Button btnValider;
    private FloatingActionButton btnGPS;
    private ImageView mIvLogo;
    private AVLoadingIndicatorView avi;


    public static final String PREFS_NAME = "MyPrefsFile";
    private Handler mHandlerTada;
    private int mShortDelay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnValider = (Button) findViewById(R.id.btnValider);
        id = (EditText) findViewById(R.id.editTextId);
        mdp = (EditText) findViewById(R.id.editTextMdp);
        mIvLogo = (ImageView) findViewById(R.id.mIvLogo);
        avi = (AVLoadingIndicatorView)findViewById(R.id.avi);

        avi.hide();

        mHandlerTada = new Handler(); // android.os.handler
        mShortDelay = 4000; //milliseconds

        mHandlerTada.postDelayed(new Runnable(){
            public void run(){

                YoYo.with(Techniques.Pulse)
                        .duration(2000)
                        .repeat(1)
                        .playOn(findViewById(R.id.mIvLogo));


                mHandlerTada.postDelayed(this, mShortDelay);
            }
        }, mShortDelay);

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
                        btnValider.setVisibility(View.INVISIBLE);
                        avi.smoothToShow();
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


                            Intent loginIntent = new Intent(LoginActivity.this, ChannelListActivity.class);
                            startActivity(loginIntent, ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, mIvLogo, "logo").toBundle());


                        }
                        else{
                            btnValider.setVisibility(View.VISIBLE);
                            avi.hide();
                            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.llBackground), "Erreur", Snackbar.LENGTH_SHORT);
                            mySnackbar.setAction("Réessayer", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                            mySnackbar.show();
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

package alban.crepela.channelmessaging;

import android.animation.Animator;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.dynamitechetan.flowinggradient.FlowingGradientClass;
import com.google.gson.Gson;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.HashMap;
import java.util.Random;

public class LoginActivity extends AppCompatActivity implements OnDownloadCompleteListener {
    private EditText id;
    private EditText mdp;
    private Button btnValider;
    private FloatingActionButton btnGPS;
    private ImageView mIvLogo;
    private AVLoadingIndicatorView avi;

    private static final String[] explainStringArray = {
            "Bazinga",
            "Mouahahah",
            "It's gonna be legen..",
            "..wait for it..",
            "..dary"
    };


    public static final String PREFS_NAME = "MyPrefsFile";
    private Handler mHandlerTada;
    private int mShortDelay;
    private TextView textDefil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnValider = (Button) findViewById(R.id.btnValider);
        id = (EditText) findViewById(R.id.editTextId);
        mdp = (EditText) findViewById(R.id.editTextMdp);
        mIvLogo = (ImageView) findViewById(R.id.mIvLogo);
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        textDefil = (TextView)findViewById(R.id.textDefil);

        avi.hide();

        FlowingGradientClass grad = new FlowingGradientClass();
        grad.setBackgroundResource(R.drawable.translate)
                .onLinearLayout((LinearLayout)findViewById(R.id.llBackground))
                .setTransitionDuration(4000)
                .start();

        mHandlerTada = new Handler(); // android.os.handler
        mShortDelay = 4000; //milliseconds

        mHandlerTada.postDelayed(new Runnable() {
            public void run() {

                YoYo.with(Techniques.Pulse)
                        .duration(2000)
                        .repeat(1)
                        .playOn(findViewById(R.id.mIvLogo));


                YoYo.with(Techniques.SlideOutRight).duration(750).withListener(new Animator.AnimatorListener() {
                    public void onAnimationEnd(Animator animation) {
                        textDefil.setText(explainStringArray[new Random().nextInt(explainStringArray.length)]);
                        YoYo.with(Techniques.SlideInLeft).duration(750).playOn(textDefil);
                    }
                    public void onAnimationStart(Animator animation) { }
                    public void onAnimationCancel(Animator animation) { }
                    public void onAnimationRepeat(Animator animation) { }

                }).playOn(textDefil);


                mHandlerTada.postDelayed(this, mShortDelay);
            }
        }, mShortDelay);

        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnValider.setVisibility(View.INVISIBLE);
                avi.show();
                connect();
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


    @Override
    public void onDownloadCompleted(String result) {

        //déserialisation
        Gson gson = new Gson();
        Reponse obj = gson.fromJson(result, Reponse.class);

        if(obj.getResponse().equals("Ok")){

            //Shared preferences
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("accesstoken", obj.getAccesstoken());
            // Commit the edits!
            editor.commit();


            Intent loginIntent = new Intent(LoginActivity.this, ChannelListActivity.class);
            startActivity(loginIntent, ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, mIvLogo, "logo").toBundle());
            btnValider.setVisibility(View.VISIBLE);
            avi.hide();
        }
        else{
            btnValider.setVisibility(View.VISIBLE);
            avi.hide();
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.llBackground), "Erreur", Snackbar.LENGTH_SHORT);
            mySnackbar.setAction("Réessayer", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    connect();
                }
            });
            mySnackbar.show();
        }
    }




    @Override
    protected void onResume() {
        super.onResume();
        btnValider.setVisibility(View.VISIBLE);
        avi.hide();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public void connect() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username",id.getText().toString());
        params.put("password",mdp.getText().toString());

        final Connexion connexion = new Connexion(getApplicationContext(), params, "http://www.raphaelbischof.fr/messaging/?function=connect");
        connexion.setOnDownloadCompleteListener(LoginActivity.this);
        connexion.execute();
    }


}

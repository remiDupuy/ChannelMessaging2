package alban.crepela.channelmessaging;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    private EditText id;
    private EditText mdp;
    private Button btnValider;
    private Connexion connexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnValider = (Button) findViewById(R.id.btnValider);
        id = (EditText) findViewById(R.id.editTextId);
        mdp = (EditText) findViewById(R.id.editTextMdp);
        connexion = new Connexion(getApplicationContext());

        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connexion.execute(id.getText().toString(),mdp.getText().toString());
                connexion.setOnDownloadCompleteListener(new  OnDownloadCompleteListener() {
                    @Override
                    public void onDownloadCompleted(String news) {
                        System.out.println("hihi");
                    }
                });
            }
        });
    }
}

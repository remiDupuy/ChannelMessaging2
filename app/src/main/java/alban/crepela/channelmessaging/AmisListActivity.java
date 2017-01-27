package alban.crepela.channelmessaging;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class AmisListActivity extends AppCompatActivity {

    private GridView gridViewFriends;
    private List<Friend> listFriends;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amis_list);

        UserDataSource db = new UserDataSource(AmisListActivity.this);
        db.open();
        listFriends = db.getAllHommes();
        db.close();
        gridViewFriends = (GridView)findViewById(R.id.gridView);
        gridViewFriends.setAdapter(new FriendArrayAdapter(getApplicationContext(), listFriends));

    }
}

package alban.crepela.channelmessaging;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

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

        gridViewFriends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Friend friend = (Friend)gridViewFriends.getItemAtPosition(position);
                Intent mpIntent = new Intent(getApplicationContext(), PrivateMsgActivity.class);
                mpIntent.putExtra("userid", Integer.toString(friend.getId()));
                startActivity(mpIntent);
            }
        });

    }
}

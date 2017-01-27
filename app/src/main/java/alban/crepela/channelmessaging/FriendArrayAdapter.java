package alban.crepela.channelmessaging;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dupuyr on 27/01/2017.
 */
public class FriendArrayAdapter extends ArrayAdapter<Friend> {

    public FriendArrayAdapter(Context context, List<Friend> friends) {
        super(context, 0, friends);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Friend friend = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.friends_list_row, parent, false);
        }
        // Lookup view for data population
        TextView txtViewName = (TextView) convertView.findViewById(R.id.textViewName);
        ImageView imgView = (ImageView) convertView.findViewById(R.id.imageViewFriend);

        // Populate the data into the template view using the data object
        txtViewName.setText(friend.getName());


        new DownloadImageTask(imgView).execute(friend.getImgUrl());
        // Return the completed view to render on screen
        return convertView;


    }

}

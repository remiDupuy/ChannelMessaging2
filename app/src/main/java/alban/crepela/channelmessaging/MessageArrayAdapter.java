package alban.crepela.channelmessaging;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dupuyr on 23/01/2017.
 */
public class MessageArrayAdapter extends ArrayAdapter<Message>{

    public MessageArrayAdapter(Context context, List<Message> messages) {
        super(context, 0, messages);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Message message = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.messages_list_row, parent, false);
        }
        // Lookup view for data population
        TextView txtMsg = (TextView) convertView.findViewById(R.id.textViewMessage);
        TextView txtDate = (TextView) convertView.findViewById(R.id.textViewDate);
        ImageView img = (ImageView)convertView.findViewById(R.id.imageView);

        // Populate the data into the template view using the data object
        txtMsg.setText(message.getUserID()+" : "+message.getMessage());
        txtDate.setText(message.getDate().toString() + " - " + message.getName());

        if(ChannelActivity.listBitmaps.containsKey((message.getImageUrl()))) {
            Bitmap imgDl = ChannelActivity.listBitmaps.get(message.getImageUrl());
            img.setImageBitmap(GetRoundedCornerBitmap.rounded(imgDl));
        }
        else
            new DownloadImageTask(img).execute(message.getImageUrl());
        // Return the completed view to render on screen
        return convertView;


    }
}

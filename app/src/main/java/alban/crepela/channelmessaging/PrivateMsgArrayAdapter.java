package alban.crepela.channelmessaging;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dupuyr on 30/01/2017.
 */
public class PrivateMsgArrayAdapter extends ArrayAdapter<PrivateMsg>{

    public PrivateMsgArrayAdapter(Context context, List<PrivateMsg> privateMsgs) {
        super(context, 0, privateMsgs);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        PrivateMsg privateMsg = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null && privateMsg.getSendbyme() == 0) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.privatemsg_list_row, parent, false);
        } else if(convertView == null && privateMsg.getSendbyme() == 1) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.privatemsg_list_row_right, parent, false);
        }
        // Lookup view for data population
        TextView txtMsg = (TextView) convertView.findViewById(R.id.txtMP);
        TextView txtDest = (TextView) convertView.findViewById(R.id.txtDest);
        ImageView img = (ImageView)convertView.findViewById(R.id.imageViewMP);

        // Populate the data into the template view using the data object
        txtMsg.setText(privateMsg.getMessage());
        txtDest.setText(privateMsg.getUsername());

        if(privateMsg.getEverRead().equals("0")) {
            txtMsg.setTypeface(Typeface.DEFAULT_BOLD);
            txtMsg.setTextColor(0);
        }

        if(ChannelActivity.listBitmaps.containsKey((privateMsg.getImageUrl()))) {
            Bitmap imgDl = ChannelActivity.listBitmaps.get(privateMsg.getImageUrl());
            img.setImageBitmap(GetRoundedCornerBitmap.rounded(imgDl));
        }
        else
            new DownloadImageTask(img).execute(privateMsg.getImageUrl());
        // Return the completed view to render on screen
        return convertView;


    }

}

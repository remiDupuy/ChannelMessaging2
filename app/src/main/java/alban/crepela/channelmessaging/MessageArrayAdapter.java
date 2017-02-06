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
 * Created by dupuyr on 23/01/2017.
 */
public class MessageArrayAdapter extends ArrayAdapter<Message>{

    public MessageArrayAdapter(Context context, List<Message> messages) {
        super(context, 0, messages);
    }

    @Override
    public int getItemViewType(int position) {
        Message message = getItem(position);
        if(message.getMessageImageUrl().equals(""))
            return R.layout.messages_list_row;
        else
            return R.layout.messages_img_list_row;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Message message = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null || ((MessageArrayAdapterViewHolder) convertView.getTag()).viewType != getItemViewType(position)) {
            convertView = generateRowOfType(getItemViewType(position), parent);
        }

        MessageArrayAdapterViewHolder viewHolder = (MessageArrayAdapterViewHolder) convertView.getTag();
        if (viewHolder.viewType == R.layout.messages_img_list_row) {
            ImageMessageViewHolder messageViewHolder = (ImageMessageViewHolder) viewHolder;
            // Populate the data into the template view using the data object
            messageViewHolder.txtName.setText(message.getName());
            messageViewHolder.txtDate.setText(message.getDate());

            if(ChannelActivity.listBitmaps.containsKey((message.getMessageImageUrl()))) {
                Bitmap imgDl = ChannelActivity.listBitmaps.get(message.getMessageImageUrl());
                messageViewHolder.imgView.setImageBitmap(imgDl);
            }
            else
                new DownloadImageTask(messageViewHolder.imgView).execute(message.getMessageImageUrl());
        }

        if(viewHolder.viewType == R.layout.messages_list_row) {
            ClassicMessageViewHolder messageViewHolder = (ClassicMessageViewHolder) viewHolder;
            messageViewHolder.txtMsg.setText(message.getUserID()+" : "+message.getMessage());
            messageViewHolder.txtDate.setText(message.getDate().toString() + " - " + message.getName());

            if(ChannelActivity.listBitmaps.containsKey((message.getImageUrl()))) {
                Bitmap imgDl = ChannelActivity.listBitmaps.get(message.getImageUrl());
                messageViewHolder.img.setImageBitmap(GetRoundedCornerBitmap.rounded(imgDl));
            }
            else
                new DownloadImageTask(messageViewHolder.img).execute(message.getImageUrl());
        }


        // Return the completed view to render on screen
        return convertView;


    }

    private View generateRowOfType(int itemViewType, ViewGroup parent) {
        if(itemViewType == R.layout.messages_list_row) {
            View convertView = LayoutInflater.from(getContext()).inflate(R.layout.messages_list_row, parent, false);
            TextView txtName = (TextView)convertView.findViewById(R.id.textViewMessage);
            TextView txtDate = (TextView)convertView.findViewById(R.id.textViewDate);
            ImageView imgView = (ImageView)convertView.findViewById(R.id.ImageView);
            convertView.setTag(new ClassicMessageViewHolder(itemViewType, txtName, txtDate, imgView));
            return convertView;
        }
        else {
            View convertView = LayoutInflater.from(getContext()).inflate(R.layout.messages_img_list_row, parent, false);
            TextView txtName = (TextView)convertView.findViewById(R.id.textViewName);
            TextView txtDate = (TextView)convertView.findViewById(R.id.textViewDate2);
            ImageView imgView = (ImageView)convertView.findViewById(R.id.imageViewPhoto);
            convertView.setTag(new ImageMessageViewHolder(itemViewType, txtName, txtDate, imgView));
            return convertView;
        }
    }

    public class MessageArrayAdapterViewHolder {
        private final int viewType;

        public MessageArrayAdapterViewHolder(int viewType) {
            this.viewType = viewType;
        }
    }

    public class ImageMessageViewHolder extends MessageArrayAdapterViewHolder {
        public final TextView txtName;
        public final TextView txtDate;
        public final ImageView imgView;
        public ImageMessageViewHolder(int viewType, TextView txtName, TextView txtDate, ImageView imgView) {
            super(viewType);
            this.txtName = txtName;
            this.txtDate = txtDate;
            this.imgView = imgView;
        }
    }
    public class ClassicMessageViewHolder extends MessageArrayAdapterViewHolder {
        public final TextView txtMsg;
        public final TextView txtDate;
        public final ImageView img;

        public ClassicMessageViewHolder(int viewType, TextView txtMsg, TextView txtDate, ImageView img) {
            super(viewType);
            this.txtMsg = txtMsg;
            this.txtDate = txtDate;
            this.img = img;
        }
    }
}

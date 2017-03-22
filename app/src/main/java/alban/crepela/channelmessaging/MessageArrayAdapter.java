package alban.crepela.channelmessaging;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * Created by dupuyr on 23/01/2017.
 */
public class MessageArrayAdapter extends ArrayAdapter<Message> {

    private MediaPlayer mdPl;

    public MessageArrayAdapter(Context context, List<Message> messages) {
        super(context, 0, messages);
    }

    @Override
    public int getItemViewType(int position) {
        Message message = getItem(position);
        int retour = 0;
        if(message.getSoundUrl().equals("")&&message.getMessageImageUrl().equals("")) {
            retour = R.layout.messages_list_row;
        }
        else if(message.getSoundUrl().equals("")) {
            retour = R.layout.messages_img_list_row;
        }
        else {
            retour = R.layout.messages_sound_list_row;

        }
        return retour;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Message message = getItem(position);
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

        if(viewHolder.viewType == R.layout.messages_sound_list_row) {
            SoundMessageViewHolder messageViewHolder = (SoundMessageViewHolder) viewHolder;
            messageViewHolder.txtDate.setText(message.getUserID()+" : "+message.getDate());

            if(ChannelActivity.listBitmaps.containsKey((message.getImageUrl()))) {
                Bitmap imgDl = ChannelActivity.listBitmaps.get(message.getImageUrl());
                messageViewHolder.imgView.setImageBitmap(GetRoundedCornerBitmap.rounded(imgDl));
            }
            else {
                new DownloadImageTask(messageViewHolder.imgView).execute(message.getImageUrl());
            }

            if(ChannelActivity.listSounds.containsKey((message.getSoundUrl()))) {
                ChannelActivity.listSounds.get(message.getSoundUrl());
            }


            ((SoundMessageViewHolder) viewHolder).btnSound.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final File soundFile = new File(Environment.getExternalStorageDirectory()+"/sound"+message.getSoundUrl().substring(message.getSoundUrl().lastIndexOf("/")));
                    DownloadFile download = new DownloadFile(message.getSoundUrl(), message.getSoundUrl().substring(message.getSoundUrl().lastIndexOf("/")));
                    ChannelActivity.listSounds.get(message.getSoundUrl());
                }
            });


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
        else if(itemViewType == R.layout.messages_img_list_row){
            View convertView = LayoutInflater.from(getContext()).inflate(R.layout.messages_img_list_row, parent, false);
            TextView txtName = (TextView)convertView.findViewById(R.id.textViewName);
            TextView txtDate = (TextView)convertView.findViewById(R.id.textViewDate2);
            ImageView imgView = (ImageView)convertView.findViewById(R.id.imageViewPhoto);
            convertView.setTag(new ImageMessageViewHolder(itemViewType, txtName, txtDate, imgView));
            return convertView;
        }
        else {
            View convertView = LayoutInflater.from(getContext()).inflate(R.layout.messages_sound_list_row, parent, false);
            TextView txtDate = (TextView)convertView.findViewById(R.id.txtSoundDate);
            ImageView imgView = (ImageView)convertView.findViewById(R.id.ImageView);
            Button btnLire = (Button)convertView.findViewById(R.id.btnReadSound);
            convertView.setTag(new SoundMessageViewHolder(itemViewType, txtDate, imgView, btnLire));
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

    public class SoundMessageViewHolder extends MessageArrayAdapterViewHolder {
        public final TextView txtDate;
        public final Button btnSound;
        public final ImageView imgView;

        public SoundMessageViewHolder(int viewType, TextView txtDate, ImageView imgView , Button btnSound) {
            super(viewType);
            this.txtDate = txtDate;
            this.imgView = imgView;
            this.btnSound = btnSound;
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

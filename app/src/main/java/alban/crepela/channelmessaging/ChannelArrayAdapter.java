package alban.crepela.channelmessaging;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by crepela on 20/01/2017.
 */
public class ChannelArrayAdapter extends ArrayAdapter<Channel>{

    public ChannelArrayAdapter(Context context, List<Channel> channels) {
        super(context, 0, channels);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) LayoutInflater.from(getContext().inflate(R.layout.channels_list_row, parent, false);
        View rowView = inflater.inflate(R.layout.channels_list_row, parent, false);
        /*
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        ImageView imageView = (ImageView)
                rowView.findViewById(R.id.icon);
        textView.setText(values[position]);
        String s = values[position];
        if (s.startsWith("iPhone")) {
            imageView.setImageResource(R.drawable.no);
        } else {
            imageView.setImageResource(R.drawable.ok);
        }*/
        return rowView;
    }
}

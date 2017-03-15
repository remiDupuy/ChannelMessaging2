package alban.crepela.channelmessaging.Button;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import alban.crepela.channelmessaging.SoundRecordDialog;

/**
 * Created by dupuyr on 15/03/2017.
 */
public class PlayButton extends Button{
    SoundRecordDialog srd = new SoundRecordDialog();
    boolean mStartPlaying = true;

    View.OnClickListener clicker = new View.OnClickListener() {
        public void onClick(View v) {
            srd.onPlay(mStartPlaying);
            if (mStartPlaying) {
                setText("Stop playing");
            } else {
                setText("Start playing");
            }
            mStartPlaying = !mStartPlaying;
        }
    };

    public PlayButton(Context ctx) {
        super(ctx);
        setText("Start playing");
        setOnClickListener(clicker);
    }

    public PlayButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setText("Start playing");
        setOnClickListener(clicker);
    }
}

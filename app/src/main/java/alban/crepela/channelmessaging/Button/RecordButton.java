package alban.crepela.channelmessaging.Button;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import alban.crepela.channelmessaging.SoundRecordDialog;

/**
 * Created by dupuyr on 15/03/2017.
 */
public class RecordButton extends Button{
    SoundRecordDialog srd = new SoundRecordDialog();
    boolean mStartRecording = true;

    View.OnClickListener clicker = new View.OnClickListener() {
        public void onClick(View v) {
            srd.onRecord(mStartRecording);
            if (mStartRecording) {
                setText("Stop recording");
            } else {
                setText("Start recording");
            }
            mStartRecording = !mStartRecording;
        }
    };

    public RecordButton(Context ctx) {
        super(ctx);
        setText("Start recording");
        setOnClickListener(clicker);
    }

    public RecordButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setText("Start recording");
        setOnClickListener(clicker);
    }
}

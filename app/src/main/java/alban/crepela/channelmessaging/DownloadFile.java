package alban.crepela.channelmessaging;

import android.media.MediaPlayer;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by dupuyr on 22/03/2017.
 */
public class DownloadFile extends AsyncTask<String, Void, Void> {

    private ArrayList<OnDownloadCompleteListener> listeners = new ArrayList<OnDownloadCompleteListener>();

    public DownloadFile() {

    }

    @Override
    protected Void doInBackground(String... params) {
        downloadFromUrl(params[0], params[1]);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        for(OnDownloadCompleteListener oneListener : listeners) {
            oneListener.onDownloadCompleted("");
        }
    }

    private void downloadFromUrl(String fileURL, String fileName) {
        try {
            URL url = new URL(fileURL);
            File file = new File(fileName);
            file.createNewFile();
 /* Open a connection to that URL. */
            URLConnection ucon = url.openConnection();
 /* Define InputStreams to read from the URLConnection.*/
            InputStream is = ucon.getInputStream();
 /* Read bytes to the Buffer until there is nothing more to
read(-1) and write on the fly in the file.*/
            FileOutputStream fos = new FileOutputStream(file);
            final int BUFFER_SIZE = 23 * 1024;
            BufferedInputStream bis = new BufferedInputStream(is,
                    BUFFER_SIZE);
            byte[] baf = new byte[BUFFER_SIZE];
            int actual = 0;
            while (actual != -1) {
                fos.write(baf, 0, actual);
                actual = bis.read(baf, 0, BUFFER_SIZE);
            }
            fos.close();

            MediaPlayer mdPl = new MediaPlayer();
            mdPl.setDataSource(fileName);
            mdPl.prepare();
            ChannelActivity.listSounds.put(fileName, mdPl);
        } catch (IOException e) {
            //TODO HANDLER
        }
    }


}

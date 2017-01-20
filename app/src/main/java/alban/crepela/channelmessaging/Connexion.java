package alban.crepela.channelmessaging;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by crepela on 20/01/2017.
 */
public class Connexion  extends AsyncTask<String,Integer, Reponse> {
    private Context myContext;
    /*
    private String id;
    private String mdp;*/
    private ArrayList<OnDownloadCompleteListener> listeners = new ArrayList<OnDownloadCompleteListener>();

    public Connexion(Context myContext)
    {
        this.myContext = myContext;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(myContext, "Connexion en cours...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onProgressUpdate(Integer... values){
        super.onProgressUpdate(values);
        Toast.makeText(myContext, "Avancement : "+values.toString()+"/100",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Reponse doInBackground(String... arg0) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username",arg0[0]);
        params.put("password",arg0[1]);
        String response = this.performPostCall("http://www.raphaelbischof.fr/messaging/?function=connect", params);

        Gson gson = new Gson();
        Reponse obj = gson.fromJson(response, Reponse.class);

        return obj;
    }

    @Override
    protected void onPostExecute(Reponse result) {
        if(result.getResponse().equals("Ok"))
            Toast.makeText(myContext, "Connecté", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(myContext, "Erreur de connexion", Toast.LENGTH_SHORT).show();

        for (OnDownloadCompleteListener oneListener : listeners)
        {
            oneListener.onDownloadCompleted(result.getResponse());
        }
    }


    public String performPostCall(String requestURL, HashMap<String, String> postDataParams) {
        URL url;
        String response = "";
        try {
            url = new URL(requestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));
            writer.flush();
            writer.close();
            os.close();
            int responseCode=conn.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new
                        InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
            }
            else {
                response="";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first) first = false;
            else result.append("&");
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }

    public void setOnDownloadCompleteListener (OnDownloadCompleteListener listener)
    {
        // Store the listener object
        this.listeners.add(listener);
    }
}

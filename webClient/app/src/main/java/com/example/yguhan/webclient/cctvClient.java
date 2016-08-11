package com.example.yguhan.webclient;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.util.Log;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipart;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;


public class cctvClient extends AppCompatActivity {

    private static final String DEBUG_TAG = "HttpClient";
    private static final String urlText = "http://www.dongaribot.com/userPage/list/";
    private TextView textView;
    private EditText editText;
    private Button button;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cctv_client);

        editText = (EditText) findViewById(R.id.editText);
        textView = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String imgName = editText.getText().toString();
                if (imgName != null) {
                    myClickHandler(imgName);
                    imgName = null;
                }
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void myClickHandler(String imgName) {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (imgName != null) {
                Log.v("hello world", urlText);
                //new DownloadWebpageTask().execute(urlText);
                new PostQueryTask().execute(urlText, imgName);
            } else {
                textView.setText("Selected Img is invalid");
            }
        } else {
            textView.setText("No network connection available");
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "cctvClient Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.yguhan.webclient/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "cctvClient Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.yguhan.webclient/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    private class PostQueryTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... querys) {

            //try2
            int fbyte, buffersize, cbuffer;
            int maxbuffer = 1024 * 1024;
            final String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****" + Long.toString(System.currentTimeMillis()) + "*****";
            String returnByServer = "";
            BufferedReader reader = null;

            Log.v("doInBackground: ", querys[0]);
            String fileName = querys[1] + ".jpg";
            String path = "/storage/emulated/0/DCIM/Camera/" + fileName;


            String sfile = path;

            try {

                //** start tmp
                //try2 continue
                //FileBody fileBody = new FileBody(new File(path));

                Log.v("path: ", path);

                File file = new File(path);
                FileBody fb = new FileBody(file);

                MultipartEntityBuilder reqEntity = MultipartEntityBuilder.create();
                HttpPost httpPost = new HttpPost(querys[0]);
                HttpClient httpClient = new DefaultHttpClient();

                reqEntity.addTextBody("Connection", "keep-Alive", ContentType.TEXT_PLAIN);
                reqEntity.addTextBody("ENCType", "multipart/form-data", ContentType.TEXT_PLAIN);
                reqEntity.addTextBody("Content-Type", "multipart/form-data; boundary=" + boundary, ContentType.TEXT_PLAIN);
                reqEntity.addPart("docfile", fb);

                HttpEntity entity = reqEntity.build();
                httpPost.setEntity(entity);

                HttpResponse response = httpClient.execute(httpPost);
                returnByServer = EntityUtils.toString(response.getEntity());

                //returnByServer = httpPost.getRequestLine().toString();
                if (returnByServer != "") {
                    return returnByServer;
                } else
                    return "Success: server return nothing..";
                //temp **/

                /** original start
                 File f=new File(sfile);
                 FileInputStream fis=new FileInputStream(f);

                 URL url=new URL(querys[0]);
                 HttpURLConnection con =(HttpURLConnection) url.openConnection();
                 con.setDoInput(true);
                 con.setDoOutput(true);
                 con.setRequestMethod("POST");
                 con.setUseCaches(false);
                 con.setRequestProperty("Connection", "keep-Alive");
                 con.setRequestProperty("ENCType", "multipart/form-data");
                 con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
                 //con.setRequestProperty("imagefile", fileName);
                 //con.setRequestProperty("docfile", sfile);

                 DataOutputStream dos = new DataOutputStream(con.getOutputStream());
                 // upload image :
                 dos.writeBytes(twoHyphens + boundary + lineEnd);
                 //dos.writeBytes("Content-Disposition: form-data; name=\"docfile\"; filename=\"" + fileName + "\""+ lineEnd);
                 //dos.writeBytes("Content-Disposition: form-data; name=\"docfile\"; filename=\"" + fileName + "\""+ lineEnd);
                 dos.writeBytes("Content-Disposition: form-data; name=\"docfile\"; filename=\"" + fileName + "\""+ lineEnd);
                 //add start
                 dos.writeBytes("Content-Type: image/jpeg" + lineEnd);
                 dos.writeBytes("Content-Transfer-Encoding: binary" + lineEnd);
                 //add end
                 dos.writeBytes(lineEnd);

                 fbyte=fis.available();
                 buffersize=Math.min(fbyte, maxbuffer);
                 byte[] buffer=new byte[buffersize];
                 cbuffer=fis.read(buffer, 0, buffersize);
                 while(cbuffer>0){
                 dos.write(buffer,0,buffersize);
                 fbyte=fis.available();
                 buffersize=Math.min(fbyte, maxbuffer);
                 cbuffer=fis.read(buffer,0,buffersize);
                 }
                 dos.writeBytes(lineEnd);


                 if(con.getResponseCode()==200){
                 fis.close();
                 dos.flush();
                 dos.close();
                 }

                 return "success";
                 original end **/



                /***
                 String Username = "my username";
                 String Token    = "my token";

                 //write username :
                 dos.writeBytes(twoHyphens+ boundary + lineEnd);
                 dos.writeBytes("Content-Disposition: form-data; name=\"username\";" + lineEnd);
                 dos.writeBytes("Content-Type: text/plain; charset=UTF-8" + lineEnd);
                 dos.writeBytes(lineEnd + Username + lineEnd);
                 dos.writeBytes(lineEnd);

                 //write token :
                 dos.writeBytes(twoHyphens+ boundary + lineEnd);
                 dos.writeBytes("Content-Disposition: form-data; name=\"token\";" + lineEnd);
                 dos.writeBytes("Content-Type: text/plain; charset=UTF-8" + lineEnd);
                 dos.writeBytes(lineEnd + Token + lineEnd);
                 dos.writeBytes(lineEnd);


                 **/

                /**
                 //try0
                 //return uploadImg(querys[0], querys[1]);

                 //try1
                 String fileName = querys[1] + ".jpg";
                 String path = "/storage/emulated/0/DCIM/Camera/"+ fileName;
                 Bitmap bitmap = BitmapFactory.decodeFile(path);
                 ByteArrayOutputStream bos = new ByteArrayOutputStream();
                 bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                 ContentBody contentBody = new ByteArrayBody(bos.toByteArray(), fileName);

                 MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                 reqEntity.addPart("picture", contentBody);
                 String response = multipost(querys[0], reqEntity);
                 return response;
                 **/
            }
            // catch case of try1
            catch (Exception e) {
                Log.e("Debug", "error: " + e.getMessage(), e);
                return "Unable to send query. URL may be invalid";
            }
            /** catch case of try0
             catch (IOException e) {
             Log.e("Debug", "error: " + e.getMessage(), e);
             return "Unable to send query. URL may be invalid";
             }
             **/
        }

        @Override
        protected void onPostExecute(String result) {
            textView.setText(result);
        }
    }



    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                Log.v("doInBackground: ", urls[0]);
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            textView.setText(result);
        }
    }

    private String downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        int len = 500;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            conn.connect();
            int response = conn.getResponseCode();
            Log.d(DEBUG_TAG, "The response is: " + response);
            is = conn.getInputStream();

            String contentAsString = readIt(is, len);
            return contentAsString;
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }


}



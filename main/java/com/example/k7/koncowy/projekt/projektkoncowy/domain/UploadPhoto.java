package com.example.k7.koncowy.projekt.projektkoncowy.domain;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.http.HttpResponseCache;
import android.os.AsyncTask;

import com.example.k7.koncowy.projekt.projektkoncowy.Settings;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.concurrent.RecursiveTask;

public class UploadPhoto extends AsyncTask<String, Void, String> {
    private ProgressDialog pDialog;
    private Context _context;
    private byte[] photo;
    private String result;
    public UploadPhoto(Context context, byte[] photo)
    {
        _context = context;
        this.photo = photo;
        configureProgressDialog();
    }

    private void configureProgressDialog()
    {
        pDialog = new ProgressDialog(_context);
        pDialog.setMessage("UPLOADING...");
        pDialog.setCancelable(false);
    }

    @Override
    protected String doInBackground(String... strings) {
        HttpPost httpPost = new HttpPost(Settings.UPLOAD_URL);
        httpPost.setEntity(new ByteArrayEntity(photo));
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpPost);
            result = EntityUtils.toString(httpResponse.getEntity(), HTTP.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        pDialog.dismiss();
    }
}

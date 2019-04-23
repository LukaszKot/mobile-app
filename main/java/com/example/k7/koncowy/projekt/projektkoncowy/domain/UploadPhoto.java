package com.example.k7.koncowy.projekt.projektkoncowy.domain;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class UploadPhoto extends AsyncTask<String, Void, String> {
    private ProgressDialog pDialog;
    private Context _context;
    public UploadPhoto(Context context)
    {
        _context = context;
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
        return null;
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

package com.example.k7.koncowy.projekt.projektkoncowy.domain;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Network {

    private Context _context;

    public Network(Context context)
    {
        _context = context;
    }

    public boolean checkInternetAccess()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo==null || !networkInfo.isConnected())
        {
            return false;
        }
        else
        {
            return true;
        }
    }

}

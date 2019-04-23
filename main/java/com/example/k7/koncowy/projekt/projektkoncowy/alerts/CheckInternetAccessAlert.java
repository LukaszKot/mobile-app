package com.example.k7.koncowy.projekt.projektkoncowy.alerts;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.example.k7.koncowy.projekt.projektkoncowy.domain.ICallback;
import com.example.k7.koncowy.projekt.projektkoncowy.domain.Network;

public class CheckInternetAccessAlert {
    private Context _context;
    private AlertDialog.Builder alert;
    private Network _network;
    public CheckInternetAccessAlert(Context context)
    {
        _context = context;
        _network = new Network(_context);
    }

    public void enforceInternetAccess(final ICallback callback)
    {
        if(!_network.checkInternetAccess())
        {
            displayAlert(callback);
        }
        else
        {
            callback.whenProcessDone();
        }
    }

    private void displayAlert(final ICallback callback)
    {
        alert = new AlertDialog.Builder(_context);
        alert.setTitle("Brak dostępu do Internetu.");
        alert.setMessage("Włącz Internet aby móc kontynować...");
        alert.setNegativeButton("ANULUJ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                enforceInternetAccess(callback);
            }
        });
        alert.show();
    }
}

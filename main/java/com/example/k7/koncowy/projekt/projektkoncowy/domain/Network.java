package com.example.k7.koncowy.projekt.projektkoncowy.domain;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.k7.koncowy.projekt.projektkoncowy.Settings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

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

    public void loadJsonToArrayList(final IGetItemsCallback callback)
    {
        Response.Listener<String> responseListiner = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(_context, response, Toast.LENGTH_SHORT).show();
                ArrayList<Item> items = convertJsonToItems(response);
                callback.onDataLoaded(items);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("xxx", "error" + error.getMessage());
            }
        };
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Settings.DOWNLOAD_URL,
                responseListiner, errorListener);
        RequestQueue requestQueue = Volley.newRequestQueue(_context);
        requestQueue.add(stringRequest);
    }

    private ArrayList<Item> convertJsonToItems(String json)
    {
        try
        {
            ArrayList<Item> list = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("ImagesList");

            for (int i=0; i<jsonArray.length(); i++)
            {
                JSONObject obj = jsonArray.getJSONObject(i);
                Item listItem = getSingleItem(obj);
                list.add(listItem);
            }
            return list;
        }catch (JSONException exception)
        {
        }
        return null;
    }

    private Item getSingleItem(JSONObject obj)
    {
        try
        {
            Item listItem = new Item(obj.getString("IMAGE_SAVE_TIME"),
                    obj.getInt("IMAGE_SIZE"), obj.getString("IMAGE_NAME"));
            return listItem;
        }catch (JSONException exception)
        {
        }
        return null;
    }

}

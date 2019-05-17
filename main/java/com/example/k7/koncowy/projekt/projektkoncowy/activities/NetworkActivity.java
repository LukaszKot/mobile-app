package com.example.k7.koncowy.projekt.projektkoncowy.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.k7.koncowy.projekt.projektkoncowy.R;
import com.example.k7.koncowy.projekt.projektkoncowy.adapters.ItemAdapter;
import com.example.k7.koncowy.projekt.projektkoncowy.domain.IGetItemsCallback;
import com.example.k7.koncowy.projekt.projektkoncowy.domain.Item;
import com.example.k7.koncowy.projekt.projektkoncowy.domain.Network;

import java.util.ArrayList;

public class NetworkActivity extends AppCompatActivity {

    private ArrayList<Item> _list;
    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(NetworkActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        new Network(NetworkActivity.this).loadJsonToArrayList(new IGetItemsCallback() {
            @Override
            public void onDataLoaded(ArrayList<Item> items) {
                _list = items;
                adapter = new ItemAdapter(_list);
                recyclerView.setAdapter(adapter);
            }
        });




    }
}

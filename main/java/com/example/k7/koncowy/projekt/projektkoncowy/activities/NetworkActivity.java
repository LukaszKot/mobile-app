package com.example.k7.koncowy.projekt.projektkoncowy.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.k7.koncowy.projekt.projektkoncowy.R;
import com.example.k7.koncowy.projekt.projektkoncowy.adapters.ItemAdapter;
import com.example.k7.koncowy.projekt.projektkoncowy.domain.Item;

import java.util.ArrayList;

public class NetworkActivity extends AppCompatActivity {

    private ArrayList<Item> _list = new ArrayList<>();
    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);
        recyclerView = findViewById(R.id.recyclerView);

        _list.add(new Item("pierwszy", 33, null));
        _list.add(new Item("drugi", 66, null));
        _list.add(new Item("trzeci", 99, null));

        layoutManager = new LinearLayoutManager(NetworkActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ItemAdapter(_list);
        recyclerView.setAdapter(adapter);
    }
}

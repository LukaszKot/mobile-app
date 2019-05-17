package com.example.k7.koncowy.projekt.projektkoncowy.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.k7.koncowy.projekt.projektkoncowy.R;
import com.example.k7.koncowy.projekt.projektkoncowy.Settings;
import com.example.k7.koncowy.projekt.projektkoncowy.domain.Item;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private List<Item> _list;

    public ItemAdapter(List<Item> list)
    {
        _list = list;
    }

    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemAdapter.ViewHolder holder, int position) {
        Item item = _list.get(position);
        holder.saveDate.setText("data zapisu: "+item.getSaveDate());
        holder.size.setText("rozmiar: "+ String.valueOf(item.getSize()));
        Picasso.get()
                .load(Settings.DOWNLOAD_IMAGES_URL+item.getName())
                .into(holder.image);

    }

    @Override
    public int getItemCount() {
        return _list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView saveDate;
        private TextView size;
        private ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            saveDate = itemView.findViewById(R.id.saveDate);
            size = itemView.findViewById(R.id.size);
            image = itemView.findViewById(R.id.image);
        }
    }
}

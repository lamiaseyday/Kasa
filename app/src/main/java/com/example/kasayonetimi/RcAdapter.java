package com.example.kasayonetimi;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RcAdapter extends RecyclerView.Adapter<RcAdapter.MyViewHolder> {


    LayoutInflater layoutInflater;
    List<SelectItems> itemsList;
    List<Integer> selectID = new ArrayList<>();
    Context context;
    int pos;

    public RcAdapter(Context context, List<SelectItems> itemsList) {
        layoutInflater = layoutInflater.from(context);
        this.itemsList = itemsList;
        this.context = context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.custom_list_view, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        pos = position + 1;
        holder.bind(itemsList.get(position));

        /*holder.textBuro.setText(itemsList.get(position).getBuroNo());
        holder.textEsas.setText(itemsList.get(position).getEsasNo());
        holder.textMahkeme.setText(itemsList.get(position).getMahkeme());
        holder.textBorclu.setText(itemsList.get(position).getBorcluİsim());*/
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    public void filteredList(List<SelectItems> filterİtems) {
        itemsList = filterİtems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
       return position;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textBuro, textEsas, textMahkeme, textBorclu;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textBuro = itemView.findViewById(R.id.buroNo);
            textEsas = itemView.findViewById(R.id.esasNo);
            textMahkeme = itemView.findViewById(R.id.mahkeme);
            textBorclu = itemView.findViewById(R.id.borcluİsmi);
            imageView = itemView.findViewById(R.id.imageCheck);
        }

        void bind(final SelectItems selectItems) {
            textBuro.setText(selectItems.getBuroNo());
            textEsas.setText(selectItems.getEsasNo());
            textMahkeme.setText(selectItems.getMahkeme());
            textBorclu.setText(selectItems.getBorcluİsim());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        selectItems.setSelected(!selectItems.isSelected());
                        imageView.setVisibility(selectItems.isSelected() ? View.VISIBLE : View.GONE);
                    }
            });
        }
    }
    public List<SelectItems> getAll() {
        return itemsList;
    }

    public List<SelectItems> getSelected() {
        List<SelectItems> selected = new ArrayList<>();

        for (int i = 0; i < itemsList.size(); i++) {

                if (itemsList.get(i).isSelected()) {
                    selected.add(itemsList.get(pos));
                }
        }
        return selected;
    }
}

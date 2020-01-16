package com.example.kasayonetimi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class RcAdapter extends RecyclerView.Adapter<RcAdapter.MyViewHolder> {

    LayoutInflater layoutInflater;
    List<SelectItems> itemsList;
    Context context;
    public RcAdapter(Context context, List<SelectItems> itemsList) {
        layoutInflater = layoutInflater.from(context);
        this.itemsList = itemsList;
        this.context = context;
    }

    /**
     * listenin her elemanı hangi tasarım da olacak ise o tasarıma
     * ait olan xml dosyası inflater ile java nesnesine bağlanıyor.
     */
    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.custom_list_view, parent, false);
        return new MyViewHolder(view);
    }

    /**
     * Gerekli bileşenler bind metodunda activity'e bağlanıyor.
     * MyViewHolder sınıfında bileşenler tanımlanıyor ve yapıcı
     * metod ile ilk değerleri atanıyor.
     */
    @Override
    public void onBindViewHolder( final MyViewHolder holder, final int position) {
        final SelectItems item = itemsList.get(position);
        holder.bind(itemsList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            /**
             * Listenin seçilen elemanına image görünür olur. seçim iptal
             * edildiğinde ise image gizlenir.
             */
            @Override
            public void onClick(View view) {
                item.setSelected(!item.isSelected());
                holder.imageView.setVisibility(item.isSelected() ? View.VISIBLE : View.GONE);
            }
        });
        holder.imageView.setVisibility(item.isSelected() ? View.VISIBLE : View.GONE);
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
        EditText searchİtems = null;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textBuro = itemView.findViewById(R.id.buroNo);
            textEsas = itemView.findViewById(R.id.esasNo);
            textMahkeme = itemView.findViewById(R.id.mahkeme);
            textBorclu = itemView.findViewById(R.id.borcluİsmi);
            imageView = itemView.findViewById(R.id.imageCheck);
            searchİtems = itemView.findViewById(R.id.search);
        }
        void bind(final SelectItems selectItems) {
            textBuro.setText(selectItems.getBuroNo());
            textEsas.setText(selectItems.getEsasNo());
            textMahkeme.setText(selectItems.getMahkeme());
            textBorclu.setText(selectItems.getBorcluİsim());
        }
    }

    public List<SelectItems> getAll() {
        return itemsList;
    }

    public List<SelectItems> getSelected() {
        List<SelectItems> selected = new ArrayList<>();

        for (int i = 0; i < itemsList.size(); i++) {

            if (itemsList.get(i).isSelected()) {
                selected.add(itemsList.get(i));
            }
        }
        return selected;
    }
}
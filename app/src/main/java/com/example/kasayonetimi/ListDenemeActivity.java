package com.example.kasayonetimi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListDenemeActivity extends AppCompatActivity {

    ListView deneme;
    //ArrayAdapter<String> arrayAdapter;
    //private String[] dizi = {"seyda","seyda","seyda","seyda","seyda"};
    DenemeAdapter denemeAdapter;
    List<DenemeModel> listem = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_deneme);

        deneme = findViewById(R.id.denemeList);

        listem.add(new DenemeModel("asd", "asd", R.drawable.search));
        listem.add(new DenemeModel("asd", "asd", R.drawable.search));
        listem.add(new DenemeModel("asd", "asd", R.drawable.search));
        listem.add(new DenemeModel("asd", "asd", R.drawable.search));
        listem.add(new DenemeModel("asd", "asd", R.drawable.search));
        listem.add(new DenemeModel("asd", "asd", R.drawable.search));

        denemeAdapter = new DenemeAdapter(ListDenemeActivity.this, listem);
        deneme.setAdapter(denemeAdapter);

        deneme.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ListDenemeActivity.this, "tıklandı", Toast.LENGTH_LONG).show();
            }
        });
    }
}

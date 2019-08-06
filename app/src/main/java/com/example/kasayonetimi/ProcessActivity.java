package com.example.kasayonetimi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class ProcessActivity extends AppCompatActivity {

    /*
    * Toolbar toolbarPro yapınca hata veriyor.
    * Tip farklılığından dolayı.
    * */
    ListView listView;
    Button btnMasrafİsle;
    androidx.appcompat.widget.Toolbar toolbarPro;
    Spinner spinnerMasraf;
    LinearLayout linearLayout;
    ArrayList<SelectItems> selectItems = new ArrayList<>();

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process);

        listView = new ListView(this);
        btnMasrafİsle = findViewById(R.id.masraf_isle);
        toolbarPro = findViewById(R.id.toolbarProcess);
        linearLayout = findViewById(R.id.lnr);
       // spinnerMasraf = findViewById(R.id.spinnerMas);


        String[] items = {"deneme", "deneme","deneme","deneme"};


        toolbarPro.setTitle(getResources().getString(R.string.app_name));

       // selectItems = (ArrayList<SelectItems>) getIntent().getSerializableExtra("list");

        Intent i = getIntent();
        selectItems = (ArrayList<SelectItems>) i.getSerializableExtra("list");

        ArrayAdapter<SelectItems> adapter = new ArrayAdapter<>(ProcessActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, selectItems);
        listView.setAdapter(adapter);

        Button button = new Button(this);
        button.setText("Seçili " + selectItems.size() + " Adet Dosyayı Görmek İçin Tıklayınız");
        button.setTextSize((float) 13.0);
        button.setTextColor(Color.parseColor("#424A5D"));
        button.setBackgroundResource(R.drawable.button_masrafdosyalistesi);

        LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        buttonLayoutParams.setMargins(50, 0, 0, 0);
        button.setLayoutParams(buttonLayoutParams);
        linearLayout.addView(button);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProcessActivity.this);
                builder.setCancelable(true);
                builder.setPositiveButton("ok", null);
                builder.setView(listView);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


     /*   ArrayAdapter<String> masrfaAdapter = new ArrayAdapter<>(ProcessActivity.this,
                R.layout.spinner_style, getResources().getStringArray(R.array.names));

        masrfaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMasraf.setAdapter(masrfaAdapter);*/

    }
    public void cikis(View view){
        Intent i = new Intent(ProcessActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }


    public void masrafİsle(View view){

        final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(ProcessActivity.this);
        builder.setTitle("Uyarı!");
        builder.setMessage("Masraf İşlensin mi?");
        builder.setCancelable(false);

        builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "İşleme yapılıyor.", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "İşlem iptal oldu.", Toast.LENGTH_SHORT).show();

            }
        });

        androidx.appcompat.app.AlertDialog dialog = builder.create();
        dialog.show();

    }

/*    @Override
    public void onBackPressed() {
        Intent i = new Intent(ProcessActivity.this, SelectedActivity.class);
        startActivity(i);
        finish();
        super.onBackPressed();
    }*/
}

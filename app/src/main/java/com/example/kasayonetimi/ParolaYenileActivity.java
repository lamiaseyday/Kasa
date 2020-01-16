package com.example.kasayonetimi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ParolaYenileActivity extends AppCompatActivity {

    Button butonParolaYenile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parola_yenile);
        butonParolaYenile = findViewById(R.id.btn_parolayenile);
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(ParolaYenileActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
        super.onBackPressed();
    }
    public void ParolaYenile(View view) {
        Toast.makeText(ParolaYenileActivity.this, "Parolanız Yenilendi", Toast.LENGTH_LONG).show();
        Intent i = new Intent(ParolaYenileActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }
}

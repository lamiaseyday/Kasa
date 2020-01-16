package com.example.kasayonetimi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ProcessActivity extends AppCompatActivity implements AnimationDrawableWithCallback.IAnimationFinishListener{

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    androidx.appcompat.app.AlertDialog.Builder builder;
    androidx.appcompat.app.AlertDialog dialog;
    ImageView imageanim;
    AnimationDrawableWithCallback animation;
    ListView secililer;
    ProgressDialog progressDialog;
    Button btnMasrafİsle;
    EditText masrafBedeli;
    androidx.appcompat.widget.Toolbar toolbarPro;
    //Spinner spinnerMasraf;
    LinearLayout linearLayout;
    List<SelectItems> selectItems = new ArrayList<>();
//n adet dosya n lira masraf işleniyor
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = preferences.edit();

        masrafBedeli = findViewById(R.id.masrafBedel);
        imageanim = findViewById(R.id.imageanim);
        secililer = findViewById(R.id.seciliListe);
        btnMasrafİsle = findViewById(R.id.masraf_isle);
        toolbarPro = findViewById(R.id.toolbarProcess);
        linearLayout = findViewById(R.id.lnr);
        // spinnerMasraf = findViewById(R.id.spinnerMas);

        toolbarPro.setTitle(getResources().getString(R.string.app_name));

        /**
         * Daha önceki activity'den gönderdiğim verileri burda alıyorum.
         */
        Intent i = getIntent();
        selectItems = (ArrayList<SelectItems>) i.getSerializableExtra("list");

        /**
         * Button yaratma işlemi yapılıyor.
         */
        Button button = new Button(this);
        button.setText("Seçili " + selectItems.size() + " Adet Dosya Bulunmakta");
        button.setTextSize((float) 13.0);
        button.setTextColor(Color.parseColor("#424A5D"));
        button.setEnabled(false);
        button.setBackgroundResource(R.drawable.button_masrafdosyalistesi);

        LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        buttonLayoutParams.setMargins(50, 0, 0, 0);
        button.setLayoutParams(buttonLayoutParams);
        linearLayout.addView(button);

        progressDialog = new ProgressDialog(ProcessActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("yükleniyor");
    }
    /**
     * Masraf işleme onaylandıktan sonra çalışacak metod.
     */
    public void masrafİsle(View view) {


        if(masrafBedeli.getText().toString().equals("")){
            Toast.makeText(ProcessActivity.this, "Masraf Bedeli Giriniz", Toast.LENGTH_SHORT).show();
        }else {

            /**
             * Masraf bedeli girdisine ne kadar masraf olacağını girdikten sonra onayla işleminde
             * klavye kapanma işlemi yapılır.
             */
            if(!(masrafBedeli.getText().equals(""))) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }

            builder = new androidx.appcompat.app.AlertDialog.Builder(ProcessActivity.this);
            builder.setTitle("Bilgi!");
            builder.setMessage("Seçili " + selectItems.size() + " Adet Dosyaya " + masrafBedeli.getText() + " Lira Masraf İşleniyor.");
            builder.setCancelable(false);

            builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    animation = new AnimationDrawableWithCallback();
                    animation.addFrame(getResources().getDrawable(R.drawable.birinci), 500);
                    animation.addFrame(getResources().getDrawable(R.drawable.ikinci), 500);
                    animation.addFrame(getResources().getDrawable(R.drawable.ucuncu), 500);
                    animation.addFrame(getResources().getDrawable(R.drawable.dorduncu), 500);
                    animation.addFrame(getResources().getDrawable(R.drawable.besinci), 500);
                    animation.addFrame(getResources().getDrawable(R.drawable.altinci), 500);
                    animation.setOneShot(true);
                    imageanim.setImageDrawable(animation);
                    /**
                     * True ise, animasyon yalnızca tek bir kez çalışır ve sonra durur.
                     */
                    animation.start();
                    animation.setAnimationFinishListener(ProcessActivity.this);
                    ArrayAdapter<SelectItems> adapter = new ArrayAdapter<>(ProcessActivity.this, R.layout.select_item_layout, android.R.id.text1, selectItems);
                    secililer.setAdapter(adapter);
                    Intent it = new Intent();
                    setResult(RESULT_OK, it);
                    /**
                     * Her masraf işleminde animasyon gerçekleşir ve liste tekrardan yüklenir.
                     */
                    imageanim.setVisibility(View.VISIBLE);
                    secililer.setVisibility(View.GONE);
                }
            });
            builder.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(getApplicationContext(), "İşlem iptal oldu.", Toast.LENGTH_SHORT).show();
                }
            });
            dialog = builder.create();
            dialog.show();
        }
    }
    public void cikis(View view){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ProcessActivity.this);
        alertDialog.setTitle("Uyarı");
        alertDialog.setMessage("Çıkış Yaptıktan Sonra Aynı Kullanıcı Adı ve Şifre ile Giriş Yapmak Zorundasınız");
        alertDialog.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                /**
                 * Burda değerlerin hepsi silindiği için tekrardan uygulamaya giriş yapabilmesi
                 * için kayıt olmalıdır
                 * sharedpreferences değerlerinn hepsini siler
                 */
                editor.clear();
                editor.commit();

                Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(i);
                /**
                 * “java.lang.IllegalStateException: Can not be called to deliver a result”
                 * hatasını çözdü.
                 */
                setResult(Activity.RESULT_CANCELED);
                /**
                 * Activite belleği temizleniyor. Back tuşuna basınca kafayı yemesin diye :).
                 */
                finishAffinity();
                finish();
            }
        });
        alertDialog.show();
    }
    /**
     * Animasyon görevini tamamlayınca animasyon resimleri kaybolur
     * liste görünür olur.
     */
    @Override
    public void onAnimationChanged(int index, boolean finished) {
        if (finished) {
            imageanim.setVisibility(View.GONE);
            secililer.setVisibility(View.VISIBLE);
        }
    }
}

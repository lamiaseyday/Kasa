package com.example.kasayonetimi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    Button giris;
    EditText buroKod, kullaniciAd, parola;
    String buro_string, kulAd_string, parola_string;
    /**
     * preferences referansı
     */
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());//preferences objesi
        /**
         * aynı şekil editor nesnesi oluşturuluyor
         */
        editor = preferences.edit();
        /**
         * Burda sharedPreferencas üzerine kayıtlı login değerini alıyoruz.
         * Login değeri doğru giriş yapıldığında veya kayıt olduğunda true olarak kaydedilir
         * Amacı ise kullanıcı uygulamadan cıkarken direk çıkıs demeden cıktıysa yanı direk
         * home veya back tusuyla uygulamadan çıktıysa
         * Geri geldiğinde tekrar giriş bilgilerini istemeden anasayfaya yönlendiriyoruz
         * Bu değer ancak anasayfa üzerinde cıkış butonuna basılırsa diğer bilgiler
         * silinmeden bu değer false yapılır.
         *
         * yani bu if blogu sayesinde uygulamayı giriş yaptıktan sonra home veya back tuşu ile
         * kapattığımız zaman loginin false olmasına rağmen true gibi davranıp uygulamada
         * kaldığımız yerden devam etmemizi sağlar çıkış butonuna tıklamadığımız sürece.
         */
        if (preferences.getBoolean("login", false)) {
            Intent i = new Intent(getApplicationContext(), SelectedActivity.class);
            startActivity(i);
            finish();
        }
        buroKod = findViewById(R.id.editBuroKod);
        kullaniciAd = findViewById(R.id.edtKullaniciAdi);
        parola = findViewById(R.id.edtParola);
        giris = (Button) findViewById(R.id.btn_giris);

        giris.setOnClickListener(new View.OnClickListener() {//giriş butonu tıklandığı zaman
            @Override
            public void onClick(View v) {
                buro_string = buroKod.getText().toString();
                kulAd_string = kullaniciAd.getText().toString();
                parola_string = parola.getText().toString();
                /**
                 * bilgilerin eksik olmasına karşın yapılan kontrol
                 * String sınıfında bulunan matches metodu ile string'in içinde
                 * bir değerlendirme yapıyoruz boş mu değil mi diye.
                 */
                if (buro_string.matches("") || kulAd_string.matches("") || parola_string.matches("")) {

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);
                    alertDialog.setTitle("Uyar");
                    alertDialog.setMessage("Eksiksiz Doldurunz!");
                    alertDialog.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    alertDialog.show();
                } else {
                    editor.putString("buro", buro_string);
                    editor.putString("kula", kulAd_string);
                    editor.putString("par", parola_string);
                    /**
                     * uygulamaya tekrar girdiğinde kontrol için kullanılcak
                     * parametreler tam girildiğinde login true olur.
                     */
                    editor.putBoolean("login", true);
                    /**
                     * yapılan değişiklikler kaydedilmesi için editor nesnesinin commit() metodu çağırılır.
                     * Değerlerimizi sharedPreferences a kaydettik.Artık bu bilgiler ile giriş yapabiliriz.
                     */
                    editor.commit();
                    Intent i = new Intent(getApplicationContext(), SelectedActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });
    }
    /**
     *parola unutulduysa parola değiştirilecek olan kısım.
     */
    public void ParolamiUnuttum(View view) {
        Intent i = new Intent(LoginActivity.this, ParolaYenileActivity.class);
        startActivity(i);
        finish();
    }
}

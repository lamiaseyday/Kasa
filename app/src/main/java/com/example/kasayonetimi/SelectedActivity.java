package com.example.kasayonetimi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.view.View.*;

public class SelectedActivity extends AppCompatActivity {

    LinearLayout iptal, onay;
    SharedPreferences preferences;
    /**
     * preferences editor nesnesi referansı .prefernces nesnesine veri ekleyip cıkarmak için
     */
    SharedPreferences.Editor editor;
    ProgressDialog progressDialog;
    RecyclerView recyclerViewList;
    List<SelectItems> list = new ArrayList<>();
    EditText editSearch;
    ImageView imageCheck;
    Button buttonClear;
    RcAdapter rcAdapter;
    private static int current_page = 1;
    private int ival = 1;
    private int loadLimit = 10;
    List<String> secilenler;
    private int REQUEST_CODE = 1;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = preferences.edit();
        loadData(current_page);

        imageCheck = findViewById(R.id.imageCheck);
        editSearch = findViewById(R.id.search);
        buttonClear = findViewById(R.id.btnClear);
        recyclerViewList = findViewById(R.id.recyclerview_list);
        iptal = findViewById(R.id.lnrİptal);
        onay = findViewById(R.id.lnrOnayla);

        /**
         * LayoutManager Recyclerview da öğelerin yerleşimini yapmamız için gereklidir, Kendi
         * layoutManager’imizi RecyclerView.LayoutManager’i extend ederek kullanabiliriz ya da hali
         * hazırda olan LayoutManager’i da kullanabiliriz. 3 tane LayoutManager Çeşidi var,
         *
         *     LinearLayoutManager : Alt alta tek sıra olacak şekilde.
         *     GridLayoutManager: Alt alta fakat sıra sayısını siz belirleyebilirsiniz.
         *     StaggeredGridLayoutManager: Alt alta ama boyutunu görselleştirilmesini siz
         *     belirlersiniz. Özellikle dergi, kıyafet uygulamalarında harika gidiyor.
         */
        final LinearLayoutManager manager = new LinearLayoutManager(SelectedActivity.this);
        recyclerViewList.setLayoutManager(manager);
        rcAdapter = new RcAdapter(SelectedActivity.this, list);
        recyclerViewList.setAdapter(rcAdapter);

        recyclerViewList.setOnScrollListener(new EndlessRecyclerOnScrollListener(
                manager) {
            @Override
            public void onLoadMore(int current_page) {
                loadMoreData(current_page);
            }
        });

        /**
         * Activity içerisinde bulunan arama editText'in de olan değişimi dinlemek için
         * kullanılan metod.
         */
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            /**
             * EditText'e veri girildikten sonra çalışacak olan metod.
             */
            @Override
            public void afterTextChanged(Editable editable) {
                /**
                 * Bir veri girdiğimiz anda veriyi temizlemek için
                 * bir çarpı ikonu çıkar.
                 */
                buttonClear.setVisibility(VISIBLE);
                /**
                 * Eğer veri yoksa ortada ikon gizlenir.
                 */
                if (editSearch.getText().length() <= 0) {
                    buttonClear.setVisibility(GONE);
                }
                /**
                 * Filtreleme işleminin yapıldığı metod.
                 */
                filter(editable.toString());
            }
        });
        editSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /**
                 * Harfler arasında odaklma yaparak gezmek için.
                 */
                editSearch.requestFocus();
                editSearch.setFocusableInTouchMode(true);
                /**
                 * Klavyenin Sayfanın açıldığında değilde kullanıcı etkileşimi(tıklama,yan çevirme vs)
                 * ile açılmasını istiyorsanız.
                 */
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editSearch, InputMethodManager.SHOW_FORCED);
            }
        });
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Seçimleriniz Yükleniyor");
        progressDialog.dismiss();
    }
    /**
     *Arama Edit'inin temizlenme işlemini yapan metod.
     */
    public void clear(View view) {
        editSearch.getText().clear();
    }
    /**
     * Listeye ilk gelen elemanlar bu metodda tanımlıdır.
     */
    private void loadData(int current_page) {
        for (int i = ival; i <= loadLimit; i++) {
            SelectItems st = new SelectItems("124578", "234567", "mahkeme", "borçlu");
            list.add(st);
            ival++;
        }
    }
    /**
     * Sayfayı aşşağı doğru kaydırdıkça gelecek olan elemanlar bu metodda tanımlıdır.
     */
    private void loadMoreData(int current_page) {
        loadLimit = ival + 10;
        for (int i = ival; i <= loadLimit; i++) {
            SelectItems st = new SelectItems("buro" + i, "esas", "mahkeme", "borçlu");
            list.add(st);
            ival++;
        }
        /**
         *  Adaptare verinin değiştiğini bildirmek için kullanıyoruz.
         */
        rcAdapter.notifyDataSetChanged();
    }
    /**
     * Filtrelediğimiz elemanları atmak için bir liste oluşturuldu.
     * Liste üzerinde gezmek için bir döngü oluşturuldu.
     * Girilen büyük ve küçük harfler contains metodu kullanılarak karşılaştırıldı.
     * Liste Özel Adaptere gömüldü.
     */
    private void filter(String s) {
        List<SelectItems> filterİtems = new ArrayList<>();
        for (SelectItems selectItems : list) {
            if (selectItems.getBorcluİsim().toUpperCase().contains(s.toUpperCase()) || selectItems.getBorcluİsim().toLowerCase().contains(s.toLowerCase()) ||
                    selectItems.getBuroNo().toUpperCase().contains(s.toUpperCase()) || selectItems.getBuroNo().toLowerCase().contains(s.toLowerCase()) ||
                    selectItems.getMahkeme().toUpperCase().contains(s.toUpperCase()) || selectItems.getMahkeme().toLowerCase().contains(s.toLowerCase()) ||
                    selectItems.getEsasNo().toUpperCase().contains(s.toUpperCase()) || selectItems.getEsasNo().toLowerCase().contains(s.toLowerCase())) {
                filterİtems.add(selectItems);
            }
        }
        rcAdapter.filteredList(filterİtems);
    }

    /**
     * Listede seçim yaptıktan sonra onaylama yapıp sonraki activity'e geçtiğimiz metod.
     * Sayfaya geçiş yaparken 2 saniye yüklenme olur. Bu 2 saniye beklemeyi Handler sınıfından
     * oluşturduğum nesne ile postDelayed metoduna ulaşarak yapıyorum.
     */
    public void onayla(View view) {
        if (rcAdapter.getSelected().size() > 0) {
            progressDialog.show();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                }
            }, 2000);

            /**
             * Seçilenler listemi eğer burada ilklemezsem global olarak ilklersem
             * her geri yapıp tekrar seçim yaptıklarım artı önceden seçtiklerimi de
             * listeye atar.
             * Listemin tek bir elemanında birden fazla editText olduğu için her seçimimde
             * seçimimi tek bir eleman olarak göstermek için StringBuilder sınıfımda bulunan
             * append metodu ile birleştiriyorum ve listeye o şekilde atıyorum.
             */
            secilenler = new ArrayList<>();
            for (int i = 0; i < rcAdapter.getSelected().size(); i++) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("   " + rcAdapter.getSelected().get(i).getBorcluİsim() + "                ");
                stringBuilder.append(rcAdapter.getSelected().get(i).getEsasNo());
                secilenler.add(String.valueOf(stringBuilder));
                /**
                 * onaylama tuşuna basıldığı anda edittext içeriği temizlenir..
                 * */
                editSearch.getText().clear();
            }
            Intent in = new Intent(SelectedActivity.this, ProcessActivity.class);
            /**
             * Serializable sınıfı verilerin tipine bakmaksızın işlem yapmayı sağlıyor.
             */
            in.putExtra("list", (Serializable) secilenler);
            /**
             * Bir activity'den sonuç almak istediğimiz zaman kullanırız.
             * Çağıran aktivite çağırdığı aktivite tarafından geri verilen sonucu onActivityResult()
             * metodu içinde alır.
             */
            startActivityForResult(in, REQUEST_CODE);
        } else {
            Toast.makeText(SelectedActivity.this, "Seçim Yapmadınız!", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * Çağrılan aktivite tarafından tanımlanan sonuç kodu (Eğer işlem başarılı ise RESULT_OK,
         * kullanıcı işlemi iptal ederse veya işlem başarısız olursa RESULT_CANCELED değerini alır).
         *
         * BİLGİ: Aktiviteniz başlatıldığında, getIntent() metodunu kullanarak aktivitenizi
         * başlatan intent'i elde edebilirsiniz
         */
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                startActivity(getIntent());
                secilenler.clear();
            }
        }
    }
    public void exitApp(View view) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SelectedActivity.this);
        alertDialog.setTitle("Uyarı");
        alertDialog.setMessage("Çıkış Yaptıktan Sonra Aynı Kullanıcı Adı ve Şifre ile Giriş Yapmak Zorundasınız");
        alertDialog.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                /**
                 * Burdaki amaç çıkış butonu ile kullanıcı bilgilerini silmeden çıkış yapmaktır.
                 * Kullanıcı bilgileri silinmeyip sharedpreference üzerinde tutulduğu için aynı
                 * bilgilerle tekrardan giriş yapılabilir.
                 * login değerini değiştirerek cıkıs yaptığı için değeri false yapıyoruz.
                 */
                editor.putBoolean("login", false);
                editor.commit();
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
        alertDialog.show();
    }
    /**
     * Listeden seçtiğimiz verileri iptal etmeyi sağlar bu metod.
     */
    public void iptal(View view) {
        //finish();
        startActivity(getIntent());
    }
    @Override
    public void onBackPressed() {
        secilenler.clear();
        finish();
        moveTaskToBack(true);
        super.onBackPressed();
    }
}

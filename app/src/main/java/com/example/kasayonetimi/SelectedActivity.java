package com.example.kasayonetimi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SelectedActivity extends AppCompatActivity {

    RecyclerView recyclerViewList;
    List<SelectItems> list = new ArrayList<>();
    EditText editSearch;
    RcAdapter rcAdapter;
    ImageView imageCheck;
    private static int current_page = 1;
    private int ival = 1;
    private int loadLimit = 10;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected);

        loadData(current_page);
        imageCheck = findViewById(R.id.imageCheck);
        editSearch = findViewById(R.id.search);
        recyclerViewList = findViewById(R.id.recyclerview_list);


        final LinearLayoutManager manager = new LinearLayoutManager(SelectedActivity.this);

        recyclerViewList.setLayoutManager(manager);

        recyclerViewList.setOnScrollListener(new EndlessRecyclerOnScrollListener(
                manager) {
            @Override
            public void onLoadMore(int current_page) {
                loadMoreData(current_page);
            }
        });

        rcAdapter = new RcAdapter(SelectedActivity.this, list);
        recyclerViewList.setAdapter(rcAdapter);
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                filter(editable.toString());
            }
        });

        editSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editSearch.requestFocus();
                editSearch.setFocusableInTouchMode(true);

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editSearch, InputMethodManager.SHOW_FORCED);
            }

        });
    }
    private void loadData(int current_page) {
        for (int i = ival; i <= loadLimit; i++) {
            SelectItems st = new SelectItems("124578", "234567", "mahkeme", "borçlu");

            list.add(st);
            ival++;
        }
    }

    private void loadMoreData(int current_page) {
        loadLimit = ival + 10;

        for (int i = ival; i <= loadLimit; i++) {
            SelectItems st = new SelectItems("buro" + i, "esas", "mahkeme", "borçlu");

            list.add(st);
            ival++;
        }
        rcAdapter.notifyDataSetChanged();
    }

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

    public void onayla(View view) {
        if (rcAdapter.getSelected().size() > 0) {
            List<String> secilenler = new ArrayList<>();
            for (int i = 0; i < rcAdapter.getSelected().size(); i++) {

                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(rcAdapter.getSelected().get(i).getBorcluİsim() + " | ");
                stringBuilder.append(rcAdapter.getSelected().get(i).getEsasNo());

                secilenler.add(String.valueOf(stringBuilder));
            }
            Intent in = new Intent(SelectedActivity.this, ProcessActivity.class);
            in.putExtra("list", (Serializable) secilenler);
            startActivity(in);
        }
    }
    public void cikis(View view){
        Intent intent = new Intent(SelectedActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}

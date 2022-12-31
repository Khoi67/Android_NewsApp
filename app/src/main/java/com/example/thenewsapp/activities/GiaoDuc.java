package com.example.thenewsapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thenewsapp.Adapter;
import com.example.thenewsapp.Item;
import com.example.thenewsapp.R;
import com.example.thenewsapp.objects.Database;
import com.example.thenewsapp.objects.enums.Categories;
import com.example.thenewsapp.objects.enums.References;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GiaoDuc extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView rcGiaoDuc;
    Adapter itemAdapter;
    List<Item> itemList;
    Database database = new Database();
    DatabaseReference paperRef = database.getRef(References.PAPER);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giao_duc);

        rcGiaoDuc = findViewById(R.id.rc_GiaoDuc);
        toolbar = findViewById(R.id.toolbar);

        itemList = new ArrayList<>();
        itemAdapter = new Adapter(this ,itemList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcGiaoDuc.setLayoutManager(linearLayoutManager);
        rcGiaoDuc.setAdapter(itemAdapter);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        getListItem();

    }
    private void getListItem(){
        paperRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        Item item = dataSnapshot.getValue(Item.class);
                        if(item.getCategory().equals(Categories.GIAO_DUC)){
                            itemList.add(item);
                        }
                    }
                    itemAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(GiaoDuc.this, "Fails", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
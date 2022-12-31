package com.example.thenewsapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.thenewsapp.R;

public class Report1 extends AppCompatActivity {
    Button bttReport;
    Button bttBecomeWriter;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        bttReport = findViewById(R.id.bt_report);
        bttBecomeWriter = findViewById(R.id.bt_become_writer);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        bttReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openReport2();
            }
        });

        bttBecomeWriter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBecomeWriter();
            }
        });

        }
    public void openReport2(){
        Intent intent = new Intent(this, Report2.class);
        startActivity(intent);
    }
    public void openBecomeWriter() {
        Intent intent = new Intent(this, BecomeWritter.class);
        startActivity(intent);
    }
}
package com.example.thenewsapp.activities;

import static com.example.thenewsapp.LocalKey.THEME;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import com.example.thenewsapp.R;
import com.example.thenewsapp.SharedPrefs;

public class Setting extends AppCompatActivity {
    Button btAbout;
    Switch dmSwitch;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        dmSwitch = findViewById(R.id.dm_Switch);
        toolbar = findViewById(R.id.toolbar);
        btAbout = findViewById(R.id.bt_aboutUs);
        setSupportActionBar(toolbar);
        String theme = SharedPrefs.getInstance().get(THEME,String.class, "Light");
        if(theme.equals("Light")){
            dmSwitch.setChecked(false);
        }
        else{
            dmSwitch.setChecked(true);
        }
        dmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    SharedPrefs.getInstance().put(THEME, "Dark");
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                else {
                    SharedPrefs.getInstance().put(THEME, "Light");
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });



        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openAboutUs();
            }
        });
    }
    public void openAboutUs(){
        Intent intent = new Intent(this, AboutUs.class);
        startActivity(intent);
    }
}
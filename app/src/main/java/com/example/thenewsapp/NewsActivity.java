package com.example.thenewsapp;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class NewsActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView tvTitle, tvAuthor, tvContent;
    ImageView ivImage;
    GoogleSignInAccount googleSignInAccount;
    Button btn_Submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        init();
        googleSignInAccount = SharedPrefs.getInstance().get(LocalKey.GOOGLE_ACCOUNT_INFO, GoogleSignInAccount.class);
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Item item = (Item) bundle.get("object-item");
            tvTitle.setText(item.getTieuDe());
            tvContent.setText(item.getContent());
            tvAuthor.setText(tvAuthor.getText().toString() + " "+ item.getTacGia());
            Glide.with(NewsActivity.this).load(Uri.parse(item.getImgUri())).into(ivImage);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    private void init() {
        tvTitle =findViewById(R.id.tv_title);
        toolbar = findViewById(R.id.toolbar);
        tvAuthor = findViewById(R.id.tv_author);
        tvContent = findViewById(R.id.tv_content);
        ivImage = findViewById(R.id.iv_news_image);
        btn_Submit = findViewById(R.id.btn_comment_submit);
    }

}
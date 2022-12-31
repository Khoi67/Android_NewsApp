package com.example.thenewsapp.objects.writer;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.thenewsapp.Item;
import com.example.thenewsapp.LocalKey;
import com.example.thenewsapp.R;
import com.example.thenewsapp.SharedPrefs;
import com.example.thenewsapp.objects.Database;
import com.example.thenewsapp.objects.enums.Categories;
import com.example.thenewsapp.objects.enums.References;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;

public class Writer extends AppCompatActivity {

    private final int ACTIVITY_SELECT_IMAGE = 10;

    Database database = new Database();
    DatabaseReference writerRef = database.getRef(References.PAPER);

    EditText edTitle;
    EditText edContent;

    Button btnWrite, btnUploadImage;
    Toolbar toolbar;
    ImageView ivImagePreview;

    Spinner staticSpinner;

    GoogleSignInOptions gso;
    GoogleSignInClient googleSignInClient;
    GoogleSignInAccount googleSignInAccount;
    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    StorageReference imageRef = firebaseStorage.getReference("paper_images");

    Item item = new Item();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writer);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (googleSignInAccount == null) googleSignInAccount = SharedPrefs.getInstance().get(LocalKey.GOOGLE_ACCOUNT_INFO, GoogleSignInAccount.class);
        edTitle = findViewById(R.id.ed_title);
        edContent = findViewById(R.id.ed_content);
        btnWrite = findViewById(R.id.btn_submit);
        toolbar = findViewById(R.id.toolbar);
        staticSpinner = findViewById(R.id.spinner);
        btnUploadImage = findViewById(R.id.btn_upload_image);
        ivImagePreview = findViewById(R.id.iv_image_preview);
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.type,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        staticSpinner.setAdapter(staticAdapter);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent selectImage = new Intent();
                selectImage.setType("image/");
                selectImage.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(selectImage, ACTIVITY_SELECT_IMAGE);
            }
        });


        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Writer.this);
                alertDialogBuilder.setTitle("Đăng bài!");
                alertDialogBuilder.setMessage("Bạn có muốn đăng bài?");
                alertDialogBuilder.setCancelable(false);

                alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        String key = writerRef.push().getKey();
                        item.setContent(edContent.getText().toString());
                        item.setTieuDe(edTitle.getText().toString());
                        item.setId(key);
                        TextView tmptv = (TextView) staticSpinner.getSelectedView();
                        if (tmptv!= null) item.setCategory(getCategoryFromString(tmptv.getText().toString()));
                        if (googleSignInAccount != null) {
                            item.setTacGia(googleSignInAccount.getDisplayName());
                        }
                        writerRef.child(key).setValue(item);
                        finish();
                    }
                });

                alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });
    }

    private Categories getCategoryFromString(String c) {
        Categories result = Categories.THE_THAO;
        for (Categories categories : Categories.values()) {
            if (c.trim().equalsIgnoreCase(categories.getValue())){
                result = categories;
            }
        }
        return result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTIVITY_SELECT_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri imgURI = data.getData();
            String filename = Calendar.getInstance().getTimeInMillis()+"";
            imageRef.child(filename).putFile(imgURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageRef.child(filename).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            item.setFilename(filename);
                            item.setImgUri(uri.toString());
                            Glide.with(Writer.this).load(uri).into(ivImagePreview);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("upload_failed", e.toString());
                            Toast.makeText(Writer.this, "Fail to show image preview.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("upload_failed", e.toString());
                    Toast.makeText(Writer.this, "Fail to upload image.", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

}
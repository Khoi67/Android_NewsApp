package com.example.thenewsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thenewsapp.activities.GiaoDuc;
import com.example.thenewsapp.activities.PhapLuat;
import com.example.thenewsapp.activities.Report1;
import com.example.thenewsapp.activities.Setting;
import com.example.thenewsapp.activities.TheThao;
import com.example.thenewsapp.objects.Account;
import com.example.thenewsapp.objects.Database;
import com.example.thenewsapp.objects.admin.Admin;
import com.example.thenewsapp.objects.enums.References;
import com.example.thenewsapp.objects.enums.Role;
import com.example.thenewsapp.objects.writer.Writer;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    RecyclerView rvItem;
    Adapter itemAdapter;
    List<Item> itemList;
    LinearLayoutManager linearLayoutManager;

    GoogleSignInOptions gso;
    GoogleSignInClient googleSignInClient;
    GoogleSignInAccount googleSignInAccount;
    Account signInAccount;

    Database database = new Database();
    DatabaseReference paperRef = database.getRef(References.PAPER);
    DatabaseReference accountRef = database.getRef(References.ACCOUNT);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (googleSignInAccount == null) googleSignInAccount = SharedPrefs.getInstance().get(LocalKey.GOOGLE_ACCOUNT_INFO, GoogleSignInAccount.class);
        signInAccount = SharedPrefs.getInstance().get(LocalKey.LAST_SIGNED_IN_USER, Account.class, new Account());
        accountRef.child(googleSignInAccount.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    SharedPrefs.getInstance().put(LocalKey.LAST_SIGNED_IN_USER, snapshot.getValue());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        rvItem = findViewById(R.id.rvItem);
        itemList = new ArrayList<>();
        itemAdapter = new Adapter(this ,itemList);

        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rvItem.setLayoutManager(linearLayoutManager);
        rvItem.setAdapter(itemAdapter);

        setSupportActionBar(toolbar);
        setTitle("Tin tức hằng ngày");

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getListItem();

        navigationView.getMenu().getItem(0).setEnabled(true);
        navigationView.getMenu().getItem(6).getSubMenu().getItem(2).setVisible(false);
        navigationView.getMenu().getItem(1).setVisible(false);
        navigationView.getMenu().getItem(6).getSubMenu().getItem(3).setVisible(false);

        if (googleSignInAccount != null) {
            navigationView.getMenu().getItem(0).setTitle(googleSignInAccount.getDisplayName());
            navigationView.getMenu().getItem(0).setEnabled(false);
            navigationView.getMenu().getItem(6).getSubMenu().getItem(2).setVisible(false);
            navigationView.getMenu().getItem(6).getSubMenu().getItem(4).setVisible(true);
            if (signInAccount.getRole() == Role.ADMIN) {
                navigationView.getMenu().getItem(1).setVisible(true);
                navigationView.getMenu().getItem(6).getSubMenu().getItem(3).setVisible(true);
                navigationView.getMenu().getItem(6).getSubMenu().getItem(1).setVisible(false);
                navigationView.getMenu().getItem(6).getSubMenu().getItem(2).setVisible(true);

            }
            System.out.println(signInAccount.getRole());
            if (signInAccount.getRole() == Role.WRITER){
                navigationView.getMenu().getItem(6).getSubMenu().getItem(3).setVisible(true);
            }
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
             @Override
             public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                 switch (item.getItemId()){
                     case R.id.nav_login:
                         Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                         startActivity(intent);
                         break;

                     case R.id.nav_TheThao:
                         Intent intentTheThao = new Intent( MainActivity.this, TheThao.class);
                         startActivity(intentTheThao);
                         break;

                     case R.id.nav_PhapLuat:
                         Intent intentPhapLuat = new Intent( MainActivity.this, PhapLuat.class);
                         startActivity(intentPhapLuat);
                         break;

                     case R.id.nav_GiaoDuc:
                         Intent intentGiaoDuc = new Intent( MainActivity.this, GiaoDuc.class);
                         startActivity(intentGiaoDuc);
                         break;

                     case R.id.nav_report:
                         Intent intentReport = new Intent(MainActivity.this, Report1.class);
                         startActivity(intentReport);
                         break;

                     case R.id.nav_set:
                         Intent intentSetting = new Intent(MainActivity.this, Setting.class);
                         startActivity(intentSetting);
                         break;

                     case R.id.nav_logout:
                         googleSignInClient.signOut().addOnSuccessListener(new OnSuccessListener<Void>() {
                             @Override
                             public void onSuccess(Void unused) {
                                 SharedPrefs.getInstance().clear();
                                 FirebaseAuth.getInstance().signOut();
                                 Intent login = new Intent(getApplicationContext(), MainActivity.class);
                                 startActivity(login);
                                 finish();
                             }
                         });
                         break;
                     case R.id.nav_admin:
                         Intent intent1 = new Intent(MainActivity.this, Admin.class);
                         startActivity(intent1);
                         break;
                     case R.id.nav_write:
                         Intent intent2 = new Intent(MainActivity.this, Writer.class);
                         startActivity(intent2);
                         break;

                 }
                 return true;
             }
         });

    }

    @Override
    protected void onResume() {
        super.onResume();
        signInAccount = SharedPrefs.getInstance().get(LocalKey.LAST_SIGNED_IN_USER, Account.class);
    }

    private void getListItem(){
        paperRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        Item item = dataSnapshot.getValue(Item.class);
                        itemList.add(item);
                    }
                    itemAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Fails", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onBackPressed(){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }

}
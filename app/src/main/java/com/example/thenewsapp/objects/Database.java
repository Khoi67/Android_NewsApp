package com.example.thenewsapp.objects;

import com.example.thenewsapp.objects.enums.References;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Database {
    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    public Database() {
    }

    public DatabaseReference getRef(References reference) {
        return firebaseDatabase.getReference(reference.getValue());
    }
}

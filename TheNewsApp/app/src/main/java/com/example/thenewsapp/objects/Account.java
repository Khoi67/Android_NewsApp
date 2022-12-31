package com.example.thenewsapp.objects;

import com.example.thenewsapp.objects.enums.Role;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Account implements Serializable {
    private String id, email, name;
    private Role role = Role.BASIC_USER;
    private List<String> favoritePaper = new ArrayList<>();

    public Account(String id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public Account(String id, String email, String name, Role role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.role = role;
    }

    public Account(String id, String email, String name, Role role, List<String> favoritePaper) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.role = role;
        this.favoritePaper = favoritePaper;
    }

    public Account() {

    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getFavoritePaper() {
        return favoritePaper;
    }

    public void setFavoritePaper(List<String> favoritePaper) {
        this.favoritePaper = favoritePaper;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", role=" + role +
                ", favoritePaper=" + favoritePaper +
                '}';
    }
}

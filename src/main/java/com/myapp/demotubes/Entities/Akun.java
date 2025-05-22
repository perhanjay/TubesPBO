package com.myapp.demotubes.Entities;

import com.myapp.demotubes.Entities.Properties.Roles;

public abstract class Akun {
    private int id;
    private String username;
    private String password;
    private Roles role;
    private Integer id_warga;

    public Akun(int id, String username, String password, Roles role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Akun(int id, String username, String password, Roles role, int id_warga) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.id_warga = id_warga;
        this.password = password;
    }


    public Roles getRole() {
        return role;
    }


    public void setRole(Roles role) {
        this.role = role;
    }

    public Integer getIdWarga() {
        return id_warga;
    }

    public void setIdWarga(int id_warga) {
        this.id_warga = id_warga;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    abstract void showDashboard();

}

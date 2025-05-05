package com.myapp.demotubes.Entities;

import com.myapp.demotubes.Entities.Properties.Roles;

public class Akun extends Entity {
    private String password;
    private Roles role;
    private String id_warga = null;

    public Akun(int id, String name, String password, Roles role) {
        super(id, name);
        this.password = password;
        this.role = role;
    }

    public Akun(int id, String name, String password, Roles role, String id_warga) {
        super(id, name);
        this.password = password;
        this.role = role;
        this.id_warga = id_warga;
    }

    public Akun(){}

    public Roles getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public String getId_warga() {
        return id_warga;
    }

    public void setId_warga(String id_warga) {
        this.id_warga = id_warga;
    }
}

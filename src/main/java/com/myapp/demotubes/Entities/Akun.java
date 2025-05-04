package com.myapp.demotubes.Entities;

import com.myapp.demotubes.Entities.Properties.Roles;

public class Akun extends Entity {
    private String password;
    private Roles role;

    public Akun(int id, String name, String password, Roles role) {
        super(id, name);
        this.password = password;
        this.role = role;
    }
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
}

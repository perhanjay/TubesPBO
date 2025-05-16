package com.myapp.demotubes.Entities;

import com.myapp.demotubes.Entities.Properties.Roles;

public class User extends Akun{
    public User(int id, String username, String password, Roles role) {
        super(id, username, password, role);
    }

    public User(int id, String username, String password, Roles role, String id_warga) {
        super(id, username, password, role, id_warga);
    }

    @Override
    void showDashboard() {

    }
}

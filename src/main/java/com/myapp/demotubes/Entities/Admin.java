package com.myapp.demotubes.Entities;

import com.myapp.demotubes.Entities.Properties.Roles;

public class Admin extends Akun{
    public Admin(int id, String username, String password, Roles role) {
        super(id, username, password, role);
    }

    public Admin(int id, String username, String password, Roles role, int id_warga) {
        super(id, username, password, role, id_warga);
    }

    @Override
    void showDashboard() {
    }
}

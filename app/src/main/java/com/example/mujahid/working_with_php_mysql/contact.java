package com.example.mujahid.working_with_php_mysql;

/**
 * Created by Mujahid on 1/13/2018.
 */

public class contact {
    private String name, father_name, email, phone;

    public contact(String name, String father_name, String email, String phone) {
        this.name = name;
        this.father_name = father_name;
        this.email = email;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getFather_name() {
        return father_name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}

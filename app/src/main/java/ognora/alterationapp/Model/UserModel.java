package ognora.alterationapp.Model;

import java.io.Serializable;

public class UserModel implements Serializable {

    String _id;
    String name;
    String phone_no;
    String password;
    String[] address;
    String email;
    String role;
    String[] jwtToken;

    public UserModel() {

    }

    public UserModel(String name, String phone_no, String password, String email) {
        this.name = name;
        this.phone_no = phone_no;
        this.password = password;
        this.email = email;
    }

    public String get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public String getPassword() {
        return password;
    }

    public String[] getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String[] getJwtToken() {
        return jwtToken;
    }


}

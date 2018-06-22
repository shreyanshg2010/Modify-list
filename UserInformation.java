package co.shrey.contacts;

/**
 * Created by shrey on 12-06-2018.
 */

import java.io.Serializable;

public class UserInformation implements Serializable {
    private String name;
    private String phone;
    private String email;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private String key;


    public UserInformation() {

    }

    public UserInformation(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}

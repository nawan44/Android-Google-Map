package id.co.hendevane.universitas;

import java.io.Serializable;

public class University implements Serializable {

    public String name;
    public String address;
    public String web;

    public University(String name, String address, String web) {
        this.name = name;
        this.address = address;
        this.web = web;
    }

}

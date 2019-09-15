package com.example.muhammadumar.sawlection.data;

public class DataHasil {
    private String id, nama, hasil;

    public DataHasil() {
    }

    public DataHasil(String id, String nama, String hasil) {
        this.id = id;
        this.nama = nama;
        this.hasil = hasil;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getHasil() {
        return hasil;
    }

    public void setHasil(String hasil) {
        this.hasil = hasil;
    }

}
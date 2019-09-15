package com.example.muhammadumar.sawlection.data;

public class DataPanitia {
    String id_panitia;
    String id_user;
    String id_user_pil;
    String id_seleksi;

    public DataPanitia() {
    }

    public DataPanitia(String id_panitia, String id_user, String id_seleksi) {
        this.id_panitia = id_panitia;
        this.id_user = id_user;
        this.id_seleksi = id_seleksi;
    }

    public String getId_panitia() {
        return id_panitia;
    }

    public void setId_panitia(String id_panitia) {
        this.id_panitia = id_panitia;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getId_user_pil() {
        return id_user_pil;
    }

    public void setId_user_pil(String id_user_pil) {
        this.id_user_pil = id_user_pil;
    }

    public String getId_seleksi() {
        return id_seleksi;
    }

    public void setId_seleksi(String id_seleksi) {
        this.id_seleksi = id_seleksi;
    }
}
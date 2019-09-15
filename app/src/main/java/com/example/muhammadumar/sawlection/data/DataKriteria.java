package com.example.muhammadumar.sawlection.data;

public class DataKriteria {
    private String id, kriteria, tipe, bobot, id_seleksi;

    public DataKriteria() {
    }

    public DataKriteria(String id, String kriteria, String tipe, String bobot, String id_seleksi) {
        this.id = id;
        this.kriteria = kriteria;
        this.tipe = tipe;
        this.bobot = bobot;
        this.id_seleksi = id_seleksi;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKriteria() {
        return kriteria;
    }

    public void setKriteria(String kriteria) {
        this.kriteria = kriteria;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public String getBobot() {
        return bobot;
    }

    public void setBobot(String bobot) {
        this.bobot = bobot;
    }

    public String getId_seleksi() {
        return id_seleksi;
    }

    public void setId_seleksi(String id_seleksi) {
        this.id_seleksi = id_seleksi;
    }
}
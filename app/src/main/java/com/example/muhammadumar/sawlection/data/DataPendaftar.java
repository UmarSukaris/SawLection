package com.example.muhammadumar.sawlection.data;

public class DataPendaftar {
    private String id, nim, nama_pendaftar, id_seleksi;

    public DataPendaftar() {
    }

    public DataPendaftar(String id, String nim, String nama_pendaftar, String id_seleksi) {
        this.id = id;
        this.nim = nim;
        this.nama_pendaftar = nama_pendaftar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama_pendaftar() {
        return nama_pendaftar;
    }

    public void setNama_pendaftar(String nama_pendaftar) {
        this.nama_pendaftar = nama_pendaftar;
    }

    public String getId_seleksi() {
        return id_seleksi;
    }

    public void setId_seleksi(String id_seleksi) {
        this.id_seleksi = id_seleksi;
    }
}
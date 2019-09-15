package com.example.muhammadumar.sawlection.data;

public class DataSeleksi {
    private String id, nama_seleksi, id_laboratorium, id_laboratorium_pil, tahun_ajaran, kuota, deskripsi;

    public DataSeleksi() {
    }

    public DataSeleksi(String id, String nama_seleksi, String id_laboratorium, String tahun_ajaran, String kuota, String deskripsi) {
        this.id = id;
        this.nama_seleksi = nama_seleksi;
        this.id_laboratorium = id_laboratorium;
        this.tahun_ajaran = tahun_ajaran;
        this.kuota = kuota;
        this.deskripsi = deskripsi;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama_seleksi() {
        return nama_seleksi;
    }

    public void setNama_seleksi(String nama_seleksi) {
        this.nama_seleksi = nama_seleksi;
    }

    public String getId_laboratorium() {
        return id_laboratorium;
    }

    public void setId_laboratorium(String id_laboratorium) {
        this.id_laboratorium = id_laboratorium;
    }

    public String getId_laboratorium_pil() {
        return id_laboratorium_pil;
    }

    public void setId_laboratorium_pil(String id_laboratorium_pil) {
        this.id_laboratorium_pil = id_laboratorium_pil;
    }

    public String getTahun_ajaran() {
        return tahun_ajaran;
    }

    public void setTahun_ajaran(String tahun_ajaran) {
        this.tahun_ajaran = tahun_ajaran;
    }

    public String getKuota() {
        return kuota;
    }

    public void setKuota(String kuota) {
        this.kuota = kuota;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}
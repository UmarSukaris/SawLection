package com.example.muhammadumar.sawlection.data;

public class DataRangking {
    String id_rangking;
    String id_pendaftar;
    String id_pendaftar_pil;
    String id_kriteria;
    String id_kriteria_pil;
    String nilai;

    public String getNilai() {
        return nilai;
    }

    public void setNilai(String nilai) {
        this.nilai = nilai;
    }

    public DataRangking() {
    }

    public String getId_kriteria_pil() {
        return id_kriteria_pil;
    }

    public void setId_kriteria_pil(String id_kriteria_pil) {
        this.id_kriteria_pil = id_kriteria_pil;
    }


    public String getId_pendaftar_pil() {
        return id_pendaftar_pil;
    }

    public void setId_pendaftar_pil(String id_pendaftar_pil) {
        this.id_pendaftar_pil = id_pendaftar_pil;
    }

    public DataRangking(String id_rangking, String id_pendaftar, String id_kriteria, String nilai) {
        this.id_rangking = id_rangking;
        this.id_pendaftar = id_pendaftar;
        this.id_kriteria = id_kriteria;
        this.nilai = nilai;
    }

    public void setId_rangking(String id_rangking) {
        this.id_rangking = id_rangking;
    }

    public void setId_pendaftar(String id_pendaftar) {
        this.id_pendaftar = id_pendaftar;
    }

    public void setId_kriteria(String id_kriteria) {
        this.id_kriteria = id_kriteria;
    }

    public String getId_rangking() {
        return id_rangking;
    }

    public String getId_pendaftar() {
        return id_pendaftar;
    }

    public String getId_kriteria() {
        return id_kriteria;
    }
}
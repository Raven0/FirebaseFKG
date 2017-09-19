package com.preangerstd.firebasefkg;

/**
 * Created by azhzh on 9/16/2017.
 */

public class DataMahasiswa {

    private String agama;
    private String alamat;
    private String angkatan;
    private String golDarah;
    private String jenisKelamin;
    private String kewarganegaraan;
    private String namaDepan;
    private String namaBelakang;
    private String tglLahir;
    private String selectProdi;

    public DataMahasiswa() {

    }

    public DataMahasiswa(String agama, String alamat, String angkatan, String golDarah, String jenisKelamin, String kewarganegaraan, String namaDepan, String namaBelakang, String tglLahir, String selectProdi, String urlPhoto) {
        this.agama = agama;
        this.alamat = alamat;
        this.angkatan = angkatan;
        this.golDarah = golDarah;
        this.jenisKelamin = jenisKelamin;
        this.kewarganegaraan = kewarganegaraan;
        this.namaDepan = namaDepan;
        this.namaBelakang = namaBelakang;
        this.tglLahir = tglLahir;
        this.selectProdi = selectProdi;
        this.urlPhoto = urlPhoto;
    }

    public String getAgama() {
        return agama;
    }

    public void setAgama(String agama) {
        this.agama = agama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getAngkatan() {
        return angkatan;
    }

    public void setAngkatan(String angkatan) {
        this.angkatan = angkatan;
    }

    public String getGolDarah() {
        return golDarah;
    }

    public void setGolDarah(String golDarah) {
        this.golDarah = golDarah;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getKewarganegaraan() {
        return kewarganegaraan;
    }

    public void setKewarganegaraan(String kewarganegaraan) {
        this.kewarganegaraan = kewarganegaraan;
    }

    public String getNamaDepan() {
        return namaDepan;
    }

    public void setNamaDepan(String namaDepan) {
        this.namaDepan = namaDepan;
    }

    public String getNamaBelakang() {
        return namaBelakang;
    }

    public void setNamaBelakang(String namaBelakang) {
        this.namaBelakang = namaBelakang;
    }

    public String getTglLahir() {
        return tglLahir;
    }

    public void setTglLahir(String tglLahir) {
        this.tglLahir = tglLahir;
    }

    public String getSelectProdi() {
        return selectProdi;
    }

    public void setSelectProdi(String selectProdi) {
        this.selectProdi = selectProdi;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    private String urlPhoto;

}

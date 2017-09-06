package com.preangerstd.firebasefkg;

/**
 * Created by azhzh on 9/6/2017.
 */

public class DataMahasiswa {

    private String namaMahasiswa;
    private String jenisKelamin;
    private String image;

    public DataMahasiswa() {
    }

    public DataMahasiswa(String namaMahasiswa, String jenisKelamin, String image) {
        this.namaMahasiswa = namaMahasiswa;
        this.jenisKelamin = jenisKelamin;
        this.image = image;
    }

    public String getNamaMahasiswa() {
        return namaMahasiswa;
    }

    public void setNamaMahasiswa(String namaMahasiswa) {
        this.namaMahasiswa = namaMahasiswa;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

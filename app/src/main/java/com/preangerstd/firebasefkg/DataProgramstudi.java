package com.preangerstd.firebasefkg;

/**
 * Created by azhzh on 9/16/2017.
 */

public class DataProgramstudi {

    private String programStudi;
    private String ketua;
    private String sekretaris;
    private String konsentrasi;
    private String gelar;
    private String alamat;
    private String email;

    public DataProgramstudi(){

    }

    public DataProgramstudi(String programStudi, String ketua, String sekretaris, String konsentrasi, String gelar, String alamat, String email) {
        this.programStudi = programStudi;
        this.ketua = ketua;
        this.sekretaris = sekretaris;
        this.konsentrasi = konsentrasi;
        this.gelar = gelar;
        this.alamat = alamat;
        this.email = email;
    }

    public String getProgramStudi() {
        return programStudi;
    }

    public void setProgramStudi(String programStudi) {
        this.programStudi = programStudi;
    }

    public String getKetua() {
        return ketua;
    }

    public void setKetua(String ketua) {
        this.ketua = ketua;
    }

    public String getSekretaris() {
        return sekretaris;
    }

    public void setSekretaris(String sekretaris) {
        this.sekretaris = sekretaris;
    }

    public String getKonsentrasi() {
        return konsentrasi;
    }

    public void setKonsentrasi(String konsentrasi) {
        this.konsentrasi = konsentrasi;
    }

    public String getGelar() {
        return gelar;
    }

    public void setGelar(String gelar) {
        this.gelar = gelar;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}

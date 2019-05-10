package com.journaldev.bootifulmongodb.model;

public class DetailTps{
    private String nama;
    private String dapil[];
    private String url;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String[] getDapil() {
        return dapil;
    }

    public void setDapil(String[] dapil) {
        this.dapil = dapil;
    }
}

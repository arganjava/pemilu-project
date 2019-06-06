package com.journaldev.bootifulmongodb.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Document
public class Tps {

    @Id
    private String tpsId;

    private String provinsi;

    private String kota;

    private String kecamatan;

    private String kelurahan;

    private String url;

    private String voteId;

    private String nama;

    private String psu;

    private int selisih;

    @Field("ts")
    @JsonProperty("ts")
    private String time;

    @Field("chart")
    @JsonProperty("chart")
    private Chart chart;

    @Field("pemilih_j")
    @JsonProperty("pemilih_j")
    private int pemilihJumlah;

    @Field("suara_sah")
    @JsonProperty("suara_sah")
    private int suaraSah;

    @Field("pengguna_j")
    @JsonProperty("pengguna_j")
    private int penggunaJumlah;

    @Field("suara_total")
    @JsonProperty("suara_total")
    private int suaraTotal;

    @Field("suara_tidak_sah")
    @JsonProperty("suara_tidak_sah")
    private int suaraTidakSah;

    @Field("images")
    @JsonProperty("images")
    private String images[];

    private String generateAt;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getGenerateAt() {
        return generateAt;
    }

    public void setGenerateAt(String generateAt) {
        this.generateAt = generateAt;
    }

    private boolean isFraud;

    public boolean isFraud() {
        return isFraud;
    }

    public void setFraud(boolean fraud) {
        isFraud = fraud;
    }

    public String getTpsId() {
        return tpsId;
    }

    public void setTpsId(String tpsId) {
        this.tpsId = tpsId;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getKelurahan() {
        return kelurahan;
    }

    public void setKelurahan(String kelurahan) {
        this.kelurahan = kelurahan;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Chart getChart() {
        return chart;
    }

    public void setChart(Chart chart) {
        this.chart = chart;
    }

    public int getPemilihJumlah() {
        return pemilihJumlah;
    }

    public void setPemilihJumlah(int pemilihJumlah) {
        this.pemilihJumlah = pemilihJumlah;
    }

    public int getSuaraSah() {
        return suaraSah;
    }

    public void setSuaraSah(int suaraSah) {
        this.suaraSah = suaraSah;
    }

    public int getPenggunaJumlah() {
        return penggunaJumlah;
    }

    public void setPenggunaJumlah(int penggunaJumlah) {
        this.penggunaJumlah = penggunaJumlah;
    }

    public int getSuaraTotal() {
        return suaraTotal;
    }

    public void setSuaraTotal(int suaraTotal) {
        this.suaraTotal = suaraTotal;
    }

    public int getSuaraTidakSah() {
        return suaraTidakSah;
    }

    public void setSuaraTidakSah(int suaraTidakSah) {
        this.suaraTidakSah = suaraTidakSah;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public String getVoteId() {
        return voteId;
    }

    public void setVoteId(String voteId) {
        this.voteId = voteId;
    }

    public String getPsu() {
        return psu;
    }

    public void setPsu(String psu) {
        this.psu = psu;
    }

    public int getSelisih() {
        return selisih;
    }

    public void setSelisih(int selisih) {
        this.selisih = selisih;
    }

    public class Chart {
        @JsonProperty("21")
        private int _21;
        @JsonProperty("22")
        private int _22;

        public int totalChart() {
            return this._21 + this._22;
        }

        public int get_21() {
            return _21;
        }

        public void set_21(int _21) {
            this._21 = _21;
        }

        public int get_22() {
            return _22;
        }

        public void set_22(int _22) {
            this._22 = _22;
        }
    }

    @Override
    public String toString() {
        return "Tps{" +
                "provinsi='" + provinsi + '\'' +
                ", kota='" + kota + '\'' +
                ", kecamatan='" + kecamatan + '\'' +
                ", kelurahan='" + kelurahan + '\'' +
                ", nama='" + nama + '\'' +
                '}';
    }
}

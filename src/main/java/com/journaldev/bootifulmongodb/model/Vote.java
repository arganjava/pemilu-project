package com.journaldev.bootifulmongodb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Document
public class Vote {

    @Id
    private String voteId;

    private String provinsi;

    private String kota;

    private String kecamatan;

    private String kelurahan;

    private String url;

    private String[] urlTpss;

    private Map<String, DetailTps> detailTpsMap;

    public Map<String, DetailTps> getDetailTpsMap() {
        return detailTpsMap;
    }

    public void setDetailTpsMap(Map<String, DetailTps> detailTpsMap) {
        this.detailTpsMap = detailTpsMap;
    }

    public String getVoteId() {
        return voteId;
    }

    public void setVoteId(String voteId) {
        this.voteId = voteId;
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

    public String[] getUrlTpss() {
        return urlTpss;
    }

    public void setUrlTpss(String[] urlTpss) {
        this.urlTpss = urlTpss;
    }

}

package com.journaldev.bootifulmongodb.dal;

import com.journaldev.bootifulmongodb.model.DetailTps;
import com.journaldev.bootifulmongodb.model.Tps;
import com.journaldev.bootifulmongodb.model.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class VoteService {


    @Autowired
    private MongoTemplate mongoTemplate;

    private final String imageLink = "https://pemilu2019.kpu.go.id/img/c/";


    public void saveDinamic(Vote vote) {
        mongoTemplate.insert(vote, vote.getProvinsi());
    }

    public void updateDinamic(Vote vote) {
        CompletableFuture.runAsync(() -> {
            Query query = new Query();
            query.addCriteria(Criteria.where("voteId").is(vote.getVoteId()));
            Update update = new Update();
            update.set("urlTpss", vote.getUrlTpss());
            mongoTemplate.updateFirst(query, update, Vote.class, vote.getProvinsi());
        });
    }

    public void updateDinamicDetail(Vote vote) {
        CompletableFuture.runAsync(() -> {
            Query query = new Query();
            query.addCriteria(Criteria.where("voteId").is(vote.getVoteId()));
            Update update = new Update();
            update.set("detailTpsMap", vote.getDetailTpsMap());
            mongoTemplate.updateFirst(query, update, Vote.class, vote.getProvinsi());
        });
    }


    public void updateDinamicTps(Tps tps) {
        CompletableFuture.runAsync(() -> {
            Query query = new Query();
            query.addCriteria(Criteria.where("tpsId").is(tps.getTpsId()));
            Update update = new Update();
            update.set("generateAt", tps.getGenerateAt());
            update.set("chart", tps.getChart());
            update.set("pemilihJumlah", tps.getPemilihJumlah());
            update.set("suaraSah", tps.getSuaraSah());
            update.set("penggunaJumlah", tps.getPenggunaJumlah());
            update.set("suaraTotal", tps.getSuaraTotal());
            update.set("suaraTidakSah", tps.getSuaraTidakSah());
            update.set("time", tps.getTime());

            int total2122 = tps.getChart().totalChart();

            if(total2122 != tps.getSuaraSah()){
                update.set("isFraud", true);
                update.set("selisih", tps.getSuaraSah() - total2122);
            }

            String images[] = new String[tps.getImages().length];
            String urlSplit[] = tps.getUrl().split("/");
            String geLastSplit = urlSplit[urlSplit.length-1];
            geLastSplit = geLastSplit.replace(".json", "");
            String getFirstChar = geLastSplit.substring(0, 3);
            String getSecondChar = geLastSplit.substring(3, 6);
            for(int i =0; i < tps.getImages().length; i++){
                images[i] = imageLink +getFirstChar+"/"+getSecondChar+"/"+geLastSplit+"/"+tps.getImages()[i];
            }
            update.set("images", images);
            mongoTemplate.updateFirst(query, update, Tps.class, tps.getProvinsi()+"_TPS");
        });
    }

    public List<Vote> voteList(String collectionName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("urlTpss").exists(false));
        return mongoTemplate.find(query, Vote.class, collectionName);
    }

    public List<Vote> voteListDetail(String collectionName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("detailTpsMap").exists(false));
        return mongoTemplate.find(query, Vote.class, collectionName);
    }

    public List<Vote> getAll(String collectionName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("urlTpss").exists(true));
        return mongoTemplate.find(query, Vote.class, collectionName);
    }


    public void execute(String collectionName) {
        List<Vote> votes = getAll(collectionName);
        mongoTemplate.dropCollection(collectionName+"_TPS");
        int index = 0;
        for(Vote vote: votes){
            List<Tps> tpsList = vote.getDetailTpsMap().entrySet().stream().collect(Collectors.mapping(p -> {
                Tps tps = new Tps();
                tps.setVoteId(vote.getVoteId());
                tps.setKota(vote.getKota());
                tps.setKecamatan(vote.getKecamatan());
                tps.setProvinsi(vote.getProvinsi());
                tps.setKelurahan(vote.getKelurahan());
                DetailTps detailTps = p.getValue();
                tps.setUrl(detailTps.getUrl());
                tps.setNama(detailTps.getNama());
                return tps;
            }, Collectors.toList()));
            mongoTemplate.insert(tpsList, vote.getProvinsi()+"_TPS");
                index++;
            if(index == votes.size()){
                System.out.println(collectionName+" Done >>>>>>>>>>>>>>>!!!!!!!!!!!!");
            }
        }
    }

    public List<Tps> tpsList(String collectionName, String notInGenerateAt, int page) {
        Query query = new Query();
        query.addCriteria(Criteria.where("generateAt").ne(notInGenerateAt));
        query.with(new PageRequest(page, 1000));
        return mongoTemplate.find(query, Tps.class, collectionName+"_TPS");
    }


}

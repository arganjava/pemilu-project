package com.journaldev.bootifulmongodb.controller;

import com.journaldev.bootifulmongodb.dal.VoteRepository;
import com.journaldev.bootifulmongodb.dal.VoteService;
import com.journaldev.bootifulmongodb.model.DetailTps;
import com.journaldev.bootifulmongodb.model.Vote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;


@RestController
@CrossOrigin(origins = "https://pemilu2019.kpu.go.id")
@RequestMapping(value = "/vote")
public class VoteController {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private final VoteRepository voteRepository;

    @Autowired
    private VoteService voteService;

    public VoteController(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String addNewUsers(@RequestBody Vote vote) {
        LOG.info("Saving user.");
        voteService.saveDinamic(vote);
        return "oke";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@RequestBody Vote vote) {
        if (vote.getUrlTpss().length > 0) {
            CompletableFuture.runAsync(() -> {
                String newUrls[] = new String[vote.getUrlTpss().length];
                for (int i = 0; i < vote.getUrlTpss().length; i++) {
                    String tpsId = vote.getUrlTpss()[i];
                    String url = vote.getUrl().replace("https://pemilu2019.kpu.go.id/static/json/wilayah", "https://pemilu2019.kpu.go.id/static/json/hhcw/ppwp");
                    url = url.replace(".json", "/" + tpsId + ".json");
                    newUrls[i] = url;
                }
                vote.setUrlTpss(newUrls);
                LOG.info("Update user.", vote.getUrl());
                voteService.updateDinamic(vote);
                System.out.println(vote.getUrl());
            });
        }
        return "oke";
    }

    @RequestMapping(value = "/update-detail", method = RequestMethod.POST)
    public String updateDetail(@RequestBody Vote vote) {
        if (vote.getDetailTpsMap().size() > 0) {
            CompletableFuture.runAsync(() -> {
                Map<String, DetailTps> stringDetailTpsMap = new HashMap<>();
                for (Map.Entry<String, DetailTps> entry : vote.getDetailTpsMap().entrySet()){
                    String url = vote.getUrl().replace("https://pemilu2019.kpu.go.id/static/json/wilayah", "https://pemilu2019.kpu.go.id/static/json/hhcw/ppwp");
                    url = url.replace(".json", "/" + entry.getKey() + ".json");
                    DetailTps detailTps = entry.getValue();
                    detailTps.setUrl(url);
                    stringDetailTpsMap.put(entry.getKey(), detailTps);
                }
                vote.setDetailTpsMap(stringDetailTpsMap);
                LOG.info("Update updateDinamicDetail.", vote.getUrl());
                voteService.updateDinamicDetail(vote);
                System.out.println(vote.getUrl());
            });
        }
        return "oke";
    }

    @RequestMapping(value = "/list-detail", method = RequestMethod.GET)
    public ResponseEntity getListVoteDetail(@RequestParam(value = "collection") String collection) {
        LOG.info("Find User");
        return ResponseEntity.status(HttpStatus.OK).body(voteService.voteListDetail(collection));
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity getListVote(@RequestParam(value = "collection") String collection) {
        LOG.info("Find User");
        return ResponseEntity.status(HttpStatus.OK).body(voteService.voteList(collection));
    }

}
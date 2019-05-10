package com.journaldev.bootifulmongodb.controller;

import com.journaldev.bootifulmongodb.dal.VoteRepository;
import com.journaldev.bootifulmongodb.dal.VoteService;
import com.journaldev.bootifulmongodb.model.Tps;
import com.journaldev.bootifulmongodb.model.Vote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;


@RestController
@CrossOrigin(origins = "https://pemilu2019.kpu.go.id")
@RequestMapping(value = "/tps")
public class TpsController {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private final VoteRepository voteRepository;

    @Autowired
    private VoteService voteService;

    public TpsController(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String addNewUsers(@RequestBody Vote vote) {
        LOG.info("Saving user.");
        voteService.saveDinamic(vote);
        return "oke";
    }

    @RequestMapping(value = "/update-tps", method = RequestMethod.POST)
    public String update(@RequestBody Tps tps) {
        if (tps.getTpsId() != null && !StringUtils.isEmpty(tps.getTime())) {
            CompletableFuture.runAsync(() -> {
                voteService.updateDinamicTps(tps);
            });
        }
        return "oke";
    }

    @RequestMapping(value = "/generate", method = RequestMethod.GET)
    public ResponseEntity generate(@RequestParam(value = "collection") String collection) {
        LOG.info("generate tps");
        CompletableFuture.runAsync(() -> {
            voteService.execute(collection);
        });
        return ResponseEntity.status(HttpStatus.OK).body("generate");
    }

    @RequestMapping(value = "/list-notInGenerate", method = RequestMethod.GET)
    public ResponseEntity getListVote(@RequestParam(value = "collection") String collection,
                                      @RequestParam(value = "notGenerateAt") String notGenerateAt,
                                      @RequestParam(value = "page") int page) {
        LOG.info("Find List TPS");
        return ResponseEntity.status(HttpStatus.OK).body(voteService.tpsList(collection, notGenerateAt, page));
    }

}
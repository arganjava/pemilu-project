package com.journaldev.bootifulmongodb.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.journaldev.bootifulmongodb.dal.VoteRepository;
import com.journaldev.bootifulmongodb.dal.VoteService;
import com.journaldev.bootifulmongodb.model.Tps;
import com.journaldev.bootifulmongodb.model.Vote;
import com.journaldev.bootifulmongodb.spreadsheet.SheetsQuickstart;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@RestController
@CrossOrigin(origins = "https://pemilu2019.kpu.go.id")
@RequestMapping(value = "/tps")
public class TpsController {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private final VoteRepository voteRepository;

    ObjectMapper objectMapper = new ObjectMapper();

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
                                      @RequestParam(value = "notGenerateAt") String notGenerateAt) {
        LOG.info("Find List TPS");
        List<Tps> tpsList = voteService.tpsList(collection, notGenerateAt, 0);
        CompletableFuture.runAsync(() -> {
            String notGenerateAtObj = notGenerateAt;
            int count = 1;
            for(Tps tps : tpsList){
                    try {
                        HttpResponse httpResponse = Unirest.get(tps.getUrl()).asJson();
                        if(httpResponse.getStatus() == 200){
                            Tps realTps = objectMapper.readValue(httpResponse.getBody().toString(), Tps.class);
                            if(!StringUtils.isEmpty(realTps.getTime())){
                                realTps.setTpsId(tps.getTpsId());
                                realTps.setGenerateAt(notGenerateAtObj);
                                realTps.setUrl(tps.getUrl());
                                realTps.setProvinsi(tps.getProvinsi());
                                voteService.updateDinamicTps(realTps);
                            }
                        }
                    } catch (UnirestException e) {
                        LOG.error("Error", e.fillInStackTrace());
                    } catch (JsonParseException e) {
                        e.printStackTrace();
                    } catch (JsonMappingException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                count++;
                LOG.info( "count"+ count+" of " + tpsList.size()+" "+tps.toString());
            }
        });
        return ResponseEntity.status(HttpStatus.OK).body(tpsList.size());
    }

    @RequestMapping(value = "/generate-spreadsheet", method = RequestMethod.GET)
    public ResponseEntity generateSpreadsheet(@RequestParam(value = "collection") String collection,
                                   @RequestParam(value = "generatedAt", defaultValue = "") String generatedAt) throws Exception {
        List<Tps> listTps = voteService.tpsListForSpeadsheet(collection, generatedAt);
        CompletableFuture.runAsync(() -> {
            try {
                SheetsQuickstart.generateDataRows(collection, listTps);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }
        });
        return ResponseEntity.status(HttpStatus.OK).body(listTps.size());
    }

}
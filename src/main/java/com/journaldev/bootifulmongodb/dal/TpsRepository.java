package com.journaldev.bootifulmongodb.dal;

import com.journaldev.bootifulmongodb.model.Tps;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TpsRepository extends MongoRepository<Tps, String> {
    
}

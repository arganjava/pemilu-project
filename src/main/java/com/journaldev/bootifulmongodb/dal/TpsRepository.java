package com.journaldev.bootifulmongodb.dal;

import com.journaldev.bootifulmongodb.model.Tps;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TpsRepository extends MongoRepository<Tps, String> {

    List<Tps> findByIsFraud(boolean fraud);

}

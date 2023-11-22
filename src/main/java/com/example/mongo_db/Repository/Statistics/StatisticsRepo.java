package com.example.mongo_db.Repository.Statistics;

import com.example.mongo_db.Entity.Stistics.Statistic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
public interface StatisticsRepo extends MongoRepository<Statistic, String> {

    Statistic findByCurrentDate(LocalDate currentDate);



}

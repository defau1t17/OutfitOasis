package com.example.mongo_db.Entity.Stistics;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import lombok.Data;
import org.bson.BsonBinary;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Document(collection = "statistic")
@Data
public class Statistic {

    @Id
    private String id;

    private long quantityOfClients = 0;

    private long quantityOfProducers = 0;

    private long quantityOfEmployees = 0;

    private final LocalDate currentDate;

    private long quantityOfSiteVisitors = 0;

    private long quantityOfDailyNewClients = 0;

    public Statistic(LocalDate currentDate) {
        this.currentDate = currentDate;
    }


    public Statistic update(long quantityOfClients, long quantityOfProducers, long quantityOfEmployees, long quantityOfSiteVisitors, long quantityOfDailyNewClients) {
        this.quantityOfClients = quantityOfClients;
        this.quantityOfProducers = quantityOfProducers;
        this.quantityOfEmployees = quantityOfEmployees;
        this.quantityOfSiteVisitors = quantityOfSiteVisitors;
        this.quantityOfDailyNewClients = quantityOfDailyNewClients;
        return this;
    }

    public void addOneVisitor() {
        this.quantityOfSiteVisitors++;
    }

}

package com.example.mongo_db.Service.LogData;

import com.example.mongo_db.Entity.Logger.LogData;
import com.example.mongo_db.Repository.LogData.LogDataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class LoggerService {

    @Autowired
    private LogDataRepo logDataRepo;

    public void log(String client_id, String text) {
        LogData logData = null;
        if (logDataRepo.findByClientID(client_id).isPresent()) {
            logData = logDataRepo.findByClientID(client_id).get();
            logData.getLog().add("< " + LocalDateTime.now() + " > : " + text);
            logData.setClientID(client_id);
        } else {
            logData = new LogData();
            logData.getLog().add("< " + LocalDateTime.now() + " > : " + text);
            logData.setClientID(client_id);
        }
        save(logData);
    }

    public List<String> findLogListByClientID(String clientID) {
        return logDataRepo.findByClientID(clientID).get().getLog();
    }



    private void save(LogData logData) {
        logDataRepo.save(logData);
    }


}

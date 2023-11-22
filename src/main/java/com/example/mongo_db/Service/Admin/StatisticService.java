package com.example.mongo_db.Service.Admin;


import com.example.mongo_db.Entity.Role;
import com.example.mongo_db.Entity.Stistics.Statistic;
import com.example.mongo_db.Repository.ClientsRepoes.ClientsRepo;
import com.example.mongo_db.Repository.Statistics.StatisticsRepo;
import com.example.mongo_db.Service.Clients.ClientsService;
import com.example.mongo_db.Service.LogData.LoggerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@EnableScheduling
@Service
public class StatisticService {


    @Autowired
    private ClientsRepo clientsRepo;

    @Autowired
    private StatisticsRepo statisticsRepository;

    @Autowired
    private LoggerService loggerService;


    @Scheduled(cron = "59 59 23 * * ?")
    public void saveFinalDailyStatistic() {
        Statistic todayStatistic = statisticsRepository.findByCurrentDate(LocalDate.now());
        long allClients = clientsRepo.findAllByRole(Role.ROLE_CLIENT).size();
        long allProducers = clientsRepo.findAllByRole(Role.ROLE_PRODUCER).size();
        long allEmployees = clientsRepo.findAllByRole(Role.ROLE_EMPLOYEE).size();
        long webVisitors = todayStatistic.getQuantityOfSiteVisitors();
        long newDailyClients = clientsRepo.findAllByRegistrationDate(LocalDate.now()).size();
        Statistic finalStatistic = todayStatistic.update(allClients, allProducers, allEmployees, webVisitors, newDailyClients);
        statisticsRepository.save(finalStatistic);
        loggerService.log("SYSTEM", "daily statistics has been saved");
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void generateNewDailyStatistic() {
        Statistic statistic = new Statistic(LocalDate.now());
        statisticsRepository.save(statistic);
    }

    public void addNewVisitor() {
        Statistic todayStatistic = statisticsRepository.findByCurrentDate(LocalDate.now());
        todayStatistic.addOneVisitor();
        statisticsRepository.save(todayStatistic);
    }

    public Statistic calculateWeekStatistic() {
        Statistic statistic = new Statistic(LocalDate.now());
        int avgWeekClientsIncrement = 0;
        int avgWeekProducersIncrement = 0;
        int avgWeekEmployeesIncrement = 0;
        int avgWeekVisitorsIncrement = 0;
        int avgWeekNewDailyClientsIncrement = 0;

        LocalDate currentDayOfWeek = LocalDate.now();
        LocalDate day = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        int daysDiff = currentDayOfWeek.getDayOfMonth() - day.getDayOfMonth() + 1;
        while (!day.isAfter(currentDayOfWeek)) {
            Statistic dailyStatistics = statisticsRepository.findByCurrentDate(day);
            if (dailyStatistics != null) {
                avgWeekClientsIncrement += dailyStatistics.getQuantityOfClients();
                avgWeekProducersIncrement += dailyStatistics.getQuantityOfProducers();
                avgWeekEmployeesIncrement += dailyStatistics.getQuantityOfEmployees();
                avgWeekVisitorsIncrement += dailyStatistics.getQuantityOfSiteVisitors();
                avgWeekNewDailyClientsIncrement += dailyStatistics.getQuantityOfDailyNewClients();
            }
            day = day.plusDays(1);
        }
        statistic.setQuantityOfClients(avgWeekClientsIncrement / daysDiff);
        statistic.setQuantityOfEmployees(avgWeekEmployeesIncrement / daysDiff);
        statistic.setQuantityOfProducers(avgWeekProducersIncrement / daysDiff);
        statistic.setQuantityOfSiteVisitors(avgWeekVisitorsIncrement / daysDiff);
        statistic.setQuantityOfDailyNewClients(avgWeekNewDailyClientsIncrement / daysDiff);

        return statistic;
    }

    public Statistic getStatistic() {
        Statistic todayStatistic = statisticsRepository.findByCurrentDate(LocalDate.now());
        long allClients = clientsRepo.findAllByRole(Role.ROLE_CLIENT).size();
        long allProducers = clientsRepo.findAllByRole(Role.ROLE_PRODUCER).size();
        long allEmployees = clientsRepo.findAllByRole(Role.ROLE_EMPLOYEE).size();
        long webVisitors = todayStatistic.getQuantityOfSiteVisitors();
        long newDailyClients = clientsRepo.findAllByRegistrationDate(LocalDate.now()).size();
        Statistic finalStatistic = todayStatistic.update(allClients, allProducers, allEmployees, webVisitors, newDailyClients);
        statisticsRepository.save(finalStatistic);
        return todayStatistic;
    }
}

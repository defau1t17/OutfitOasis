package com.example.mongo_db.Controller.Admin;


import com.example.mongo_db.Entity.Client.Client;
import com.example.mongo_db.Entity.Requests.GlobalRequests;
import com.example.mongo_db.Entity.Stistics.Statistic;
import com.example.mongo_db.Service.Admin.AdminService;
import com.example.mongo_db.Service.Admin.StatisticService;
import com.example.mongo_db.Service.LogData.LoggerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/shop/administration/")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private StatisticService statisticService;

    @GetMapping("/panel")
    public String displayAdminPanelPage() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        return "/shop/admin/admin_panel_page";
    }

    @GetMapping("/requests")
    public String displayRequestPage(@RequestParam(required = false) Optional<Integer> page,
                                     @RequestParam(required = false) List<String> tag,
                                     Model model) {


        Page<GlobalRequests> requestsByParams = adminService.getRequestsByParams(page.orElse(0), tag);
        model.addAttribute("requests_data", requestsByParams);
        model.addAttribute("tags", tag);
        model.addAttribute("page", page.orElse(0));
        return "/shop/admin/admin_requests_page";
    }

    @GetMapping("/clients")
    public String displayClientsPage(@RequestParam(required = false) Optional<Integer> page,
                                     @RequestParam(required = false) List<String> roles,
                                     @RequestParam(required = false) String name,
                                     @RequestParam(required = false) String secondName,
                                     @RequestParam(required = false) Optional<Integer> age,
                                     @RequestParam(required = false) String gender,
                                     Model model) {
        Page<Client> clientsByParams = adminService.getClientsByParams(page.orElse(0), roles, name, secondName, age.orElse(0), gender);
        model.addAttribute("clients", clientsByParams);
        model.addAttribute("searchName", name);
        model.addAttribute("searchSecondName", secondName);
        model.addAttribute("searchAge", age);
        model.addAttribute("searchGender", gender);
        model.addAttribute("checked_roles", roles);
        model.addAttribute("page", page.orElse(0));
        return "/shop/admin/all_shop_clients";
    }


    @GetMapping("/statistics")
    public String displayStatisticsPage(Model model) {
        model.addAttribute("avgStatistic", statisticService.calculateWeekStatistic());
        model.addAttribute("currentStatistic", statisticService.getStatistic());
        model.addAttribute("weekVisitorsStatistic", statisticService.getWeekVisitorsStatistic());

        return "/shop/admin/global_statistics_page";
    }


}

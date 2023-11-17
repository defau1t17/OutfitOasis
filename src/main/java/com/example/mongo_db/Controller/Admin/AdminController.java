package com.example.mongo_db.Controller.Admin;


import com.example.mongo_db.Entity.Client.Client;
import com.example.mongo_db.Entity.Requests.GlobalRequests;
import com.example.mongo_db.Service.Admin.AdminService;
import com.example.mongo_db.Service.LogData.LoggerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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


    @GetMapping("/panel")
    public String displayAdminPanelPage() {
        return "/shop/admin/admin_panel_page";
    }

    @GetMapping("/requests")
    public String displayRequestPage(@RequestParam(required = false) Optional<Integer> page,
                                     @RequestParam(required = false) List<String> tag,
                                     Model model, HttpServletRequest request) {
        Page<GlobalRequests> requestsByParams = adminService.getRequestsByParams(page.orElse(0), tag);
        model.addAttribute("requests_data", requestsByParams);
        model.addAttribute("tags", tag);
        model.addAttribute("page", page.orElse(0));
        return "/shop/admin/admin_requests_page";
    }

    @GetMapping("/clients")
    public String displayClientsPage(@RequestParam(required = false) Optional<Integer> page,
                                     @RequestParam(required = false) List<String> roles,
                                     Model model) {
        Page<Client> clientsByParams = adminService.getClientsByParams(page.orElse(0), roles);
        model.addAttribute("clients", clientsByParams);
        model.addAttribute("checked_roles", roles);
        model.addAttribute("page", page.orElse(0));
        return "/shop/admin/all_shop_clients";
    }


}

package com.example.mongo_db.Controller.Admin;


import com.example.mongo_db.Entity.Requests.GlobalRequests;
import com.example.mongo_db.Service.Admin.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/shop/administration/admin/")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/panel/{id}")
    public String displayAdminPanelPage(@PathVariable(value = "id") String id) {
        return "/shop/admin/main_admin_panel_page";
    }

    @GetMapping("/panel/{id}/display/requests")
    public String displayRequestPage(@PathVariable(value = "id") String id, @RequestParam("page") Optional<Integer> page, Model model, HttpServletRequest request) {
        Page<GlobalRequests> requestsPage = adminService.getRequestsByPageNumber(page.orElse(0));
        model.addAttribute("requests_data", requestsPage);
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (Map.Entry<String, String[]> test : parameterMap.entrySet()) {
            System.out.println(Arrays.toString(test.getValue()));
        }

        return "/shop/admin/display_requests_page";
    }


}

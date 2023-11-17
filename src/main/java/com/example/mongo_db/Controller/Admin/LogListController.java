package com.example.mongo_db.Controller.Admin;

import com.example.mongo_db.Service.LogData.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LogListController {

    @Autowired
    private LoggerService loggerService;

    @RequestMapping("/shop/{id}/loglist")
    public String displayClientLogListPage(@PathVariable(value = "id") String id, Model model) {
        model.addAttribute("loglist", loggerService.findLogListByClientID(id));
        return "/shop/admin/loglist_page";
    }

}

package com.example.mongo_db.Controller.Admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shop/send")
public class RequestRegisterController {


    @RequestMapping(value = "/bug/report", method = RequestMethod.POST)
    public ResponseEntity getRepost() {
        System.out.println("report caught");

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}

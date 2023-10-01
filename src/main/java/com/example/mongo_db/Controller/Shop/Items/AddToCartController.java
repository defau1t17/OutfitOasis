package com.example.mongo_db.Controller.Shop.Items;

import com.example.mongo_db.Repository.ItemsRepoes.ItemRepo;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shop/catalog/add")
public class AddToCartController {

    @Autowired
    private ItemRepo itemRepo;

//    @GetMapping("/item/{id}")
//    public ResponseEntity tea(@PathVariable(value = "id") String id) {
//        return ResponseEntity.ok().build();
//    }

//    @CrossOrigin(origins = "*",methods = {RequestMethod.POST})
    @PostMapping("/item/{id}")
    public ResponseEntity test(@PathVariable(value = "id") String id, HttpServletRequest request) {
        System.out.println("we here");
        if (itemRepo.findShopItemById(id).isPresent() && request.getSession().getAttribute("global_client") != null) {
            System.out.println("found");
            return ResponseEntity.ok().build();
        } else return ResponseEntity.status(409).build();
    }

}

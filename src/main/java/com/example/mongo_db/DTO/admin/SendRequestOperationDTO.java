package com.example.mongo_db.DTO.admin;

import lombok.Data;

@Data
public class SendRequestOperationDTO {

    private String request_id;

    private String operation;
}

package com.example.mongo_db.RestExceptions;

public class NewItemValidationException extends Exception{

    public NewItemValidationException(){
        super("New Item Validation Failed");
    }
}

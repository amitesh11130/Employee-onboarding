package com.springbootapi.execption;

public class EmployeeNotFoundException extends Exception {

    public EmployeeNotFoundException(String msg){
        super(msg);
    }

}

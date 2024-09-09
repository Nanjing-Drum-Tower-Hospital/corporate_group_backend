//package com.njglyy.corporate_group_backend.controller;
//
//import com.njglyy.corporate_group_backend.entity.Result;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.http.HttpStatus;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//
//
//    @ExceptionHandler(Exception.class)
//    @ResponseBody
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public Result handleGeneralException(Exception e) {
//
//        return new Result(500, "Internal server error: " + e.getLocalizedMessage(), null);
//    }
//
//    // Additional specific exceptions can be added here
//}

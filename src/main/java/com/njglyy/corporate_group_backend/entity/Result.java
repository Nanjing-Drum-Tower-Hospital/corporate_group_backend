package com.njglyy.corporate_group_backend.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    //返回代码
    private Integer code;
    //信息
    private String message;
    //返回数据
    private Object data;
}

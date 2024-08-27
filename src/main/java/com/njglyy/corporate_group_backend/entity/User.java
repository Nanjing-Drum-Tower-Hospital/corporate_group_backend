package com.njglyy.corporate_group_backend.entity;

//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.annotation.TableId;
//import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@TableName("user")
@Data

@NoArgsConstructor
@AllArgsConstructor
public class User {
//    @TableId(type = IdType.AUTO)
    int id;
    String username;
    String password;
    String fullName;


}

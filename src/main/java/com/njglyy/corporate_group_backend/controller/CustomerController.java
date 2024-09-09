package com.njglyy.corporate_group_backend.controller;

import com.njglyy.corporate_group_backend.entity.Customer;
import com.njglyy.corporate_group_backend.entity.Item;
import com.njglyy.corporate_group_backend.entity.Result;
import com.njglyy.corporate_group_backend.entity.Supplier;
import com.njglyy.corporate_group_backend.mapper.CheckOutMapper;
import com.njglyy.corporate_group_backend.mapper.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class CustomerController {
    @Autowired
    private CustomerMapper customerMapper;
    @RequestMapping(value = "/addOrUpdateCustomer", method = RequestMethod.POST)
    public Result addOrUpdateCustomer
            (@RequestBody Customer customer
            ) {
        if(customer.getName() == null){
            return new Result(400,"客户姓名不能为空！",null);
        }
        else{
            if(customer.getId()!=0){
                customerMapper.updateCustomer(customer.getId(),customer.getName(),customer.getGender(),customer.getPhoneNumber(),customer.getEmailAddress(),customer.getRemark());
            }
            else{
                customerMapper.addCustomer(customer.getName(), customer.getGender(), customer.getPhoneNumber(), customer.getEmailAddress(), customer.getRemark());
            }

        }

        return new Result(200,"添加成功！",null);
    }

    @RequestMapping(value = "/deleteCustomerById", method = RequestMethod.GET)
    public Result deleteCustomer
            (@RequestParam(value = "id", required = false) int id
            ) {
        customerMapper.deleteCustomerById(id);
        return new Result(200, "删除成功！", null);
    }

    @RequestMapping(value = "/queryCustomerList", method = RequestMethod.GET)
    public Result queryCustomerList
            (@RequestParam(value = "currentPage", required = false) int currentPage,
             @RequestParam(value = "pageSize", required = false) int pageSize) {
        int offset = (currentPage - 1) * pageSize;
        List<Customer> customerList = customerMapper.queryCustomerList(offset, pageSize);
        return new Result(200, null, customerList);
    }

    @RequestMapping(value = "/queryCustomerListCount", method = RequestMethod.GET)
    public Result queryCustomerListCount
            (@RequestParam(value = "currentPage", required = false) int currentPage,
             @RequestParam(value = "pageSize", required = false) int pageSize) {
        int customerListCount = customerMapper.queryCustomerListCount();
        return new Result(200, null, customerListCount);
    }

    @RequestMapping(value = "/queryCustomerByIdOrName", method = RequestMethod.GET)
    public Result queryCustomerByIdOrName
            (@RequestParam(value = "input", required = false) String input
            ) {
        String inputStr = "%"+input+"%";
        int inputInt = 0;
        try {
            inputInt = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            inputInt = 0; // Set to 0 if conversion fails
        }
        List<Customer> customerList = customerMapper.queryCustomerByIdOrName(inputInt,inputStr);
        return new Result(200,null,customerList);
    }
}

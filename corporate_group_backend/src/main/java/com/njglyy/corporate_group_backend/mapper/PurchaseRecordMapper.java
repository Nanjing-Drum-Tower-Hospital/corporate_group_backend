package com.njglyy.corporate_group_backend.mapper;

import com.njglyy.corporate_group_backend.entity.PurchaseRecord;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Mapper
@Repository
public interface PurchaseRecordMapper {
    @Insert("INSERT INTO dbo.purchase_record_list " +
            " values(#{customerId},  #{purchaseDate},  #{itemBrand}, #{itemModel}, #{itemAmount}, #{machineNo}, " +
            "#{machineManufactureDate}, #{chargerAmount}, #{receiverAmount},  #{payerName}, " +
            "#{payerPhoneNumber}, #{paymentMethod}, #{paymentDate}, #{paymentAmount}, #{recommendationStaffId}, " +
            "#{recommendationStaffName}, #{remark})")

    void addPurchaseRecord(int customerId,LocalDate purchaseDate,  String itemBrand, String itemModel, BigDecimal itemAmount, String machineNo,
                            LocalDate machineManufactureDate, BigDecimal chargerAmount, BigDecimal receiverAmount,
                            String payerName, String payerPhoneNumber,
                           String paymentMethod, LocalDate paymentDate, BigDecimal paymentAmount, String recommendationStaffId,
                            String recommendationStaffName, String remark);

    @Delete("DELETE FROM purchase_record_list " +
            "WHERE id = #{id} ")
    void deletePurchaseRecordById(@Param("id") int id);

    @Update("UPDATE dbo.purchase_record_list " +
            " set customer_id=#{customerId}, purchase_date = #{purchaseDate}, item_brand = #{itemBrand}, item_model=#{itemModel},item_amount = #{itemAmount},machine_no=#{machineNo}," +
            "machine_manufacture_date = #{machineManufactureDate},charger_amount = #{chargerAmount},receiver_amount=#{receiverAmount}," +
            "payer_name = #{payerName},payer_phone_number=#{payerPhoneNumber},payment_method=#{paymentMethod},payment_date=#{paymentDate},payment_amount=#{paymentAmount}," +
            "recommendation_staff_id=#{recommendationStaffId},recommendation_staff_name=#{recommendationStaffName},remark=#{remark} " +
            "where id= #{id} ")
    void updatePurchaseRecord(int id, int customerId,LocalDate purchaseDate, String itemBrand, String itemModel, BigDecimal itemAmount, String machineNo,
                              LocalDate machineManufactureDate, BigDecimal chargerAmount, BigDecimal receiverAmount,
                               String payerName, String payerPhoneNumber,
                              String paymentMethod, LocalDate paymentDate, BigDecimal paymentAmount, String recommendationStaffId,
                              String recommendationStaffName, String remark);



    @Select("select * from purchase_record_list " +
            "order by id desc " +
            "OFFSET #{offset} ROWS FETCH NEXT #{pageSize} ROWS ONLY " )
    @Results({
            @Result(property = "id", column = "id", id = true),
            @Result(property = "customerId", column = "customer_id"),
            @Result(property = "purchaseDate", column = "purchase_date"),
            @Result(property = "itemBrand", column = "item_brand"),
            @Result(property = "itemModel", column = "item_model"),
            @Result(property = "itemAmount", column = "item_amount"),
            @Result(property = "machineNo", column = "machine_no"),
            @Result(property = "machineManufactureDate", column = "machine_manufacture_date"),
            @Result(property = "chargerAmount", column = "charger_amount"),
            @Result(property = "receiverAmount", column = "receiver_amount"),
            @Result(property = "payerName", column = "payer_name"),
            @Result(property = "payerPhoneNumber", column = "payer_phone_number"),
            @Result(property = "paymentMethod", column = "payment_method"),
            @Result(property = "paymentDate", column = "payment_date"),
            @Result(property = "paymentAmount", column = "payment_amount"),
            @Result(property = "recommendationStaffId", column = "recommendation_staff_id"),
            @Result(property = "recommendationStaffName", column = "recommendation_staff_name"),
            @Result(property = "remark", column = "remark"),
            @Result(property = "customer", column = "customer_id",
                    one = @One(select = "com.njglyy.corporate_group_backend.mapper.CustomerMapper.queryCustomerById")),
    })
    List<PurchaseRecord> queryPurchaseRecordList(int offset, int pageSize);

    @Select("SELECT COUNT(*) FROM purchase_record_list")
    int queryPurchaseRecordListCount();

    @Select("select * from purchase_record_list " +
            "where id=#{id} and customer_id=#{customerId}" )
    @Results({
            @Result(property = "id", column = "id", id = true),
            @Result(property = "customerId", column = "customer_id"),
            @Result(property = "purchaseDate", column = "purchase_date"),
            @Result(property = "itemBrand", column = "item_brand"),
            @Result(property = "itemModel", column = "item_model"),
            @Result(property = "itemAmount", column = "item_amount"),
            @Result(property = "machineNo", column = "machine_no"),
            @Result(property = "machineManufactureDate", column = "machine_manufacture_date"),
            @Result(property = "chargerAmount", column = "charger_amount"),
            @Result(property = "receiverAmount", column = "receiver_amount"),
            @Result(property = "payerName", column = "payer_name"),
            @Result(property = "payerPhoneNumber", column = "payer_phone_number"),
            @Result(property = "paymentMethod", column = "payment_method"),
            @Result(property = "paymentDate", column = "payment_date"),
            @Result(property = "paymentAmount", column = "payment_amount"),
            @Result(property = "recommendationStaffId", column = "recommendation_staff_id"),
            @Result(property = "recommendationStaffName", column = "recommendation_staff_name"),
            @Result(property = "remark", column = "remark"),
            @Result(property = "customer", column = "customer_id",
                    one = @One(select = "com.njglyy.corporate_group_backend.mapper.CustomerMapper.queryCustomerById")),
    })
    PurchaseRecord queryPurchaseRecordByIdAndCustomerId(int id, int customerId);



    @Select("select * from purchase_record_list " +
            "where id=#{id} " )
    @Results({
            @Result(property = "id", column = "id", id = true),
            @Result(property = "customerId", column = "customer_id"),
            @Result(property = "purchaseDate", column = "purchase_date"),
            @Result(property = "itemBrand", column = "item_brand"),
            @Result(property = "itemModel", column = "item_model"),
            @Result(property = "itemAmount", column = "item_amount"),
            @Result(property = "machineNo", column = "machine_no"),
            @Result(property = "machineManufactureDate", column = "machine_manufacture_date"),
            @Result(property = "chargerAmount", column = "charger_amount"),
            @Result(property = "receiverAmount", column = "receiver_amount"),
            @Result(property = "payerName", column = "payer_name"),
            @Result(property = "payerPhoneNumber", column = "payer_phone_number"),
            @Result(property = "paymentMethod", column = "payment_method"),
            @Result(property = "paymentDate", column = "payment_date"),
            @Result(property = "paymentAmount", column = "payment_amount"),
            @Result(property = "recommendationStaffId", column = "recommendation_staff_id"),
            @Result(property = "recommendationStaffName", column = "recommendation_staff_name"),
            @Result(property = "remark", column = "remark"),
            @Result(property = "customer", column = "customer_id",
                    one = @One(select = "com.njglyy.corporate_group_backend.mapper.CustomerMapper.queryCustomerById")),
    })
    PurchaseRecord queryPurchaseRecordById(int id);


    @Select("select * from purchase_record_list " +
            "join customer_list on purchase_record_list.customer_id = customer_list.id " +
            "where customer_list.name like #{customerName} " )
    @Results({
            @Result(property = "id", column = "id", id = true),
            @Result(property = "customerId", column = "customer_id"),
            @Result(property = "purchaseDate", column = "purchase_date"),
            @Result(property = "itemBrand", column = "item_brand"),
            @Result(property = "itemModel", column = "item_model"),
            @Result(property = "itemAmount", column = "item_amount"),
            @Result(property = "machineNo", column = "machine_no"),
            @Result(property = "machineManufactureDate", column = "machine_manufacture_date"),
            @Result(property = "chargerAmount", column = "charger_amount"),
            @Result(property = "receiverAmount", column = "receiver_amount"),
            @Result(property = "payerName", column = "payer_name"),
            @Result(property = "payerPhoneNumber", column = "payer_phone_number"),
            @Result(property = "paymentMethod", column = "payment_method"),
            @Result(property = "paymentDate", column = "payment_date"),
            @Result(property = "paymentAmount", column = "payment_amount"),
            @Result(property = "recommendationStaffId", column = "recommendation_staff_id"),
            @Result(property = "recommendationStaffName", column = "recommendation_staff_name"),
            @Result(property = "remark", column = "remark"),
            @Result(property = "customer", column = "customer_id",
                    one = @One(select = "com.njglyy.corporate_group_backend.mapper.CustomerMapper.queryCustomerById")),
    })
    List<PurchaseRecord> queryPurchaseRecordByCustomerName(String customerName);

}

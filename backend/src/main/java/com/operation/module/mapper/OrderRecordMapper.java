package com.operation.module.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.operation.module.entity.OrderRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface OrderRecordMapper extends BaseMapper<OrderRecord> {

    List<OrderRecord> selectRecordsByOrderId(@Param("orderId") Long orderId);

    int insertRecord(OrderRecord record);

    int deleteRecord(@Param("id") Long id);

    int deleteRecordsByOrderId(@Param("orderId") Long orderId);
}

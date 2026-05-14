package com.operation.module.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.operation.module.entity.MaintenanceOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface MaintenanceOrderMapper extends BaseMapper<MaintenanceOrder> {

    IPage<MaintenanceOrder> selectOrderPage(Page<MaintenanceOrder> page,
                                            @Param("projectId") Long projectId,
                                            @Param("orderNo") String orderNo,
                                            @Param("orderStatus") String orderStatus,
                                            @Param("priority") String priority,
                                            @Param("deviceId") Long deviceId,
                                            @Param("assigneeId") Long assigneeId);

    List<MaintenanceOrder> selectOrderList(@Param("projectId") Long projectId,
                                            @Param("orderStatus") String orderStatus);

    MaintenanceOrder selectOrderById(@Param("id") Long id);

    MaintenanceOrder selectOrderByNo(@Param("orderNo") String orderNo);

    int insertOrder(MaintenanceOrder order);

    int updateOrder(MaintenanceOrder order);

    int deleteOrder(@Param("id") Long id);

    int batchInsertOrder(@Param("list") List<MaintenanceOrder> list);
}

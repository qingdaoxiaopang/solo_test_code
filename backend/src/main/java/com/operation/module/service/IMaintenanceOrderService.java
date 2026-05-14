package com.operation.module.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.operation.module.entity.MaintenanceOrder;
import com.operation.module.entity.OrderRecord;
import java.util.List;

public interface IMaintenanceOrderService {

    IPage<MaintenanceOrder> getOrderPage(Page<MaintenanceOrder> page, Long projectId, String orderNo, String orderStatus, String priority, Long deviceId, Long assigneeId);

    List<MaintenanceOrder> getOrderList(Long projectId, String orderStatus);

    MaintenanceOrder getOrderById(Long id);

    MaintenanceOrder getOrderByNo(String orderNo);

    boolean createOrder(MaintenanceOrder order);

    boolean updateOrder(MaintenanceOrder order);

    boolean assignOrder(Long id, Long assigneeId, String assigneeName, String assigneePhone);

    boolean processOrder(Long id, String maintenanceContent, String maintenancePhotos);

    boolean completeOrder(Long id, String spareParts, String remark);

    boolean acceptOrder(Long id, String remark);

    boolean cancelOrder(Long id, String remark);

    List<OrderRecord> getOrderRecords(Long orderId);

    boolean uploadOrderAttachment(Long orderId, String attachmentUrl, String fileName);
}

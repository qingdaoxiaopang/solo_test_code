package com.operation.module.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.operation.module.entity.MaintenanceOrder;
import com.operation.module.entity.OrderRecord;
import com.operation.module.mapper.MaintenanceOrderMapper;
import com.operation.module.mapper.OrderRecordMapper;
import com.operation.module.service.IMaintenanceOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class MaintenanceOrderServiceImpl implements IMaintenanceOrderService {

    @Autowired
    private MaintenanceOrderMapper maintenanceOrderMapper;

    @Autowired
    private OrderRecordMapper orderRecordMapper;

    @Override
    public IPage<MaintenanceOrder> getOrderPage(Page<MaintenanceOrder> page, Long projectId, String orderNo, String orderStatus, String priority, Long deviceId, Long assigneeId) {
        return maintenanceOrderMapper.selectOrderPage(page, projectId, orderNo, orderStatus, priority, deviceId, assigneeId);
    }

    @Override
    public List<MaintenanceOrder> getOrderList(Long projectId, String orderStatus) {
        return maintenanceOrderMapper.selectOrderList(projectId, orderStatus);
    }

    @Override
    public MaintenanceOrder getOrderById(Long id) {
        return maintenanceOrderMapper.selectOrderById(id);
    }

    @Override
    public MaintenanceOrder getOrderByNo(String orderNo) {
        return maintenanceOrderMapper.selectOrderByNo(orderNo);
    }

    @Override
    @Transactional
    public boolean createOrder(MaintenanceOrder order) {
        order.setOrderNo(generateOrderNo());
        order.setOrderStatus("PENDING");
        order.setReportTime(LocalDateTime.now());
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        boolean result = maintenanceOrderMapper.insertOrder(order) > 0;
        if (result) {
            addOrderRecord(order.getId(), "CREATE", "创建工单: " + order.getTitle(), order.getReporterId(), order.getReporterName());
        }
        return result;
    }

    @Override
    @Transactional
    public boolean updateOrder(MaintenanceOrder order) {
        order.setUpdateTime(LocalDateTime.now());
        return maintenanceOrderMapper.updateOrder(order) > 0;
    }

    @Override
    @Transactional
    public boolean assignOrder(Long id, Long assigneeId, String assigneeName, String assigneePhone) {
        MaintenanceOrder order = maintenanceOrderMapper.selectOrderById(id);
        if (order == null) {
            return false;
        }
        order.setAssigneeId(assigneeId);
        order.setAssigneeName(assigneeName);
        order.setAssigneePhone(assigneePhone);
        order.setAssignTime(LocalDateTime.now());
        order.setOrderStatus("ASSIGNED");
        order.setUpdateTime(LocalDateTime.now());
        boolean result = maintenanceOrderMapper.updateOrder(order) > 0;
        if (result) {
            addOrderRecord(id, "ASSIGN", "分配给: " + assigneeName, assigneeId, assigneeName);
        }
        return result;
    }

    @Override
    @Transactional
    public boolean processOrder(Long id, String maintenanceContent, String maintenancePhotos) {
        MaintenanceOrder order = maintenanceOrderMapper.selectOrderById(id);
        if (order == null) {
            return false;
        }
        order.setMaintenanceContent(maintenanceContent);
        order.setMaintenancePhotos(maintenancePhotos);
        if (order.getActualStartTime() == null) {
            order.setActualStartTime(LocalDateTime.now());
        }
        order.setOrderStatus("PROCESSING");
        order.setUpdateTime(LocalDateTime.now());
        boolean result = maintenanceOrderMapper.updateOrder(order) > 0;
        if (result) {
            addOrderRecord(id, "PROCESS", "开始处理: " + maintenanceContent, order.getAssigneeId(), order.getAssigneeName());
        }
        return result;
    }

    @Override
    @Transactional
    public boolean completeOrder(Long id, String spareParts, String remark) {
        MaintenanceOrder order = maintenanceOrderMapper.selectOrderById(id);
        if (order == null) {
            return false;
        }
        order.setSpareParts(spareParts);
        order.setActualEndTime(LocalDateTime.now());
        order.setOrderStatus("COMPLETED");
        order.setUpdateTime(LocalDateTime.now());
        boolean result = maintenanceOrderMapper.updateOrder(order) > 0;
        if (result) {
            addOrderRecord(id, "COMPLETE", "完成维护" + (remark != null ? ": " + remark : ""), order.getAssigneeId(), order.getAssigneeName());
        }
        return result;
    }

    @Override
    @Transactional
    public boolean acceptOrder(Long id, String remark) {
        MaintenanceOrder order = maintenanceOrderMapper.selectOrderById(id);
        if (order == null) {
            return false;
        }
        order.setOrderStatus("ACCEPTED");
        order.setApproveTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        boolean result = maintenanceOrderMapper.updateOrder(order) > 0;
        if (result) {
            addOrderRecord(id, "ACCEPT", "验收通过" + (remark != null ? ": " + remark : ""), order.getApproverId(), order.getApproverName());
        }
        return result;
    }

    @Override
    @Transactional
    public boolean cancelOrder(Long id, String remark) {
        MaintenanceOrder order = maintenanceOrderMapper.selectOrderById(id);
        if (order == null) {
            return false;
        }
        order.setOrderStatus("CANCELLED");
        order.setUpdateTime(LocalDateTime.now());
        boolean result = maintenanceOrderMapper.updateOrder(order) > 0;
        if (result) {
            addOrderRecord(id, "CANCEL", "取消工单" + (remark != null ? ": " + remark : ""), order.getReporterId(), order.getReporterName());
        }
        return result;
    }

    @Override
    public List<OrderRecord> getOrderRecords(Long orderId) {
        return orderRecordMapper.selectRecordsByOrderId(orderId);
    }

    @Override
    @Transactional
    public boolean uploadOrderAttachment(Long orderId, String attachmentUrl, String fileName) {
        MaintenanceOrder order = maintenanceOrderMapper.selectOrderById(orderId);
        if (order == null) {
            return false;
        }
        String attachments = order.getMaintenancePhotos();
        if (attachments != null && !attachments.isEmpty()) {
            attachments = attachments + "," + attachmentUrl;
        } else {
            attachments = attachmentUrl;
        }
        order.setMaintenancePhotos(attachments);
        order.setUpdateTime(LocalDateTime.now());
        return maintenanceOrderMapper.updateOrder(order) > 0;
    }

    private void addOrderRecord(Long orderId, String recordType, String content, Long operatorId, String operatorName) {
        OrderRecord record = new OrderRecord();
        record.setOrderId(orderId);
        record.setRecordType(recordType);
        record.setRecordContent(content);
        record.setOperatorId(operatorId);
        record.setOperatorName(operatorName);
        record.setOperateTime(LocalDateTime.now());
        record.setCreateTime(LocalDateTime.now());
        record.setUpdateTime(LocalDateTime.now());
        orderRecordMapper.insertRecord(record);
    }

    private String generateOrderNo() {
        return "MO" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 4).toUpperCase();
    }
}

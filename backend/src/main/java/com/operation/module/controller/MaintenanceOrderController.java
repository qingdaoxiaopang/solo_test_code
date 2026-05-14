package com.operation.module.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.operation.module.entity.MaintenanceOrder;
import com.operation.module.entity.OrderRecord;
import com.operation.module.service.IMaintenanceOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api/maintenance/orders")
public class MaintenanceOrderController {

    @Autowired
    private IMaintenanceOrderService maintenanceOrderService;

    @GetMapping
    public Result<IPage<MaintenanceOrder>> getOrderPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Long projectId,
            @RequestParam(required = false) String orderNo,
            @RequestParam(required = false) String orderStatus,
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) Long deviceId,
            @RequestParam(required = false) Long assigneeId) {
        Page<MaintenanceOrder> page = new Page<>(pageNum, pageSize);
        IPage<MaintenanceOrder> result = maintenanceOrderService.getOrderPage(page, projectId, orderNo, orderStatus, priority, deviceId, assigneeId);
        return Result.success(result);
    }

    @PostMapping
    public Result<Boolean> createOrder(@RequestBody MaintenanceOrder order) {
        boolean result = maintenanceOrderService.createOrder(order);
        return Result.success(result);
    }

    @GetMapping("/{id}")
    public Result<MaintenanceOrder> getOrderById(@PathVariable Long id) {
        MaintenanceOrder order = maintenanceOrderService.getOrderById(id);
        return Result.success(order);
    }

    @PutMapping("/{id}")
    public Result<Boolean> updateOrder(@PathVariable Long id, @RequestBody MaintenanceOrder order) {
        order.setId(id);
        boolean result = maintenanceOrderService.updateOrder(order);
        return Result.success(result);
    }

    @PutMapping("/{id}/assign")
    public Result<Boolean> assignOrder(
            @PathVariable Long id,
            @RequestParam Long assigneeId,
            @RequestParam String assigneeName,
            @RequestParam(required = false) String assigneePhone) {
        boolean result = maintenanceOrderService.assignOrder(id, assigneeId, assigneeName, assigneePhone);
        return Result.success(result);
    }

    @PutMapping("/{id}/process")
    public Result<Boolean> processOrder(
            @PathVariable Long id,
            @RequestParam(required = false) String maintenanceContent,
            @RequestParam(required = false) String maintenancePhotos) {
        boolean result = maintenanceOrderService.processOrder(id, maintenanceContent, maintenancePhotos);
        return Result.success(result);
    }

    @PutMapping("/{id}/complete")
    public Result<Boolean> completeOrder(
            @PathVariable Long id,
            @RequestParam(required = false) String spareParts,
            @RequestParam(required = false) String remark) {
        boolean result = maintenanceOrderService.completeOrder(id, spareParts, remark);
        return Result.success(result);
    }

    @PutMapping("/{id}/accept")
    public Result<Boolean> acceptOrder(@PathVariable Long id, @RequestParam(required = false) String remark) {
        boolean result = maintenanceOrderService.acceptOrder(id, remark);
        return Result.success(result);
    }

    @PutMapping("/{id}/cancel")
    public Result<Boolean> cancelOrder(@PathVariable Long id, @RequestParam(required = false) String remark) {
        boolean result = maintenanceOrderService.cancelOrder(id, remark);
        return Result.success(result);
    }

    @GetMapping("/{id}/records")
    public Result<List<OrderRecord>> getOrderRecords(@PathVariable Long id) {
        List<OrderRecord> records = maintenanceOrderService.getOrderRecords(id);
        return Result.success(records);
    }

    @PostMapping("/{id}/attachments")
    public Result<Boolean> uploadOrderAttachment(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String attachmentUrl = uploadToMinIO(file, "maintenance/attachments");
        boolean result = maintenanceOrderService.uploadOrderAttachment(id, attachmentUrl, fileName);
        return Result.success(result);
    }

    private String uploadToMinIO(MultipartFile file, String bucket) {
        return "https://minio.example.com/" + bucket + "/" + System.currentTimeMillis() + "-" + file.getOriginalFilename();
    }
}

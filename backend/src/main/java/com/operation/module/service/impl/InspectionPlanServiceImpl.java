package com.operation.module.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.operation.module.entity.InspectionPlan;
import com.operation.module.entity.InspectionPlanDevice;
import com.operation.module.entity.InspectionPlanExecutor;
import com.operation.module.mapper.InspectionPlanDeviceMapper;
import com.operation.module.mapper.InspectionPlanExecutorMapper;
import com.operation.module.mapper.InspectionPlanMapper;
import com.operation.module.service.IInspectionPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class InspectionPlanServiceImpl implements IInspectionPlanService {

    @Autowired
    private InspectionPlanMapper inspectionPlanMapper;

    @Autowired
    private InspectionPlanDeviceMapper inspectionPlanDeviceMapper;

    @Autowired
    private InspectionPlanExecutorMapper inspectionPlanExecutorMapper;

    @Override
    public IPage<InspectionPlan> getPlanPage(Page<InspectionPlan> page, Long projectId, String planName, String enabledStatus, String completedStatus) {
        return inspectionPlanMapper.selectPlanPage(page, projectId, planName, enabledStatus, completedStatus);
    }

    @Override
    public List<InspectionPlan> getPlanList(Long projectId, String planName) {
        return inspectionPlanMapper.selectPlanList(projectId, planName);
    }

    @Override
    public InspectionPlan getPlanById(Long id) {
        InspectionPlan plan = inspectionPlanMapper.selectPlanById(id);
        if (plan != null) {
            plan.setDeviceCount(inspectionPlanDeviceMapper.countByPlanId(id));
            plan.setExecutorCount(inspectionPlanExecutorMapper.countByPlanId(id));
        }
        return plan;
    }

    @Override
    @Transactional
    public boolean createPlan(InspectionPlan plan) {
        plan.setCreateTime(LocalDateTime.now());
        plan.setUpdateTime(LocalDateTime.now());
        plan.setEnabledStatus("ENABLED");
        plan.setCompletedStatus("IN_PROGRESS");
        return inspectionPlanMapper.insertPlan(plan) > 0;
    }

    @Override
    @Transactional
    public boolean updatePlan(InspectionPlan plan) {
        plan.setUpdateTime(LocalDateTime.now());
        return inspectionPlanMapper.updatePlan(plan) > 0;
    }

    @Override
    @Transactional
    public boolean deletePlan(Long id) {
        inspectionPlanDeviceMapper.deleteDevicesByPlanId(id);
        inspectionPlanExecutorMapper.deleteExecutorsByPlanId(id);
        return inspectionPlanMapper.deletePlan(id) > 0;
    }

    @Override
    @Transactional
    public boolean addPlanDevice(Long planId, InspectionPlanDevice planDevice) {
        planDevice.setPlanId(planId);
        planDevice.setCreateTime(LocalDateTime.now());
        boolean result = inspectionPlanDeviceMapper.insertPlanDevice(planDevice) > 0;
        if (result) {
            updatePlanDeviceCount(planId);
        }
        return result;
    }

    @Override
    @Transactional
    public boolean removePlanDevice(Long planId, Long deviceId) {
        boolean result = inspectionPlanDeviceMapper.deletePlanDevice(planId, deviceId) > 0;
        if (result) {
            updatePlanDeviceCount(planId);
        }
        return result;
    }

    @Override
    public List<InspectionPlanDevice> getPlanDevices(Long planId) {
        return inspectionPlanDeviceMapper.selectDevicesByPlanId(planId);
    }

    @Override
    @Transactional
    public boolean addPlanExecutor(Long planId, InspectionPlanExecutor executor) {
        executor.setPlanId(planId);
        executor.setCreateTime(LocalDateTime.now());
        boolean result = inspectionPlanExecutorMapper.insertPlanExecutor(executor) > 0;
        if (result) {
            updatePlanExecutorCount(planId);
        }
        return result;
    }

    @Override
    @Transactional
    public boolean removePlanExecutor(Long planId, Long executorId) {
        boolean result = inspectionPlanExecutorMapper.deletePlanExecutor(planId, executorId) > 0;
        if (result) {
            updatePlanExecutorCount(planId);
        }
        return result;
    }

    @Override
    public List<InspectionPlanExecutor> getPlanExecutors(Long planId) {
        return inspectionPlanExecutorMapper.selectExecutorsByPlanId(planId);
    }

    private void updatePlanDeviceCount(Long planId) {
        InspectionPlan plan = inspectionPlanMapper.selectPlanById(planId);
        if (plan != null) {
            plan.setDeviceCount(inspectionPlanDeviceMapper.countByPlanId(planId));
            inspectionPlanMapper.updatePlan(plan);
        }
    }

    private void updatePlanExecutorCount(Long planId) {
        InspectionPlan plan = inspectionPlanMapper.selectPlanById(planId);
        if (plan != null) {
            plan.setExecutorCount(inspectionPlanExecutorMapper.countByPlanId(planId));
            inspectionPlanMapper.updatePlan(plan);
        }
    }
}

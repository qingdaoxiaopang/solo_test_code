package com.operation.module.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.operation.module.entity.InspectionPlan;
import com.operation.module.entity.InspectionPlanDevice;
import com.operation.module.entity.InspectionPlanExecutor;
import java.util.List;

public interface IInspectionPlanService {

    IPage<InspectionPlan> getPlanPage(Page<InspectionPlan> page, Long projectId, String planName, String enabledStatus, String completedStatus);

    List<InspectionPlan> getPlanList(Long projectId, String planName);

    InspectionPlan getPlanById(Long id);

    boolean createPlan(InspectionPlan plan);

    boolean updatePlan(InspectionPlan plan);

    boolean deletePlan(Long id);

    boolean addPlanDevice(Long planId, InspectionPlanDevice planDevice);

    boolean removePlanDevice(Long planId, Long deviceId);

    List<InspectionPlanDevice> getPlanDevices(Long planId);

    boolean addPlanExecutor(Long planId, InspectionPlanExecutor executor);

    boolean removePlanExecutor(Long planId, Long executorId);

    List<InspectionPlanExecutor> getPlanExecutors(Long planId);
}

package com.operation.module.config.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.operation.common.util.UserUtils;
import com.operation.common.web.Result;
import com.operation.module.config.entity.DeviceType;
import com.operation.module.config.entity.Topic;
import com.operation.module.config.entity.UserTopic;
import com.operation.module.config.service.ITopicService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/config/topics")
public class TopicController {

    private final ITopicService topicService;

    public TopicController(ITopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping
    public Result<List<Topic>> getTopicList() {
        Long projectId = UserUtils.getProjectId();
        LambdaQueryWrapper<Topic> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Topic::getProjectId, projectId)
               .orderByAsc(Topic::getSort)
               .orderByDesc(Topic::getCreateTime);
        List<Topic> list = topicService.list(wrapper);
        return Result.success(list);
    }

    @PostMapping
    public Result<Boolean> createTopic(@RequestBody Topic topic) {
        boolean success = topicService.saveTopic(topic);
        return Result.success(success);
    }

    @PutMapping("/{id}")
    public Result<Boolean> updateTopic(@PathVariable Long id, @RequestBody Topic topic) {
        topic.setId(id);
        boolean success = topicService.updateTopic(topic);
        return Result.success(success);
    }

    @DeleteMapping("/{id}")
    public Result<Boolean> deleteTopic(@PathVariable Long id) {
        boolean success = topicService.deleteTopic(id);
        return Result.success(success);
    }

    @GetMapping("/{id}/device-types")
    public Result<List<DeviceType>> getTopicDeviceTypes(@PathVariable Long id) {
        List<DeviceType> deviceTypes = topicService.getTopicDeviceTypes(id);
        return Result.success(deviceTypes);
    }

    @PostMapping("/{id}/device-types")
    public Result<Boolean> addTopicDeviceType(@PathVariable Long id, @RequestParam Long deviceTypeId) {
        boolean success = topicService.addTopicDeviceType(id, deviceTypeId);
        return Result.success(success);
    }

    @DeleteMapping("/{id}/device-types/{typeId}")
    public Result<Boolean> removeTopicDeviceType(@PathVariable Long id, @PathVariable Long typeId) {
        boolean success = topicService.removeTopicDeviceType(id, typeId);
        return Result.success(success);
    }

    @PostMapping("/init")
    public Result<Void> initTopics() {
        Long projectId = UserUtils.getProjectId();
        Long userId = UserUtils.getUserId();
        Long deptId = UserUtils.getDeptId();
        topicService.initTopics(projectId, userId, deptId);
        return Result.success(null);
    }

    @GetMapping("/users/{userId}/topics")
    public Result<List<Topic>> getUserTopics(@PathVariable Long userId) {
        List<Topic> topics = topicService.getUserTopics(userId);
        return Result.success(topics);
    }

    @PostMapping("/users/{userId}/topics")
    public Result<Boolean> addUserTopic(@PathVariable Long userId, @RequestParam Long topicId) {
        boolean success = topicService.addUserTopic(userId, topicId);
        return Result.success(success);
    }

    @DeleteMapping("/users/{userId}/topics/{topicId}")
    public Result<Boolean> removeUserTopic(@PathVariable Long userId, @PathVariable Long topicId) {
        boolean success = topicService.removeUserTopic(userId, topicId);
        return Result.success(success);
    }
}

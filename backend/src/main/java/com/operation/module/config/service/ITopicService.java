package com.operation.module.config.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.operation.module.config.entity.DeviceType;
import com.operation.module.config.entity.Topic;
import com.operation.module.config.entity.UserTopic;
import java.util.List;

public interface ITopicService extends IService<Topic> {

    boolean saveTopic(Topic topic);

    boolean updateTopic(Topic topic);

    boolean deleteTopic(Long id);

    List<DeviceType> getTopicDeviceTypes(Long topicId);

    boolean addTopicDeviceType(Long topicId, Long deviceTypeId);

    boolean removeTopicDeviceType(Long topicId, Long deviceTypeId);

    List<Topic> getUserTopics(Long userId);

    boolean addUserTopic(Long userId, Long topicId);

    boolean removeUserTopic(Long userId, Long topicId);

    void initTopics(Long projectId, Long userId, Long deptId);
}

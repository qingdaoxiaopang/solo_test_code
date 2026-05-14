package com.operation.module.config.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.operation.common.exception.BusinessException;
import com.operation.common.util.UserUtils;
import com.operation.module.config.entity.DeviceType;
import com.operation.module.config.entity.Topic;
import com.operation.module.config.entity.TopicDeviceType;
import com.operation.module.config.entity.UserTopic;
import com.operation.module.config.mapper.DeviceTypeMapper;
import com.operation.module.config.mapper.TopicDeviceTypeMapper;
import com.operation.module.config.mapper.TopicMapper;
import com.operation.module.config.mapper.UserTopicMapper;
import com.operation.module.config.service.ITopicService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicServiceImpl extends ServiceImpl<TopicMapper, Topic> implements ITopicService {

    private final TopicDeviceTypeMapper topicDeviceTypeMapper;
    private final UserTopicMapper userTopicMapper;
    private final DeviceTypeMapper deviceTypeMapper;

    public TopicServiceImpl(TopicDeviceTypeMapper topicDeviceTypeMapper,
                           UserTopicMapper userTopicMapper,
                           DeviceTypeMapper deviceTypeMapper) {
        this.topicDeviceTypeMapper = topicDeviceTypeMapper;
        this.userTopicMapper = userTopicMapper;
        this.deviceTypeMapper = deviceTypeMapper;
    }

    @Override
    @Transactional
    public boolean saveTopic(Topic topic) {
        topic.setProjectId(UserUtils.getProjectId());
        topic.setCreateBy(UserUtils.getUserId());
        topic.setCreateDeptId(UserUtils.getDeptId());
        checkCodeUnique(topic.getCode(), topic.getProjectId(), null);
        return this.save(topic);
    }

    @Override
    @Transactional
    public boolean updateTopic(Topic topic) {
        checkCodeUnique(topic.getCode(), topic.getProjectId(), topic.getId());
        topic.setUpdateBy(UserUtils.getUserId());
        return this.updateById(topic);
    }

    @Override
    @Transactional
    public boolean deleteTopic(Long id) {
        LambdaQueryWrapper<TopicDeviceType> deviceTypeWrapper = new LambdaQueryWrapper<>();
        deviceTypeWrapper.eq(TopicDeviceType::getTopicId, id);
        topicDeviceTypeMapper.delete(deviceTypeWrapper);

        LambdaQueryWrapper<UserTopic> userTopicWrapper = new LambdaQueryWrapper<>();
        userTopicWrapper.eq(UserTopic::getTopicId, id);
        userTopicMapper.delete(userTopicWrapper);

        return this.removeById(id);
    }

    @Override
    public List<DeviceType> getTopicDeviceTypes(Long topicId) {
        Topic topic = this.getById(topicId);
        if (topic == null) {
            throw new BusinessException("专题不存在");
        }
        LambdaQueryWrapper<TopicDeviceType> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TopicDeviceType::getTopicId, topicId);
        List<TopicDeviceType> relations = topicDeviceTypeMapper.selectList(wrapper);

        if (relations.isEmpty()) {
            return new ArrayList<>();
        }

        List<Long> typeIds = relations.stream()
                .map(TopicDeviceType::getDeviceTypeId)
                .collect(Collectors.toList());

        LambdaQueryWrapper<DeviceType> typeWrapper = new LambdaQueryWrapper<>();
        typeWrapper.eq(DeviceType::getProjectId, topic.getProjectId())
                   .in(DeviceType::getId, typeIds);
        return deviceTypeMapper.selectList(typeWrapper);
    }

    @Override
    @Transactional
    public boolean addTopicDeviceType(Long topicId, Long deviceTypeId) {
        Topic topic = this.getById(topicId);
        if (topic == null) {
            throw new BusinessException("专题不存在");
        }

        LambdaQueryWrapper<TopicDeviceType> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TopicDeviceType::getTopicId, topicId)
               .eq(TopicDeviceType::getDeviceTypeId, deviceTypeId);
        if (topicDeviceTypeMapper.selectCount(wrapper) > 0) {
            throw new BusinessException("该设备类型已关联到此专题");
        }

        TopicDeviceType relation = new TopicDeviceType();
        relation.setTopicId(topicId);
        relation.setDeviceTypeId(deviceTypeId);
        relation.setCreateBy(UserUtils.getUserId());
        relation.setCreateDeptId(UserUtils.getDeptId());
        return topicDeviceTypeMapper.insert(relation) > 0;
    }

    @Override
    @Transactional
    public boolean removeTopicDeviceType(Long topicId, Long deviceTypeId) {
        LambdaQueryWrapper<TopicDeviceType> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TopicDeviceType::getTopicId, topicId)
               .eq(TopicDeviceType::getDeviceTypeId, deviceTypeId);
        return topicDeviceTypeMapper.delete(wrapper) > 0;
    }

    @Override
    public List<Topic> getUserTopics(Long userId) {
        LambdaQueryWrapper<UserTopic> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserTopic::getUserId, userId);
        List<UserTopic> userTopics = userTopicMapper.selectList(wrapper);

        if (userTopics.isEmpty()) {
            return new ArrayList<>();
        }

        List<Long> topicIds = userTopics.stream()
                .map(UserTopic::getTopicId)
                .collect(Collectors.toList());

        LambdaQueryWrapper<Topic> topicWrapper = new LambdaQueryWrapper<>();
        topicWrapper.in(Topic::getId, topicIds);
        return this.list(topicWrapper);
    }

    @Override
    @Transactional
    public boolean addUserTopic(Long userId, Long topicId) {
        LambdaQueryWrapper<UserTopic> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserTopic::getUserId, userId)
               .eq(UserTopic::getTopicId, topicId);
        if (userTopicMapper.selectCount(wrapper) > 0) {
            throw new BusinessException("该用户已关联到此专题");
        }

        UserTopic userTopic = new UserTopic();
        userTopic.setUserId(userId);
        userTopic.setTopicId(topicId);
        userTopic.setCreateBy(UserUtils.getUserId());
        userTopic.setCreateDeptId(UserUtils.getDeptId());
        return userTopicMapper.insert(userTopic) > 0;
    }

    @Override
    @Transactional
    public boolean removeUserTopic(Long userId, Long topicId) {
        LambdaQueryWrapper<UserTopic> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserTopic::getUserId, userId)
               .eq(UserTopic::getTopicId, topicId);
        return userTopicMapper.delete(wrapper) > 0;
    }

    @Override
    @Transactional
    public void initTopics(Long projectId, Long userId, Long deptId) {
        List<String[]> presetTopics = Arrays.asList(
            new String[]{"GAS", "燃气"},
            new String[]{"DRAIN", "排水"},
            new String[]{"SUPPLY", "供水"},
            new String[]{"FIRE", "消防"},
            new String[]{"BRIDGE", "桥梁"},
            new String[]{"HEAT", "热力"}
        );

        for (String[] topicData : presetTopics) {
            String code = topicData[0];
            String name = topicData[1];

            LambdaQueryWrapper<Topic> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Topic::getCode, code)
                   .eq(Topic::getProjectId, projectId);
            if (this.count(wrapper) == 0) {
                Topic topic = new Topic();
                topic.setProjectId(projectId);
                topic.setCode(code);
                topic.setName(name);
                topic.setDescription(name + "专题");
                topic.setStatus("ACTIVE");
                topic.setCreateBy(userId);
                topic.setCreateDeptId(deptId);
                this.save(topic);
            }
        }
    }

    private void checkCodeUnique(String code, Long projectId, Long excludeId) {
        LambdaQueryWrapper<Topic> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Topic::getCode, code)
               .eq(Topic::getProjectId, projectId);
        if (excludeId != null) {
            wrapper.ne(Topic::getId, excludeId);
        }
        if (this.count(wrapper) > 0) {
            throw new BusinessException("专题编码已存在");
        }
    }
}

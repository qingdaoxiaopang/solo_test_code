package com.operation.module.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.operation.module.entity.ProjectDocument;
import com.operation.module.mapper.ProjectDocumentMapper;
import com.operation.module.service.IProjectDocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectDocumentServiceImpl implements IProjectDocumentService {

    private final ProjectDocumentMapper projectDocumentMapper;

    @Value("${minio.bucket-name:project-documents}")
    private String bucketName;

    @Value("${minio.endpoint:http://localhost:9000}")
    private String endpoint;

    @Override
    public List<ProjectDocument> listByProjectId(Long projectId) {
        LambdaQueryWrapper<ProjectDocument> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProjectDocument::getProjectId, projectId);
        wrapper.eq(ProjectDocument::getDeleted, 0);
        wrapper.orderByDesc(ProjectDocument::getCreateTime);
        return projectDocumentMapper.selectList(wrapper);
    }

    @Override
    public ProjectDocument getById(Long id) {
        return projectDocumentMapper.selectById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(ProjectDocument document) {
        document.setCreateTime(LocalDateTime.now());
        document.setUpdateTime(LocalDateTime.now());
        document.setDeleted(0);
        return projectDocumentMapper.insert(document) > 0;
    }

    @Override
    public boolean deleteById(Long id) {
        LambdaUpdateWrapper<ProjectDocument> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(ProjectDocument::getId, id);
        wrapper.set(ProjectDocument::getDeleted, 1);
        wrapper.set(ProjectDocument::getUpdateTime, LocalDateTime.now());
        return projectDocumentMapper.update(null, wrapper) > 0;
    }

    @Override
    public String uploadFile(Long projectId, String fileName, String contentType, byte[] fileBytes, Long userId) {
        String objectName = generateObjectName(projectId, fileName);
        try {
            String fileUrl = uploadToMinio(objectName, contentType, fileBytes);
            ProjectDocument document = new ProjectDocument();
            document.setProjectId(projectId);
            document.setName(fileName);
            document.setFileUrl(fileUrl);
            document.setFileType(contentType);
            document.setFileSize((long) fileBytes.length);
            document.setUploadBy(userId);
            save(document);
            return fileUrl;
        } catch (Exception e) {
            log.error("文件上传失败", e);
            throw new RuntimeException("文件上传失败: " + e.getMessage());
        }
    }

    private String generateObjectName(Long projectId, String fileName) {
        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        String extension = "";
        if (fileName.contains(".")) {
            extension = fileName.substring(fileName.lastIndexOf("."));
        }
        return String.format("projects/%d/%s_%s%s", projectId, dateStr, uuid, extension);
    }

    private String uploadToMinio(String objectName, String contentType, byte[] data) {
        return endpoint + "/" + bucketName + "/" + objectName;
    }
}

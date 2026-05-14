package com.operation.module.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.operation.common.core.domain.Result;
import com.operation.module.entity.ProjectDocument;
import com.operation.module.service.IProjectDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@RestController
@RequestMapping("/api/projects/{projectId}/documents")
@RequiredArgsConstructor
public class PrjProjectDocumentController {

    private final IProjectDocumentService projectDocumentService;

    @Value("${minio.local-path:/tmp/minio}")
    private String localPath;

    @GetMapping
    public Result<Object> list(@PathVariable Long projectId) {
        return Result.success(projectDocumentService.listByProjectId(projectId));
    }

    @PostMapping
    public Result<String> upload(@PathVariable Long projectId,
                                  @RequestParam("file") MultipartFile file,
                                  @RequestParam(value = "userId", defaultValue = "0") Long userId) {
        try {
            String fileName = file.getOriginalFilename();
            String contentType = file.getContentType();
            byte[] fileBytes = file.getBytes();
            String fileUrl = projectDocumentService.uploadFile(projectId, fileName, contentType, fileBytes, userId);
            return Result.success("文件上传成功", fileUrl);
        } catch (Exception e) {
            return Result.error("文件上传失败: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success(projectDocumentService.deleteById(id));
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> download(@PathVariable Long id) {
        ProjectDocument document = projectDocumentService.getById(id);
        if (document == null) {
            return ResponseEntity.notFound().build();
        }
        try {
            Path filePath = Paths.get(localPath).resolve(document.getFileUrl());
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getName() + "\"")
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

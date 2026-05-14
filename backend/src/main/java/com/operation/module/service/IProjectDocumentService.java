package com.operation.module.service;

import com.operation.module.entity.ProjectDocument;

import java.util.List;

public interface IProjectDocumentService {

    List<ProjectDocument> listByProjectId(Long projectId);

    ProjectDocument getById(Long id);

    boolean save(ProjectDocument document);

    boolean deleteById(Long id);

    String uploadFile(Long projectId, String fileName, String contentType, byte[] fileBytes, Long userId);
}

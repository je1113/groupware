package com.infowise.demo.Service;

import com.infowise.demo.Entity.Project;
import com.infowise.demo.Enum.ProjectSearchType;
import com.infowise.demo.Repository.ProjectRepository;
import com.infowise.demo.dto.Header;
import com.infowise.demo.dto.MemberDTO;
import com.infowise.demo.dto.ProjectDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Transactional(readOnly = true)
    public Page<ProjectDTO> searchProject(ProjectSearchType searchType, String searchKeyword, Pageable pageable){
        if(searchKeyword == null|| searchKeyword.isBlank()){
            return projectRepository.findAll(pageable).map(ProjectDTO::fromEntity);
        }
        return switch (searchType){
            case NAME -> projectRepository.findByNameContaining(searchKeyword, pageable).map(ProjectDTO::fromEntity);
        };
    }

    @Transactional(readOnly = true)
    public ProjectDTO read(Long projectId){
        return projectRepository.findById(projectId)
                .map(ProjectDTO::fromEntity)
                .orElseThrow(()->new EntityNotFoundException("해당 프로젝트가 없습니다."));
    }

    public Header<ProjectDTO> create(ProjectDTO dto){
        Project newProject = projectRepository.save(dto.toEntity());
        return Header.OK(ProjectDTO.fromEntity(newProject));
    }

    public Header<ProjectDTO> update(Long projectId, ProjectDTO dto){
        try{
            Project project = projectRepository.getReferenceById(projectId);
            if (dto.name() != null) { project.setName(dto.name());}
            if (dto.startDate() != null) { project.setStartDate(dto.startDate());}
            if (dto.endDate() != null) { project.setEndDate(dto.endDate());}
            if (dto.isUse() != null) { project.setIsUse(dto.isUse());}
            return Header.OK();
        }catch (EntityNotFoundException e){
            log.warn("프로젝트 정보 수정 실패 - dto:{}",dto);
            return Header.ERROR("프로젝트정보 수정 실패");
        }
    }

    public Header delete(Long projectId){
        Optional<Project> project = projectRepository.findById(projectId);
        return project.map(e ->{
            projectRepository.delete(e);
            return Header.OK();
        }).orElseGet(()->Header.ERROR("해당 프로잭트가 없습니다."));
    }
}

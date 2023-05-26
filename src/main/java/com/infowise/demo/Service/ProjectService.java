package com.infowise.demo.Service;

import com.infowise.demo.Entity.Pic;
import com.infowise.demo.Entity.Project;
import com.infowise.demo.Entity.Work;
import com.infowise.demo.Enum.ProjectSearchType;
import com.infowise.demo.Repository.PicRepository;
import com.infowise.demo.Repository.ProjectRepository;
import com.infowise.demo.Repository.WorkRepository;
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
import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final WorkRepository workRepository;
    private final PicRepository picRepository;

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
            if (dto.costType() != null){ project.setCostType(dto.costType());}
            return Header.OK();
        }catch (EntityNotFoundException e){
            log.warn("프로젝트 정보 수정 실패 - dto:{}",dto);
            return Header.ERROR("프로젝트정보 수정 실패");
        }
    }

    public Header delete(Long projectId){
        Optional<Project> projectOptional = projectRepository.findById(projectId);
        if(projectOptional.isPresent()){
            Project project = projectOptional.get();
            List<Work> works = workRepository.findByProject(project);
            List<Pic> pics = picRepository.findAllByProject(project);

            if((works ==null || works.isEmpty()) &&(pics ==null || pics.isEmpty())){
                projectRepository.delete(project);
                return Header.OK();
            }else{
                return Header.ERROR("해당 프로젝트의 담당 및 공수정보를 먼저 삭제해야합니다.");
            }
        }else{
            return Header.ERROR("해당 프로젝트가 없습니다.");
        }
    }
}

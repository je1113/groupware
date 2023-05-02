package com.infowise.demo.Service;

import com.infowise.demo.Entity.Member;
import com.infowise.demo.Entity.Project;
import com.infowise.demo.Entity.Work;
import com.infowise.demo.Enum.WorkSearchType;
import com.infowise.demo.Repository.MemberRepository;
import com.infowise.demo.Repository.ProjectRepository;
import com.infowise.demo.Repository.WorkRepository;
import com.infowise.demo.dto.Header;
import com.infowise.demo.dto.MemberDTO;
import com.infowise.demo.dto.ProjectDTO;
import com.infowise.demo.dto.WorkDTO;
import com.infowise.demo.req.WorkReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class WorkService {

    private final WorkRepository workRepository;
    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;

    private LocalDate getLocalDateFromYearMonthWeek(Integer year, Integer month, Integer week) {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        return LocalDate.of(year, month, 1)
                .with(weekFields.weekOfYear(), week)
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    }

    @Transactional(readOnly = true)
    public Page<WorkDTO> searchWork(WorkSearchType searchType, String searchKeyword, Pageable pageable){
        if(searchKeyword == null|| searchKeyword.isBlank()) {
            return workRepository.findAll(pageable).map(WorkDTO::fromEntity);
        }
        return switch (searchType){
            case MEMBER -> workRepository.findByMemberNameContaining(searchKeyword,pageable).map(WorkDTO::fromEntity);
            case PROJECT -> workRepository.findByProjectNameContaining(searchKeyword,pageable).map(WorkDTO::fromEntity);
        };
    }

    @Transactional(readOnly = true)
    public WorkDTO read(Long workIdx){
        return workRepository.findById(workIdx)
                .map(WorkDTO::fromEntity)
                .orElseThrow(()-> new EntityNotFoundException("해당 공수 정보 없음"));
    }

    public Header<WorkDTO> create(WorkDTO dto){
        Member member = memberRepository.findById(dto.memberDTO().idx()).get();
        Project project = projectRepository.findById(dto.projectDTO().idx()).get();
        Work newWork = workRepository.save(dto.toEntity(member,project));
        return Header.OK(WorkDTO.fromEntity(newWork));
    }

    public List<Header<WorkDTO>> createBulk(List<WorkReq> reqList, MemberDTO memberDTO){
        List<Header<WorkDTO>> headers = new ArrayList<>();
        reqList.forEach(
                workReq -> headers.add(create(workReq.toDto(memberDTO)))
        );
        return headers;
    }

    public Header<WorkDTO> update(Long idx, WorkDTO dto){
        try{
            Work work = workRepository.getReferenceById(dto.idx());
            if(dto.costType()!=null){work.setCostType(dto.costType());}
            if(dto.gongSoo()!=null){work.setGongSoo(dto.gongSoo());}
            return Header.OK();
        }catch (EntityNotFoundException e){
            log.warn("공수정보 입력 실패");
            return Header.ERROR("공수정보 입력 실패");
        }
    }
    public List<Header<WorkDTO>> updateBulk(List<WorkDTO> dtoList){
        List<Header<WorkDTO>> headers = new ArrayList<>();
        dtoList.forEach(
                workDTO -> headers.add(update(workDTO.idx(), workDTO))
        );
        return headers;
    }

    public Header delete (Long workId){
        Optional<Work> work = workRepository.findById(workId);
        return work.map(e ->{
            workRepository.delete(e);
            return Header.OK();
        }).orElseGet(()->Header.ERROR("해당 공수정보가 없습니다."));
    }

    public List<Header<WorkDTO>> deleteBulk(List<WorkDTO> dtoList){
        List<Header<WorkDTO>> headers = new ArrayList<>();
        dtoList.forEach(
                workDTO -> headers.add(delete(workDTO.idx()))
        );
        return headers;
    }

    public List<WorkDTO> check(Long memberIdx, Integer year, Integer month, Integer week){
        Member member = memberRepository.findById(memberIdx).get();
        return workRepository.findAllByMemberAndYearAndMonthAndWeek(member, year, month, week)
                .stream().map(WorkDTO::fromEntity).toList();
    }
}

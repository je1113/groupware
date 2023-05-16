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
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    @Transactional(readOnly = true)
    public List<WorkDTO> readList(Long workIdx){
        Work work = workRepository.findById(workIdx).get();
        return workRepository.findAllByMemberAndYearAndMonthAndWeek(work.getMember(),work.getYear(),work.getMonth(),work.getWeek())
                .stream().map(WorkDTO::fromEntity).toList();
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
    public List<Header<WorkDTO>> updateBulk( List<WorkReq> workReqs, MemberDTO memberDTO){
        List<Header<WorkDTO>> headers = new ArrayList<>();
        workReqs.forEach(
                workReq -> headers.add(update(workReq.idx(), workReq.toDto(memberDTO)))
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


    // excel export
    public Workbook generateExcelFile() {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("Work Data");

        List<Work> works = workRepository.findAll(); // Work 데이터 조회 (예시로 가정)
        DecimalFormat decimalFormat = new DecimalFormat("#.##"); //공수 소수2째 자리까지

        int rowIdx = 0;
        Row headerRow = sheet.createRow(rowIdx++);
        headerRow.createCell(0).setCellValue("사용자 이름");
        headerRow.createCell(1).setCellValue("원가 구분");
        headerRow.createCell(2).setCellValue("프로젝트 이름");
        headerRow.createCell(3).setCellValue("프로젝트 시작날짜");
        headerRow.createCell(4).setCellValue("프로젝트 종료날짜");
        headerRow.createCell(5).setCellValue("년도");
        headerRow.createCell(6).setCellValue("월");
        headerRow.createCell(7).setCellValue("주");
        headerRow.createCell(8).setCellValue("공수");

        for (Work work : works) {
            Row row = sheet.createRow(rowIdx++);

            row.createCell(0).setCellValue(work.getMember().getName());
            row.createCell(1).setCellValue(work.getCostType().getDescription());
            row.createCell(2).setCellValue(work.getProject().getName());
            row.createCell(3).setCellValue(work.getProject().getStartDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")));
            row.createCell(4).setCellValue(work.getProject().getEndDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")));
            row.createCell(5).setCellValue(work.getYear());
            row.createCell(6).setCellValue(work.getMonth());
            row.createCell(7).setCellValue(work.getWeek());
            row.createCell(8).setCellValue(Double.parseDouble(decimalFormat.format(work.getGongSoo())));
        }

        return workbook;
    }

}

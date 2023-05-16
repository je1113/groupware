package com.infowise.demo.controller.API;

import com.infowise.demo.Service.MemberService;
import com.infowise.demo.Service.PicService;
import com.infowise.demo.Service.WorkService;
import com.infowise.demo.dto.*;
import com.infowise.demo.rep.WorkRep;
import com.infowise.demo.req.WorkReq;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class WorkController {

    private final WorkService workService;
    private final MemberService memberService;
    private final PicService picService;
    @PostMapping("work")
    public List<Header<WorkDTO>> create(@RequestBody List<WorkReq> requests,
                                        @AuthenticationPrincipal InfoWisePrincipal infoWisePrincipal){
        MemberDTO memberDTO = memberService.read(infoWisePrincipal.idx());
        return workService.createBulk(requests,memberDTO);
    }

    @GetMapping("work/{idx}")
    public WorkRep read(@PathVariable(name="idx") Long idx){
        return WorkRep.fromDTO(workService.read(idx));
    }

    @GetMapping("work/week/{idx}")
    public List<WorkRep> readWeekWork(@PathVariable(name="idx") Long idx){
        return workService.readList(idx).stream().map(WorkRep::fromDTO).toList();
    }

    @PutMapping("work")
    public List<Header<WorkDTO>> update(@RequestBody List<WorkReq> request,
                                  @AuthenticationPrincipal InfoWisePrincipal infoWisePrincipal){
        MemberDTO memberDTO = memberService.read(infoWisePrincipal.idx());
        return workService.updateBulk( request,memberDTO);
    }

    @DeleteMapping("work/{idx}")
    public Header delete(@PathVariable(name="idx")Long idx){return workService.delete(idx);}

    @DeleteMapping("work")
    public List<Header<WorkDTO>> deleteBulk(@RequestBody List<WorkDTO> request){return workService.deleteBulk(request);}

    @GetMapping("work/{year}/{month}/{week}")
    public List<WorkDTO> check(@PathVariable(name="year")Integer year,
                               @PathVariable(name="month")Integer month,
                               @PathVariable(name="week")Integer week,
                               @AuthenticationPrincipal InfoWisePrincipal infoWisePrincipal
                               ){
        return workService.check(infoWisePrincipal.idx(), year, month, week);
    }

    @GetMapping("work/export")
    public ResponseEntity<ByteArrayResource> exportToExcel(HttpServletResponse response) {
        Workbook workbook = workService.generateExcelFile();
        System.out.println(workbook);
        // 엑셀 파일을 바이트 배열로 변환
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            workbook.write(baos);
        } catch (IOException e) {
            // 예외 처리 로직 작성
        }

        // 클라이언트로 엑셀 파일 전송
        byte[] excelBytes = baos.toByteArray();
        ByteArrayResource resource = new ByteArrayResource(excelBytes);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=work_data.xls")
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .contentLength(excelBytes.length)
                .body(resource);
    }
}

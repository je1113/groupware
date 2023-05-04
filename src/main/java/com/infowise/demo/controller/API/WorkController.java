package com.infowise.demo.controller.API;

import com.infowise.demo.Service.MemberService;
import com.infowise.demo.Service.PicService;
import com.infowise.demo.Service.WorkService;
import com.infowise.demo.dto.Header;
import com.infowise.demo.dto.MemberDTO;
import com.infowise.demo.dto.ProjectDTO;
import com.infowise.demo.dto.WorkDTO;
import com.infowise.demo.rep.WorkRep;
import com.infowise.demo.req.WorkReq;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class WorkController {

    private final WorkService workService;
    private final MemberService memberService;
    private final PicService picService;
    @PostMapping("work")
    public List<Header<WorkDTO>> create(@RequestBody List<WorkReq> requests){
        MemberDTO memberDTO = memberService.read(3L);
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
    public List<Header<WorkDTO>> update(
                                  @RequestBody List<WorkReq> request){
        MemberDTO memberDTO = memberService.read(3L);
        return workService.updateBulk( request,memberDTO);
    }

    @DeleteMapping("work/{idx}")
    public Header delete(@PathVariable(name="idx")Long idx){return workService.delete(idx);}

    @DeleteMapping("work")
    public List<Header<WorkDTO>> deleteBulk(@RequestBody List<WorkDTO> request){return workService.deleteBulk(request);}

    @GetMapping("work/{year}/{month}/{week}")
    public List<WorkDTO> check(@PathVariable(name="year")Integer year,
                               @PathVariable(name="month")Integer month,
                               @PathVariable(name="week")Integer week
                               ){
        return workService.check(3L, year, month, week);
    }
}

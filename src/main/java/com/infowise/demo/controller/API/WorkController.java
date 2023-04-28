package com.infowise.demo.controller.API;

import com.infowise.demo.Service.WorkService;
import com.infowise.demo.dto.Header;
import com.infowise.demo.dto.WorkDTO;
import com.infowise.demo.rep.WorkRep;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class WorkController {

    private final WorkService workService;

    @PostMapping("work")
    public Header<WorkDTO> create(@RequestBody Header<WorkDTO> request){
        System.out.println(request+"공수등록");
        return workService.create(request.getData());
    }

    @GetMapping("work/{idx}")
    public WorkRep read(@PathVariable(name="idx") Long idx){
        return WorkRep.fromDTO(workService.read(idx));
    }

    @PutMapping("work/{idx}")
    public Header<WorkDTO> update(@PathVariable(name="idx") Long idx,
                                  @RequestBody Header<WorkDTO> request){
        return workService.update(idx, request.getData());
    }

    @DeleteMapping("work/{idx}")
    public Header delete(@PathVariable(name="idx")Long idx){return workService.delete(idx);}

    @GetMapping("work/{year}/{month}/{week}")
    public List<WorkDTO> check(@PathVariable(name="year")Integer year,
                               @PathVariable(name="month")Integer month,
                               @PathVariable(name="week")Integer week
                               ){
        return workService.check(3L, year, month, week);
    }
}

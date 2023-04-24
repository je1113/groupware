package com.infowise.demo.controller.API;

import com.infowise.demo.Entity.Pic;
import com.infowise.demo.Service.PicService;
import com.infowise.demo.Service.ProjectService;
import com.infowise.demo.dto.Header;
import com.infowise.demo.dto.PicDTO;
import com.infowise.demo.rep.PicRep;
import com.infowise.demo.req.PicReq;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PicController {

    private final PicService picService;
    @PostMapping("pic")
    public Header<Pic> create(@RequestBody Header<PicReq> request){
        System.out.println("PIC 등록");
        return picService.create(request.getData());
    }

    @GetMapping("pic/{idx}")
    public List<PicRep> readMember (@PathVariable(name = "idx") Long idx){
        return picService.projectPic(idx).stream().map(PicRep::fromDTO).toList();
    }


    @DeleteMapping("pic/{idx}")
    public Header delete(@PathVariable(name="idx")Long idx){
        return picService.delete(idx);
    }
}

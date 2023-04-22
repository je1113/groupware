package com.infowise.demo.controller.API;

import com.infowise.demo.Entity.Pic;
import com.infowise.demo.Service.PicService;
import com.infowise.demo.Service.ProjectService;
import com.infowise.demo.dto.Header;
import com.infowise.demo.dto.PicDTO;
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

    // 이거 수정 필요!! rep을 만들어 이름을 출력하도록??
    @GetMapping("pic/{idx}")
    public List<PicDTO> readMember (@PathVariable(name = "idx") Long idx){
        return picService.projectPic(idx);
    }


    @DeleteMapping("pic/{idx}")
    public Header delete(@PathVariable(name="idx")Long idx){
        return picService.delete(idx);
    }
}

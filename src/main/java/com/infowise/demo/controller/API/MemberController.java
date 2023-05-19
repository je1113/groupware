package com.infowise.demo.controller.API;

import com.infowise.demo.Service.MemberService;
import com.infowise.demo.dto.Header;
import com.infowise.demo.dto.InfoWisePrincipal;
import com.infowise.demo.dto.MemberDTO;
import com.infowise.demo.rep.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")    // http://localhost:8989/api
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("member") //http://localhost:8989/api/member
    public Header<MemberDTO> create(@RequestBody Header<MemberDTO> request){
        System.out.println(request+"직원 등록 😊");
        return memberService.create(request.getData());
    }

    @GetMapping("member/{idx}")
    public MemberDTO read(@PathVariable(name="idx") Long idx){
        return memberService.read(idx);
    }

    @GetMapping("auth")
    public Session auth(@AuthenticationPrincipal InfoWisePrincipal infoWisePrincipal){return Session.fromDTO(infoWisePrincipal);}

    @PutMapping("member/{idx}")
    public Header<MemberDTO> update(@PathVariable(name="idx") Long idx,
                                    @RequestBody Header<MemberDTO> request){
        return memberService.update(idx, request.getData());
    }

    @DeleteMapping("member/{idx}")
    public Header delete(@PathVariable(name="idx")Long idx){
        return memberService.delete(idx);
    }
}

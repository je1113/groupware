package com.infowise.demo.controller.API;

import com.infowise.demo.Service.EmailService;
import com.infowise.demo.Service.MemberService;
import com.infowise.demo.dto.Header;
import com.infowise.demo.dto.InfoWisePrincipal;
import com.infowise.demo.dto.MemberDTO;
import com.infowise.demo.rep.Session;
import com.infowise.demo.req.ChangePwReq;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")    // http://localhost:8989/api
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("member") //http://localhost:8989/api/member
    public Header<MemberDTO> create(@RequestBody Header<MemberDTO> request){
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
    public ResponseEntity<Header> delete(@PathVariable(name="idx")Long idx){
        return ResponseEntity.ok(memberService.delete(idx));
    }

    @PostMapping("member/change-pw")
    public ResponseEntity<Header> changePw(@RequestBody ChangePwReq req,
                                           @AuthenticationPrincipal InfoWisePrincipal infoWisePrincipal){
        return ResponseEntity.ok(memberService.editPw(req,infoWisePrincipal.idx()));
    }

    @PostMapping("member/forgot-password")
    public ResponseEntity<Header> processForgotPasswordForm(
            @RequestBody MemberDTO memberDTO
    ) {
        return ResponseEntity.ok(memberService.resetPassword(memberDTO.email()));
    }
}


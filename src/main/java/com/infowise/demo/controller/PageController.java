package com.infowise.demo.controller;

import com.infowise.demo.Enum.MemberSearchType;
import com.infowise.demo.Service.MemberService;
import com.infowise.demo.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequestMapping("")    ///admin
@RequiredArgsConstructor
public class PageController {

    private final MemberService memberService;


    @PostMapping(path="loginOkYeah")   ///loginOk
    public String loginOk(HttpServletRequest request, String email, String pw){
        if(memberService.login(email, pw).getData() != null){
            HttpSession session = request.getSession();
            String name = memberService.login(email, pw).getData().name();
            session.setAttribute("email", email);
            session.setAttribute("name", name);
            return "redirect:/";
        }else{
            return "redirect:/login";
        }
    }

    private String sessionCheck(HttpServletRequest request  ){
        HttpSession session = request.getSession(false);
        String id=null;
        String name = null;
        if(session== null){
            System.out.println("세션이 없습니다");
            return null;
        }else{
            id = (String) session.getAttribute("id");
            name = (String) session.getAttribute("name");
            System.out.println("세션이 있습니다 id=" +id+ ", name="+name );
            return id+"("+name+")";
        }
    }

    @GetMapping("logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/login";
    }
    @GetMapping("login")
    public ModelAndView login(){
        return new ModelAndView("login");
    }


    @GetMapping(path="")
    public String member(HttpServletRequest request, ModelMap map,
                        @PageableDefault(size = 10, sort = "idx", direction = Sort.Direction.ASC) Pageable pageable,
                        @RequestParam(required = false) MemberSearchType searchType,
                        @RequestParam(required = false) String searchValue){
//        String session = sessionCheck(request);
//        if(session == null) return "redirect:/login";
        Page<MemberDTO> members = memberService.searchMember(searchType, searchValue, pageable);
        map.addAttribute("members", members);
        List<Integer> barNumbers = IntStream.range(0, members.getTotalPages()).boxed().toList();
        map.addAttribute("barNumbers",barNumbers);
        map.addAttribute("searchTypes", MemberSearchType.values());
        return"index";
    }
}

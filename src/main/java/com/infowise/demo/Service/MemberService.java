package com.infowise.demo.Service;

import com.infowise.demo.Entity.Member;
import com.infowise.demo.Entity.Pic;
import com.infowise.demo.Entity.Work;
import com.infowise.demo.Enum.MemberSearchType;
import com.infowise.demo.Repository.MemberRepository;
import com.infowise.demo.Repository.PicRepository;
import com.infowise.demo.Repository.WorkRepository;
import com.infowise.demo.dto.Header;
import com.infowise.demo.dto.InfoWisePrincipal;
import com.infowise.demo.dto.MemberDTO;
import com.infowise.demo.req.ChangePwReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Random;


@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final WorkRepository workRepository;
    private final PicRepository picRepository;
    private final EmailService emailService;

    // 리스트
    @Transactional(readOnly = true)
    public Page<MemberDTO> searchMember(MemberSearchType searchType, String searchKeyword, Pageable pageable){
        if(searchKeyword == null|| searchKeyword.isBlank()){
            return memberRepository.findAll(pageable).map(MemberDTO::fromEntity);
        }
        return switch (searchType){
            case EMAIL -> memberRepository.findByEmailContaining(searchKeyword, pageable).map(MemberDTO::fromEntity);
            case NAME -> memberRepository.findByNameContaining(searchKeyword, pageable).map(MemberDTO::fromEntity);
            case TEAM -> memberRepository.findByTeam(searchKeyword, pageable).map(MemberDTO::fromEntity);
        };
    }

    // 상세보기
    @Transactional(readOnly = true)
    public MemberDTO read(Long memberId){
        return memberRepository.findById(memberId)
                .map(MemberDTO::fromEntity)
                .orElseThrow(()-> new EntityNotFoundException("해당 직원이 없습니다 - memberId: "+ memberId));
    }

    public Header<MemberDTO> create(MemberDTO dto){
//        String encodePw = passwordEncoder.encode(dto.pw());
        Member newMember = memberRepository.save(dto.toEntity("{noop}" + dto.pw()));
        return Header.OK(MemberDTO.fromEntity(newMember));
    }


    public Header<MemberDTO> update(Long memberId, MemberDTO dto){
        try{
//            String encodePw = passwordEncoder.encode(dto.pw());
            Member member = memberRepository.getReferenceById(memberId);
//            if(dto.pw()!= null){member.setPw(encodePw);}
            if(dto.email()!= null){member.setEmail(dto.email());}
            if(dto.name()!= null){member.setName(dto.name());}
            if(dto.team()!= null){member.setTeam(dto.team());}
            if(dto.hp()!= null){member.setHp(dto.hp());}
            if(dto.roleType()!=null){member.setRoleType(dto.roleType());}
            return Header.OK();
        }catch (EntityNotFoundException e){
            log.warn("직원 정보 수정 실패. 해당 직원이 없습니다 - dto:{}", dto);
            return Header.ERROR("직원 정보 수정 실패. 해당 직원이 없습니다");
        }
    }

    public Header delete(Long memberId){
        Optional<Member> memberOptional = memberRepository.findById(memberId);
        if(memberOptional.isPresent()){
            Member member = memberOptional.get();
            List<Work> works = workRepository.findByMember(member,Pageable.unpaged()).stream().toList();
            List<Pic> pics = picRepository.findAllByMember(member);
            if((works ==null || works.isEmpty()) &&(pics ==null || pics.isEmpty())){
                memberRepository.delete(member);
                return Header.OK();
            }else{
                return Header.ERROR("해당 사용자의 담당 및 공수정보를 먼저 삭제해야합니다.");
            }
        }else{
            return Header.ERROR("해당 직원이 없습니다.");
        }
    }

    public Header<MemberDTO> login(String email, String pw){
        try{
            Optional<Member> member = memberRepository.findByEmailAndPw(email, pw);
            if(member.isPresent()){
                return Header.OK(MemberDTO.fromEntity(member.get()));
            }else{
                return Header.ERROR("이메일 또는 비밀번호가 틀렸습니다.");
            }
        }catch(EntityNotFoundException e){
            log.warn("이메일 또는 비밀번호가 틀렸습니다.");
            return Header.ERROR("이메일 또는 비밀번호가 틀렸습니다.");
        }
    }

    public Header editPw(ChangePwReq req, Long idx){
        Optional<Member> memberOptional = memberRepository.findById(idx);
        if(memberOptional.isPresent()){
            Member member = memberOptional.get();
            if(member.getPw().equals("{noop}"+req.pw())){
                member.setPw("{noop}"+req.pwNew());
                return Header.OK();
            }else{
                return Header.ERROR("비밀번호가 틀렸습니다.");
            }
        }
        return Header.ERROR("로그인한 유저가 없거나 세션이 만료되었습니다.");

    }
    public String pwMaker(String email){
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(chars.length());
            password.append(chars.charAt(index));
        }

        return password.toString();
    }

    public Header resetPassword(String email) {
        Optional<Member> memberOptional = memberRepository.findByEmail(email);
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            String newPassword = pwMaker(email);
            member.setPw("{noop}"+newPassword);

            String subject = "[인포와이즈] 타임시트 비밀번호 변경";
            String body = "새로운 비밀번호는 " + newPassword + " 입니다.";
            emailService.sendEmail(email, subject, body);
            return Header.OK();
        }
        return Header.ERROR("해당 이메일이 존재하지 않습니다.");
    }
}

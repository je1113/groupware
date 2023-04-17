package com.infowise.demo.Service;

import com.infowise.demo.Entity.Member;
import com.infowise.demo.Enum.MemberSearchType;
import com.infowise.demo.Repository.MemberRepository;
import com.infowise.demo.dto.Header;
import com.infowise.demo.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;


@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;


    // 리스트
    @Transactional(readOnly = true)
    public Page<MemberDTO> searchMember(MemberSearchType searchType, String searchKeyword, Pageable pageable){
        if(searchKeyword == null|| searchKeyword.isBlank()){
            return memberRepository.findAll(pageable).map(MemberDTO::fromEntity);
        }
        return switch (searchType){
            case Email -> memberRepository.findByEmailContaining(searchKeyword, pageable).map(MemberDTO::fromEntity);
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
        Member newMember = memberRepository.save(dto.toEntity());
        return Header.OK(MemberDTO.fromEntity(newMember));
    }


    public Header<MemberDTO> update(Long memberId, MemberDTO dto){
        try{
            Member member = memberRepository.getReferenceById(memberId);
            if(dto.pw()!= null){member.setPw(dto.pw());}
            if(dto.name()!= null){member.setName(dto.name());}
            if(dto.team()!= null){member.setTeam(dto.team());}
            if(dto.hp()!= null){member.setHp(dto.hp());}
            return Header.OK();
        }catch (EntityNotFoundException e){
            log.warn("직원 정보 수정 실패. 해당 직원이 없습니다 - dto:{}", dto);
            return Header.ERROR("직원 정보 수정 실패. 해당 직원이 없습니다");
        }
    }

    public Header delete(Long memberId){
        Optional<Member> member = memberRepository.findById(memberId);
        return member.map(e ->{
            memberRepository.delete(e);
            return Header.OK();
        }).orElseGet(()-> Header.ERROR("해당 직원이 없습니다. - memberId"+memberId));
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

}

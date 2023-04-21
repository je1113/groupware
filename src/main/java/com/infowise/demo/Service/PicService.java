package com.infowise.demo.Service;

import com.infowise.demo.Entity.Member;
import com.infowise.demo.Entity.Pic;
import com.infowise.demo.Entity.Project;
import com.infowise.demo.Repository.MemberRepository;
import com.infowise.demo.Repository.PicRepository;
import com.infowise.demo.Repository.ProjectRepository;
import com.infowise.demo.dto.Header;
import com.infowise.demo.dto.PicDTO;
import com.infowise.demo.dto.ProjectDTO;
import com.infowise.demo.req.PicReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class PicService {

    private final PicRepository picRepository;
    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;

    // 프로젝트 리스트에서 프로젝트당 투입된 인력 볼 수 있도록
    @Transactional(readOnly = true)
    public List<PicDTO> projectPic(Long projectIdx){
        Project project = projectRepository.findById(projectIdx).get();
        return picRepository.findAllByProject(project)
                .stream().map(PicDTO::fromEntity).toList();
    }
    // String Format으로 바꿔서?(진행중)
    @Transactional(readOnly = true)
    public List<String> picMemberNames(List<ProjectDTO> projects){
        List<String> picMemberNames = new ArrayList<>();
        projects.forEach(
                projectDTO -> {
                    String memberName = new String();
                    Project project = projectRepository.findById(projectDTO.idx()).get();
                    List<Pic> pics = picRepository.findAllByProject(project);
                    if(pics == null||pics.isEmpty()){
                        memberName = " - ";
                    }else{
                        memberName = "";
                        for(Pic pic : pics){
                            memberName += pic.getMember().getName() +" ";
                        }
                    }
                    picMemberNames.add(memberName);
                }
        );
        System.out.println("😊😊"+picMemberNames);
        return picMemberNames;
    }

    // 담당정보 등록
    public Header<Pic> create(PicReq picReq){
        Member member = memberRepository.findById(picReq.memberIdx()).get();
        Project project = projectRepository.findById(picReq.projectIdx()).get();
        Pic newPic = picRepository.save(Pic.of(member,project));
        return Header.OK(newPic);
    }

    public Header delete(Long idx){
        Optional<Pic> pics =picRepository.findById(idx);
        return pics.map(pic->{
            picRepository.delete(pic);
            return Header.OK();
        }).orElseGet(()->Header.ERROR("pic 존재하지 않음"));
    }
}

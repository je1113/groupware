package com.infowise.demo.component;

import com.infowise.demo.Entity.Member;
import com.infowise.demo.Service.EmailService;
import com.infowise.demo.Service.WorkService;
import lombok.RequiredArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EmailJob implements Job {
    private final EmailService emailService;
    private final WorkService workService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // 메일 쓰기
        String subject = "[인포와이즈_공지]타임시트 입력 요청 드립니다.";
        String text = "안녕하세요. 타임시트 안내를 위해 메일드렸습니다.\n\n 오늘까지 금주 일하신 기록을 남겨주시면 됩니다. \n\n감사합니다.";
        String email = "jejeon@infowise.kr";
        emailService.sendEmail(email,subject,text);
//        // 금주 공수 입력 안한 사람 추출
//        List<Member> members = workService.notWorkMember();
//        // 반복문 돌아가면서 이메일 전송
//        for (Member member : members) {
//            emailService.sendEmail(member.getEmail(),subject,text);
//        }
    }
}

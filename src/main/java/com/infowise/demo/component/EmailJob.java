package com.infowise.demo.component;

import com.infowise.demo.Service.EmailService;
import com.infowise.demo.Service.WorkService;
import lombok.RequiredArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailJob implements Job {
    private final EmailService emailService;
    private final WorkService workService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // 금주 공수 입력 안한 사람 추출


        // 반복문 돌아가면서 이메일 전송

        String to = "jje320594@gmail.com";
        String subject = "Test Email";
        String text = "This is a test email.";
        emailService.sendEmail(to, subject, text);
    }
}

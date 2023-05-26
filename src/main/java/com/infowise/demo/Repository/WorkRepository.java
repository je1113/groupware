package com.infowise.demo.Repository;

import com.infowise.demo.Entity.Member;
import com.infowise.demo.Entity.Project;
import com.infowise.demo.Entity.Work;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;


public interface WorkRepository extends JpaRepository<Work, Long> {
    List<Work> findAllByMemberAndYearAndMonthAndWeek(Member member, Integer year,
                                                     Integer month, Integer week);
    Page<Work> findByMemberNameContaining(String memberName, Pageable pageable);
    Page<Work> findByProjectNameContaining(String projectName, Pageable pageable);
    Page<Work> findByMember(Member member,Pageable pageable);
    Page<Work> findByMemberAndProjectNameContaining(Member member, String projectName, Pageable pageable);

    List<Work> findByProject(Project project);

    Page<Work> findByDateBetween( LocalDate startDate, LocalDate endDate, Pageable pageable);
    List<Work> findByYearAndMonthAndWeek(Integer year, Integer month, Integer week);

}



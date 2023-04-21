package com.infowise.demo.Repository;

import com.infowise.demo.Entity.Member;
import com.infowise.demo.Entity.Pic;
import com.infowise.demo.Entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PicRepository extends JpaRepository<Pic, Long> {
//    List<Pic> findAllByMember(Member member);
    List<Pic> findAllByProject(Project project);
}

package com.infowise.demo.Repository;

import com.infowise.demo.Entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    //검색용

    Optional<Member> findByEmail(String email);
    Page<Member> findByEmailContaining(String email, Pageable pageable);
    Page<Member> findByNameContaining(String name, Pageable pageable);
    Page<Member> findByTeam(String group, Pageable pageable);

    Optional<Member> findByName(String name);

    //로그인용
    Optional<Member> findByEmailAndPw(String email, String pw);


}

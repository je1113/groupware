package com.infowise.demo.Repository;

import com.infowise.demo.Entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    Page<Project> findByNameContaining(String name, Pageable pageable);
}

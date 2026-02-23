package com.project.fitness.repository;

import com.project.fitness.Model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity,String> {
    List<Activity> findAByUserId(String userid);
}

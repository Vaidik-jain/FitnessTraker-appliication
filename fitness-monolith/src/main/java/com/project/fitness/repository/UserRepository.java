package com.project.fitness.repository;

import com.project.fitness.Model.Activity;
import com.project.fitness.Model.User;
import com.project.fitness.dto.ActivityResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

}

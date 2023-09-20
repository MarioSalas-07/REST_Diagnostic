package com.metaphorse.diagnostic.diagnostic_taskmanagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.metaphorse.diagnostic.diagnostic_taskmanagement.Model.Task;

public interface TaskRepository extends JpaRepository<Task, Long>{
    
}
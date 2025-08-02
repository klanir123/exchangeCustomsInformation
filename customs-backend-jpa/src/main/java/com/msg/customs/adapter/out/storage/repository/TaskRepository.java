package com.msg.customs.adapter.out.storage.repository;

import com.msg.customs.adapter.out.storage.model.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {



}

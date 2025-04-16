package com.msg.customs.adapter.out.storage.repository;

import com.msg.customs.adapter.out.storage.model.MasterPartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartRepository extends JpaRepository<MasterPartEntity, Long> {



}

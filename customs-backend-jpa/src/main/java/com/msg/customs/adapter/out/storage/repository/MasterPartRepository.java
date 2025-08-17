package com.msg.customs.adapter.out.storage.repository;

import com.msg.customs.adapter.out.storage.model.MasterPartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterPartRepository extends JpaRepository<MasterPartEntity, Long> {


    MasterPartEntity findByCatenaxId(String catenaXId);
}

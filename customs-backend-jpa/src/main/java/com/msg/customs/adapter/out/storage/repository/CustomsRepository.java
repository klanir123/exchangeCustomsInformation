package com.msg.customs.adapter.out.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.msg.customs.adapter.out.storage.model.CustomsEntity;

@Repository
public interface CustomsRepository extends JpaRepository<CustomsEntity, Long> {

}

package com.msg.customs.adapter.out.storage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.msg.customs.adapter.out.storage.model.CustomsEntity;

@Repository
public interface CustomsRepository extends JpaRepository<CustomsEntity, Long> {

	CustomsEntity findByCatenaxId(String catenaxId);
	
	List<CustomsEntity> findByCatenaxIdIn(List<String> catenaxIds);

}

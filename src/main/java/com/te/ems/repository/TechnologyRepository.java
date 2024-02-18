package com.te.ems.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.te.ems.entity.Technology;
                                                                      // primary key type 
public interface TechnologyRepository extends JpaRepository<Technology, String>{
	

}

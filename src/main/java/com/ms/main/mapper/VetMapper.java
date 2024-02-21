package com.ms.main.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ms.main.domain.Vet;

@Mapper
public interface VetMapper {
	
	public int selectCountVetList();
	
	public Vet selectVetById(int id);
	
	public void updateVet(@Param("id") int id, @Param("x") String x, @Param("y") String y);
	
}

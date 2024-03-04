package com.ms.vet.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ms.vet.domain.Vet;

@Mapper
public interface VetMapper {
	
	public int selectCountVetList();
	
	public List<Vet> selectVetListByXY(
			@Param("min_x") double min_x,
			@Param("max_x") double max_x,
			@Param("min_y") double min_y,
			@Param("max_y") double max_y,
			@Param("type") String type);
	
	public Vet selectVetById(int id);
	
	public void updateVet(@Param("id") int id, @Param("x") String x, @Param("y") String y);
	
}

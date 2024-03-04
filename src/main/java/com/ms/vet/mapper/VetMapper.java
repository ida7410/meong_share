package com.ms.vet.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ms.vet.domain.Vet;

@Mapper
public interface VetMapper {
	
	// ------- READ ------- 
	
	/***
	 * Select the total count of vets
	 * @return
	 */
	public int selectCountVetList();
	
	/***
	 * Select list of vet
	 * @param min_x
	 * @param max_x
	 * @param min_y
	 * @param max_y
	 * @param type
	 * @return
	 */
	public List<Vet> selectVetListByXY(
			@Param("min_x") double min_x,
			@Param("max_x") double max_x,
			@Param("min_y") double min_y,
			@Param("max_y") double max_y,
			@Param("type") String type);
	
	/***
	 * Get vet by vet id
	 * @param id
	 * @return
	 */
	public Vet selectVetById(int id);
	
	/***
	 * Update Vet X Y by vet id
	 * @param id
	 * @param x
	 * @param y
	 */
	public void updateVet(
			@Param("id") int id, 
			@Param("x") String x, 
			@Param("y") String y);
	
}

package com.ms.vet.bo;

import java.util.ArrayList;
import java.util.List;

import org.locationtech.proj4j.ProjCoordinate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.common.TransCoord;
import com.ms.vet.domain.Vet;
import com.ms.vet.mapper.VetMapper;

@Service
public class VetBO {
	
	@Autowired
	private VetMapper vetMapper;
	
	@Autowired
	private TransCoord transCoord;
	
	// ------- READ ------- 
	
	/***
	 * Get list of vets inside the boundry
	 * @param min_x
	 * @param min_y
	 * @param max_x
	 * @param max_y
	 * @param type
	 * @return
	 */
	public List<Vet> getVetList(String min_x, String min_y, String max_x, String max_y, String type) {
		
		List<Vet> vetList = new ArrayList<>();
		double min_x_int = Double.parseDouble(min_x);
		double min_y_int = Double.parseDouble(min_y);
		double max_x_int = Double.parseDouble(max_x);
		double max_y_int = Double.parseDouble(max_y);
		
		// if type is all => set type null
		if (type.equals("all")) {
			type = null;
		}
		
		// get vet list
		vetList = vetMapper.selectVetListByXY(min_x_int, max_x_int, min_y_int, max_y_int, type);
		
		return vetList;
	}
	
	
	// ------- UPDATE ------- 
	
	// update vets coord
	public void updateVet() {
		int totalCount = vetMapper.selectCountVetList();
		for (int i = 1; i <= totalCount; i++) {
			Vet vet = vetMapper.selectVetById(i);
			String x = vet.getX();
			String y = vet.getY();
			ProjCoordinate coord = transCoord.transform(x, y);
			vetMapper.updateVet(i, coord.x + "", coord.y + "");
		}
	}
	
}

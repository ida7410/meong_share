package com.ms.main.bo;

import java.util.ArrayList;
import java.util.List;

import org.locationtech.proj4j.ProjCoordinate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.common.TransCoord;
import com.ms.main.domain.Type;
import com.ms.main.domain.Vet;
import com.ms.main.mapper.VetMapper;

@Service
public class VetBO {
	
	@Autowired
	private VetMapper vetMapper;
	
	@Autowired
	private TransCoord transCoord;
	
	public List<Vet> getVetList(String min_x, String min_y, String max_x, String max_y, String type) {
		List<Vet> vetList = new ArrayList<>();
		double min_x_int = Double.parseDouble(min_x);
		double min_y_int = Double.parseDouble(min_y);
		double max_x_int = Double.parseDouble(max_x);
		double max_y_int = Double.parseDouble(max_y);
		
		Type t = null;
		if (type.equals("hospital")) {
			t = Type.Hospital;
		}
		else {
			t = Type.Pharmacy;
		}
		
		vetList = vetMapper.selectVetListByXY(min_x_int, max_x_int, min_y_int, max_y_int, t.getTypeStr());
		return vetList;
	}
	
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

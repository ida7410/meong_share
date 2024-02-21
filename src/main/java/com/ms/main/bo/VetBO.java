package com.ms.main.bo;

import org.locationtech.proj4j.ProjCoordinate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.common.TransCoord;
import com.ms.main.domain.Vet;
import com.ms.main.mapper.VetMapper;

@Service
public class VetBO {
	
	@Autowired
	private VetMapper vetMapper;
	
	@Autowired
	private TransCoord transCoord;
	
	public void updateVet() {
		int totalCount = vetMapper.selectCountVetList();
		for (int i = 1; i <= totalCount; i++) {
			Vet vet = vetMapper.selectVetById(i);
			String x = vet.getX();
			String y = vet.getY();
			ProjCoordinate coord = transCoord.transform(x, y);
		}
	}
	
}

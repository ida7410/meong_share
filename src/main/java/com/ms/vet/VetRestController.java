package com.ms.vet;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ms.vet.bo.VetBO;
import com.ms.vet.domain.Vet;

@RestController
public class VetRestController {
	
	@Autowired
	private VetBO vetBO;
	
	/***
	 * Get list of vets in the boundry
	 * @param min_x
	 * @param min_y
	 * @param max_x
	 * @param max_y
	 * @param type
	 * @return
	 */
	@PostMapping("/get-vet-list")
	public List<Vet> getVetList(
			@RequestParam("min_x") String min_x,
			@RequestParam("min_y") String min_y,
			@RequestParam("max_x") String max_x,
			@RequestParam("max_y") String max_y,
			@RequestParam("type") String type) {
		
		List<Vet> vetList = vetBO.getVetList(min_x, min_y, max_x, max_y, type);
		return vetList;
	}
	
}

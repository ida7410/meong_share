package com.ms.main;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.ms.common.TransCoord;
import com.ms.main.bo.VetBO;
import com.ms.main.domain.Vet;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class MapRestController {
	
	@Autowired
	private TransCoord transCoord;
	
	@Autowired
	private VetBO vetBO;
	

	@PostMapping("/get-vet-list")
	public List<Vet> getVetList(
			@RequestParam("min_x") String min_x,
			@RequestParam("min_y") String min_y,
			@RequestParam("max_x") String max_x,
			@RequestParam("max_y") String max_y) {
		
		log.info(min_x + " / " + min_y);
		log.info(max_x + " / " + max_y);
		List<Vet> vetList = vetBO.getVetList(min_x, min_y, max_x, max_y);
		return vetList;
	}
	
	@GetMapping("/naver")
	public String naver() {

        // 공식 문서의 [파라미터] 부분 참고
        URI uri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")
                .path("/v1/search/local.json")
                .queryParam("query", "동물병원")
                .queryParam("display", 10)
                .queryParam("start", 1)
                .queryParam("sort", "random")
                .encode(Charset.forName("UTF-8"))
                .build()
                .toUri();

        log.info("--- naver ---");
        log.info("uri : {}", uri);

        // Header를 위함
        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id", "KxhYATkbflu3pEkt7KIJ")
                .header("X-Naver-Client-Secret", "ICH7I7TwMi")
                .build();

        // 응답 받을 클래스 지정
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result = restTemplate.exchange(req, String.class);

        return result.getBody();
	}
}

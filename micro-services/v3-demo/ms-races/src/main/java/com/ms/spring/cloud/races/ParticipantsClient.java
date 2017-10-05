package com.ms.spring.cloud.races;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@FeignClient("ms-participants")
interface ParticipantsClient {
	
	@RequestMapping(value ="/races/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<Participant>> getParticipants(@PathVariable("id") String id);
	
}
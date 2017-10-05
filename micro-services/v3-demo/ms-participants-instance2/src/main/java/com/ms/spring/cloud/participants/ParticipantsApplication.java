package com.ms.spring.cloud.participants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@SpringBootApplication
@EnableDiscoveryClient
public class ParticipantsApplication implements CommandLineRunner{
	@Autowired
	private DiscoveryClient discoveryClient;
	
	private static List<Participant> participants = new ArrayList<Participant>();
	
	private static Logger logger= LoggerFactory.getLogger(ParticipantsApplication.class);
	
    public static void main(String[] args) {
        SpringApplication.run(ParticipantsApplication.class, args);
    }
    
    @Override
	public void run(String... arg0) throws Exception {
		participants.add(new Participant("Muni", "Sekhar", "MA", "S", Arrays.asList("666", "888")));
		participants.add(new Participant("Stephanie", "A", "MA", "M", Arrays.asList("555")));
		participants.add(new Participant("Minny", "M", "MA", "L", Arrays.asList("555","777")));
		participants.add(new Participant("Thomas", "Ez", "MA", "L", Arrays.asList("777","888","666")));
	}
    
    
    @RequestMapping("/")
	public List<Participant> getParticipants() {
    	logger.info("Inside Participants Plain Request--->");
		return participants;
	}
	
    @RequestMapping(value = "/races/{id}",method=RequestMethod.GET)
	public ResponseEntity<List<Participant>> getParticipants(@PathVariable("id") String id) {
		
		ServiceInstance localInstance =  discoveryClient.getLocalServiceInstance();
		String serverName = discoveryClient.getLocalServiceInstance().getServiceId()+":"+localInstance.getHost()+":"+localInstance.getPort();
		
		logger.info("Inside Participants Param Request--->");
		logger.info("Participants Details--->"+ participants.stream().filter(p -> p.getRaces().contains(id)).collect(Collectors.toList()));
		logger.info("Current Server-->"+serverName );
		
		return ResponseEntity.ok().header("server-name", serverName).body(participants.stream().filter(p -> p.getRaces().contains(id)).collect(Collectors.toList()));
	}
    
}


	


class Participant {
	private String firstName;
	private String lastName;
	private String homeState;
	private String shirtSize;
	private List<String> races;
	
	public Participant(){		
		super();
	}
	
	public Participant(String firstName, String lastName, String homeState,
			String shirtSize, List<String> races) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.homeState = homeState;
		this.shirtSize = shirtSize;
		this.races = races;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getHomeState() {
		return homeState;
	}
	public void setHomeState(String homeState) {
		this.homeState = homeState;
	}
	public String getShirtSize() {
		return shirtSize;
	}
	public void setShirtSize(String shirtSize) {
		this.shirtSize = shirtSize;
	}
	public List<String> getRaces() {
		return races;
	}
	public void setRaces(List<String> races) {
		this.races = races;
	}
}
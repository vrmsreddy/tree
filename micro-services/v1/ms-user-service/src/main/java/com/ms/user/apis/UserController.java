package com.ms.user.apis;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ms.user.dto.UserDTO;

/**
 * REST endpoint for the user functionality
 * 
 * @author anilallewar
 *
 */
@RestController
@RequestMapping("/")
public class UserController {

	@Value("${mail.domain}")
	private String mailDomain;

	private List<UserDTO> users = Arrays.asList(new UserDTO("Sunil", "S", "1", "sunil.s@" + mailDomain),
			new UserDTO("ms", "reddy", "2", "ms@" + mailDomain),
			new UserDTO("vrms", "reddy", "3", "vrms@" + mailDomain));

	/**
	 * Return all users
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, headers = "Accept=application/json")
	public List<UserDTO> getUsers() {
		return users;
	}

	/**
	 * Return user associated with specific user name
	 * 
	 * @param userName
	 * @return
	 */
	@RequestMapping(value = "{userName}", method = RequestMethod.GET, headers = "Accept=application/json")
	public UserDTO getUserByUserName(@PathVariable("userName") String userName) {
		UserDTO userDtoToReturn = null;
		System.out.println("DOMAIN"+mailDomain);
		for (UserDTO currentUser : users) {
			if (currentUser.getUserName().equalsIgnoreCase(userName)) {
				userDtoToReturn = currentUser;
				break;
			}
		}

		return userDtoToReturn;
	}
}

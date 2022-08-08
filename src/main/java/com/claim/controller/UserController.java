package com.claim.controller;

import com.claim.entity.User;
import com.claim.entity.UserSpotify;
import com.claim.service.AuthService;
import com.claim.service.SpotifyService;
import com.claim.service.UserService;

import java.net.URI;
import java.util.*;

import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class UserController
{
    @Autowired
    UserService userService;
	//POSTMAN SOA
    @RequestMapping(value="/save", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE, 
    		method=RequestMethod.POST)
    public void submitUserDetails(@RequestBody User user)
	{
		userService.saveUser(user);
	}

	//POSTMAN SOA
    @RequestMapping(value="/login", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE,
    		method=RequestMethod.POST)
    public ResponseEntity<User> handleLogin(@RequestBody User user)
    {
    	User userLogin = userService.loginUser(user);
    	try
    	{
    		if (userLogin != null)
			{
				SpotifyService.refreshInstance(userLogin.getUserSpotify());
				System.out.println("state : " + userLogin.getUserSpotify().getState());
				return new ResponseEntity<>(userLogin, HttpStatus.OK);
			}
    		else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    	catch (Exception r)
    	{
    		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    	}
    }
    
    @RequestMapping(value="/findByUsername", produces=MediaType.APPLICATION_JSON_VALUE, method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Optional<User>> findByUsername(String username)
	{
		Optional<User> findUser = userService.findByUsername(username);
		try
		{
			if (findUser.isPresent()) return new ResponseEntity<>(findUser, HttpStatus.OK);
			else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		catch (Exception e)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	//POSTMAN SOA
    @RequestMapping(value="/findAllUsers", produces=MediaType.APPLICATION_JSON_VALUE, method=RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<User>> findAllUsers()
    {
    	try
    	{
    		List<User> userList = userService.getAllUsers();
    		return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
    	}
    	catch(Exception r)
    	{
    		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    	}
    }
}
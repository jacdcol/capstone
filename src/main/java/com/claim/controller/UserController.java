package com.claim.controller;

import com.claim.entity.User;
import com.claim.service.SpotifyService;
import com.claim.service.UserService;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class UserController
{
    @Autowired
    UserService userService;
    
    @RequestMapping(value="/save", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE, 
    		method=RequestMethod.POST)
    public void submitUserDetails(@RequestBody User user) {userService.saveUser(user);}
    
    @RequestMapping(value="/login", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE,
    		method=RequestMethod.POST)
    public ResponseEntity<User> handleLogin(@RequestBody User user)
    {
    	User userLogin = userService.loginUser(user);
    	try
    	{
    		if (userLogin != null) return new ResponseEntity<>(userLogin, HttpStatus.OK);
    		else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    	catch (Exception r)
    	{
    		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    	}
    }
    
    @RequestMapping(value="/spotify-auth", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE,
    		method=RequestMethod.POST)
    public void spotifyAuth(@RequestBody User user)
    {
    	SpotifyService.authorizationCodeUriSync();
    	SpotifyService.authorizationCodeUriAsync();
    	SpotifyService.authorizationCodeSync();
    	SpotifyService.authorizationCodeAsync();
    	SpotifyService.authorizationCodeRefreshSync();
    	SpotifyService.authorizationCodeRefreshAsync();
    }
    
    @RequestMapping(value = "/findByEmail", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Optional<User>> findByEmail(String email)
	{
		Optional<User> findUser = userService.findByEmail(email);
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
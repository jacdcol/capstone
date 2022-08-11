package com.claim.entity;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User
{
    @Id
	@Column(name = "username")
	private String username;

    @Column(name = "password")
    private String password;

	@Column(name = "name")
	private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "spotify_id")
    private UserSpotify userSpotify;
    
    public User() {}

	public User(String username, String password, String name, UserSpotify userSpotify)
	{
		this.username = username;
		this.password = password;
		this.name = name;
		this.userSpotify = userSpotify;
	}

	public String getUsername() {return username;}
	public void setUsername(String username) {this.username = username;}
	public String getPassword() {return password;}
	public void setPassword(String password) {this.password = password;}
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	public UserSpotify getUserSpotify() {return userSpotify;}
	public void setUserSpotify(UserSpotify userSpotify) {this.userSpotify = userSpotify;}
}
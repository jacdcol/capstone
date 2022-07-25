package com.claim.entity;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User
{
    @Id
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "spotify_id")
    private UserSpotify userSpotify;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "apple_music_id")
    private UserAppleMusic userAppleMusic;
    
    public User() {}

	public User(String email, String password, UserSpotify userSpotify, UserAppleMusic userAppleMusic)
	{
		this.email = email;
		this.password = password;
		this.userSpotify = userSpotify;
		this.userAppleMusic = userAppleMusic;
	}

	public String getEmail() {return email;}
	public void setEmail(String email) {this.email = email;}
	public String getPassword() {return password;}
	public void setPassword(String password) {this.password = password;}
	public UserSpotify getUserSpotify() {return userSpotify;}
	public void setUserSpotify(UserSpotify userSpotify) {this.userSpotify = userSpotify;}
	public UserAppleMusic getUserAppleMusic() {return userAppleMusic;}
	public void setUserAppleMusic(UserAppleMusic userAppleMusic) {this.userAppleMusic = userAppleMusic;}
}
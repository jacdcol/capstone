package com.claim.entity;

import javax.persistence.*;

import com.claim.service.AuthService;

import java.net.URI;

@Entity
@Table(name = "user_spotify")
public class UserSpotify
{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

	//@OneToOne(cascade = CascadeType.ALL)
	//private TopTracks topTracks;

    /*@Column(name = "spotify_refresh_token")
    private URI spotifyRefreshToken;
    
    @Column(name = "spotify_code_verifier")
	private static final String codeVerifier = AuthService.generateCodeVerifier();
    
    @Column(name = "spotify_code_challenge")
	private static final String codeChallenge = AuthService.generateCodeChallenge(codeVerifier);*/
    
    public UserSpotify() {}

	public UserSpotify(Integer id)
	{
		this.id = id;
	}

	public Integer getId() {return id;}
}
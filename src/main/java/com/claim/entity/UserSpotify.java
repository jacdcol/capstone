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

    @Column(name = "spotify_refresh_token")
    private URI spotifyRefreshToken;
    
    @Column(name = "spotify_code_verifier")
	private static final String codeVerifier = AuthService.generateCodeVerifier();
    
    @Column(name = "spotify_code_challenge")
	private static final String codeChallenge = AuthService.generateCodeChallenge(codeVerifier);
    
    public UserSpotify() {}

	public UserSpotify(Integer id, URI spotifyRefreshToken)
	{
		this.id = id;
		this.spotifyRefreshToken = spotifyRefreshToken;
	}

	public Integer getId() {return id;}
	public URI getSpotifyRefreshToken() {return spotifyRefreshToken;}
	public void setSpotifyRefreshToken(URI spotifyRefreshToken) {this.spotifyRefreshToken = spotifyRefreshToken;}
	public static String getCodeVerifier() {return codeVerifier;}
	public static String getCodeChallenge(){return codeChallenge;}
}
package com.claim.entity;

import javax.persistence.*;

import com.claim.service.AuthService;
import net.bytebuddy.utility.RandomString;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;

import java.net.URI;

@Entity
@Table(name = "user_spotify")
public class UserSpotify
{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "email")
    private String email;

    @Column(name = "auth_code")
    private String authCode;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "state")
    private String state;

    @Column(name = "code_verifier")
    private String codeVerifier;

    @Column(name = "code_challenge")
    private String codeChallenge;
    
    public UserSpotify()
    {
        state = RandomString.make(18).toUpperCase();
        codeVerifier = AuthService.generateCodeVerifier();
        codeChallenge = AuthService.generateCodeChallenge(codeVerifier);
    }

	public UserSpotify(Integer id, String email, String authCode, String accessToken, String refreshToken, String state, String codeVerifier, String codeChallenge)
    {
        this.id = id;
        this.email = email;
        this.authCode = authCode;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.state = state;
        this.codeVerifier = codeVerifier;
        this.codeChallenge = codeChallenge;
    }

    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getAuthCode() {return authCode;}
    public void setAuthCode(String authCode) {this.authCode = authCode;}
    public String getAccessToken() {return accessToken;}
    public void setAccessToken(String accessToken) {this.accessToken = accessToken;}
    public String getRefreshToken() {return refreshToken;}
    public void setRefreshToken(String refreshToken) {this.refreshToken = refreshToken;}
    public String getState() {return state;}
    public void resetState() {this.state = RandomString.make(18).toUpperCase();}
    public String getCodeVerifier() {return codeVerifier;}
    public void resetCodeVerifier()
    {
        this.codeVerifier = AuthService.generateCodeVerifier();
        this.codeChallenge = AuthService.generateCodeChallenge(this.codeVerifier);
    }
    public String getCodeChallenge() {return codeChallenge;}
}
package com.claim.entity;

import javax.persistence.*;
import java.net.URI;

@Entity
@Table(name = "user_apple_music")
public class UserAppleMusic
{
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

    @Column(name = "email")
    private String email;

    /*@Column(name = "apple_music_access_token")
    private URI appleMusicAccessToken;

    @Column(name = "apple_music_refresh_token")
    private URI appleMusicRefreshToken;*/
    
    public UserAppleMusic() {}

	public UserAppleMusic(String email)
	{
		this.email = email;
	}

	public Integer getId() {return id;}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
package com.claim.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.net.URI;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "user_spotify")
public class UserSpotify
{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "spotify_access_token")
    private URI spotifyAccessToken;

    @Column(name = "spotify_refresh_token")
    private URI spotifyRefreshToken;
}
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
@Table(name = "user_apple_music")
public class UserAppleMusic
{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "apple_music_access_token")
    private URI appleMusicAccessToken;

    @Column(name = "apple_music_refresh_token")
    private URI appleMusicRefreshToken;
}
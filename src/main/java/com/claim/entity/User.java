package com.claim.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
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
}
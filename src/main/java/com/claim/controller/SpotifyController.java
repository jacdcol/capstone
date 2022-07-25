package com.claim.controller;

import com.claim.entity.User;
import com.claim.service.SpotifyService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class SpotifyController
{
    @RequestMapping(value="/spotify-auth", consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE,
            method= RequestMethod.POST)
    public void spotifyAuth(@RequestBody User user)
    {
        SpotifyService.authorizationCodeUriSync();
        SpotifyService.authorizationCodeUriAsync();
        SpotifyService.authorizationCodeSync();
        SpotifyService.authorizationCodeAsync();
        SpotifyService.authorizationCodeRefreshSync();
        SpotifyService.authorizationCodeRefreshAsync();
    }
}
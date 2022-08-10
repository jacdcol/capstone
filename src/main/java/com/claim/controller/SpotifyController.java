package com.claim.controller;

import com.claim.entity.User;
import com.claim.entity.UserSpotify;
import com.claim.repository.UserRepository;
import com.claim.service.AuthService;
import com.claim.service.SpotifyService;
import com.claim.service.UserService;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import se.michaelthelin.spotify.model_objects.specification.Album;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.pkce.AuthorizationCodePKCERequest;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopArtistsRequest;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopTracksRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping(value="/spotify-api")
public class SpotifyController
{
    @Autowired
    UserService userService;
    private static final String clientId = "198355d84c154d1da590aa0a7f716d5b";
    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8080/spotify-api/callback");
    private static final String scope = "user-read-email,user-top-read,user-library-modify";
    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setRedirectUri(redirectUri)
            .build();

    @RequestMapping(value="/auth-code-uri", produces=MediaType.APPLICATION_JSON_VALUE, method= RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> getSpotifyAuthCodeUri(String username)
    {
        Optional<User> user = userService.findByUsername(username);
        try
        {
            String state = "";
            String codeChallenge = "";
            String authCode = "";
            if (user.isPresent()) state = user.get().getUserSpotify().getState();
            if (user.isPresent()) codeChallenge = user.get().getUserSpotify().getCodeChallenge();

            AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodePKCEUri(codeChallenge)
                    .state(state)
                    .scope(scope)
                    .show_dialog(true)
                    .build();
            final URI uri = authorizationCodeUriRequest.execute();
            //System.out.println("URI = " + uri.toString());
            //user.ifPresent(value -> System.out.println(value.getName()));
            user.ifPresent(value -> value.getUserSpotify().setAuthCode(uri.toString()));
            if (user.isPresent()) authCode = user.get().getUserSpotify().getAuthCode();
            //System.out.println(authCode);
            return new ResponseEntity<>(authCode, HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @RequestMapping(value="/callback", produces=MediaType.APPLICATION_JSON_VALUE, method=RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> getToken(String code, String state, HttpServletResponse response)
    {
        User usr = userService.findUserByState(state);
        //System.out.println(usr.getName());
        //System.out.println(state);
        //System.out.println(code);
        AuthorizationCodePKCERequest authorizationCodePKCERequest = spotifyApi.authorizationCodePKCE(code,
                usr.getUserSpotify().getCodeVerifier()).build();
        try
        {
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodePKCERequest.execute();
            //
            SpotifyService.updateTokens(usr.getUserSpotify(), spotifyApi, authorizationCodeCredentials);
            response.sendRedirect("http://localhost:3000/dashboard");
            return new ResponseEntity<>(HttpStatus.FOUND);
        }
        catch (IOException | SpotifyWebApiException | ParseException e)
        {
            System.out.println("error : " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        catch (NullPointerException n)
        {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @RequestMapping(value="/get-top-tracks", produces=MediaType.APPLICATION_JSON_VALUE, method=RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Track[]> getTopTracks(String username)
    {

        System.out.println("track req received");
        Optional<User> user = userService.findByUsername(username);
        GetUsersTopTracksRequest getUsersTopTracksRequest = spotifyApi.getUsersTopTracks()
                .limit(50)
                .offset(0)
                .time_range("long_term")
                .build();
        try
        {
            user.ifPresent(value -> spotifyApi.setAccessToken(value.getUserSpotify().getAccessToken()));
            user.ifPresent(value -> System.out.println(value.getName()));
            final Paging<Track> trackPaging = getUsersTopTracksRequest.execute();
            System.out.println("tracks received");
            user.ifPresent(value -> SpotifyService.refreshAccessToken(value.getUserSpotify(), spotifyApi));
            return new ResponseEntity<>(trackPaging.getItems(), HttpStatus.OK);
        }
        catch (IOException | SpotifyWebApiException | ParseException e)
        {
            System.out.println("error : " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value="/get-top-artists", produces=MediaType.APPLICATION_JSON_VALUE, method=RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Artist[]> getTopArtists(String username)
    {

        System.out.println("artists req received");
        System.out.println(username);
        Optional<User> user = userService.findByUsername(username);
        GetUsersTopArtistsRequest getUsersTopArtistsRequest = spotifyApi.getUsersTopArtists()
                .limit(50)
                .offset(0)
                .time_range("long_term")
                .build();
        try
        {
            user.ifPresent(value -> spotifyApi.setAccessToken(value.getUserSpotify().getAccessToken()));
            user.ifPresent(value -> System.out.println(value.getName()));
            final Paging<Artist> artistPaging = getUsersTopArtistsRequest.execute();
            System.out.println("artists received");
            user.ifPresent(value -> SpotifyService.refreshAccessToken(value.getUserSpotify(), spotifyApi));
            return new ResponseEntity<>(artistPaging.getItems(), HttpStatus.OK);
        }
        catch (IOException | SpotifyWebApiException | ParseException e)
        {
            System.out.println("error : " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}

package com.exercise.tournament.controller;

import com.exercise.tournament.entity.Player;
import com.exercise.tournament.entity.Tournament;
import com.exercise.tournament.service.PlayerService;
import com.exercise.tournament.service.TournamentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("http://localhost:4200/")
public class TournamentController {



    private final TournamentService tournamentService;


    private final PlayerService playerService;

    public TournamentController(TournamentService tournamentService, PlayerService playerService) {
        this.tournamentService = tournamentService;
        this.playerService = playerService;
    }

    //get all tournaments
    @GetMapping("/tournament")
    public List<Tournament> getAllTournaments(){
    return tournamentService.getTournaments();
    }

    // create tournament
    @PostMapping("/tournament")
    public Tournament createTournament(@RequestBody Tournament tournament){
        return tournamentService.createTournament(tournament);
    }

    //  get tournament  by id
    @GetMapping("/tournament/{id}")
    public Optional<Tournament> getTournamentById(@PathVariable long id){
    return tournamentService.getTournamentById(id);
    }

    // delete a tournament
    @DeleteMapping("/tournament/{id}")
    public ResponseEntity<Object> deleteTournamentById(@PathVariable long id){
    return tournamentService.deleteTournamentById(id);
    }
    @PutMapping("/tournament/{id}")
    public Tournament updateTournament(@PathVariable long id, @RequestBody Tournament tournament){
        return tournamentService.updateTournamentById(id, tournament);

    }
    @GetMapping("/getAllPlayers")
    public List<Player> getPlayers(){
        return playerService.getAllPlayers();
    }

    @PostMapping("/player/{tournamentId}")
    public Player createPlayer(@PathVariable(value = "tournamentId") Long tournamentId, @RequestBody Player player) {
        return playerService.createPlayer(tournamentId,player);
    }
    @GetMapping("/player/{playerId}")
    public Optional<Player> getPlayerById(@PathVariable long playerId){
        return playerService.getPlayerById(playerId);
    }

    @PutMapping("/player/{playerId}")
    public Player updatePlayer(@PathVariable long playerId, @RequestBody Player player){
        return playerService.updatePlayerById(playerId, player);
    }
    @DeleteMapping("/player/{playerId}")
    public ResponseEntity<Object> deletePlayerById(@PathVariable long playerId){
        return playerService.deletePlayerById(playerId);
    }
}

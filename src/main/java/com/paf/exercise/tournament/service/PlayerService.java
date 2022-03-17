package com.paf.exercise.tournament.service;

import com.paf.exercise.tournament.entity.Player;
import com.paf.exercise.tournament.entity.Tournament;
import com.paf.exercise.tournament.exception.ResourceNotFoundException;
import com.paf.exercise.tournament.repository.PlayerRepository;
import com.paf.exercise.tournament.repository.TournamentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PlayerService {
    private final TournamentRepository tournamentRepository;

    private final PlayerRepository playerRepository;

    public PlayerService(TournamentRepository tournamentRepository, PlayerRepository playerRepository) {
        this.tournamentRepository = tournamentRepository;
        this.playerRepository = playerRepository;
    }

    public List<Player> getAllPlayers() {

        List<Player> list = playerRepository.findAll();
        Collections.sort(list, new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                return Long.compare(p1.getTournament_id(), p2.getTournament_id());
            }
        });
        return list;
    }

    public Optional<Player> getPlayerById(Long playerId) {
        if (!playerRepository.existsById(playerId)) {
            throw new ResourceNotFoundException("Player with id " + playerId + " not found");
        }
        return playerRepository.findById(playerId);
    }

    public Player createPlayer(Long tournamentId, Player player) {
        Set<Player> players = new HashSet<Player>();
        Tournament tournament1 = new Tournament();

        Optional<Tournament> byId = tournamentRepository.findById(tournamentId);
        if (!byId.isPresent()) {


        }
        Tournament tournament = byId.get();

        //tie Tournament to Player
        player.setTournament(tournament);

        Player player1 = playerRepository.save(player);
        //tie Player to Tournament
        players.add(player1);
        tournament1.setPlayers(players);

        return player1;

    }

    public Player updatePlayerById(Long playerId, Player playerRequest) {
        if (!playerRepository.existsById(playerId)) {
            throw new ResourceNotFoundException("Player with id " + playerId + " not found");
        }
        Optional<Player> player = playerRepository.findById(playerId);

        if (!player.isPresent()) {
            throw new ResourceNotFoundException("Player with id " + playerId + " not found");
        }

        Player player1 = player.get();
        player1.setName(playerRequest.getName());


        return playerRepository.save(player1);
    }

    public ResponseEntity<Object> deletePlayerById(long playerId) {
        if (!playerRepository.existsById(playerId)) {
            throw new ResourceNotFoundException("Player with id " + playerId + " not found");
        }
        playerRepository.deleteById(playerId);
        return ResponseEntity.ok().build();

    }
}

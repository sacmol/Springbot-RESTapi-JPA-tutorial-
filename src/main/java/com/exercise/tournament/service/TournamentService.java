package com.exercise.tournament.service;

import com.exercise.tournament.entity.Tournament;
import com.exercise.tournament.entity.Player;
import com.exercise.tournament.exception.ResourceNotFoundException;
import com.exercise.tournament.repository.TournamentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TournamentService {
    private final TournamentRepository tournamentRepository;

    public TournamentService(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public List<Tournament> getTournaments() {
        return tournamentRepository.findAll();
    }


    public Optional<Tournament> getTournamentById(Long id) {
        if (!tournamentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tournament with id " + id + " not found");
        }
        return tournamentRepository.findById(id);
    }
    public Tournament createTournament(Tournament tournament) {
//        return tournamentRepository.save(tournament);

        List<Player> list = new ArrayList<>();
        Tournament t1 = new Tournament();
        t1.setReward_amount(tournament.getReward_amount());

        for (Player p: tournament.getPlayers()) {

                t1.addPlayer(p);
        }
    return tournamentRepository.save(t1);
    }
    public ResponseEntity<Object> deleteTournamentById(long id) {
        if (!tournamentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tournament with id " + id + " not found");
        }

        tournamentRepository.deleteById(id);

        return ResponseEntity.ok().build();

    }
    public Tournament updateTournamentById(Long tournamentId, Tournament tournamentRequest) throws ResourceNotFoundException{
        if (!tournamentRepository.existsById(tournamentId)) {
            throw new ResourceNotFoundException("Tournament with id " + tournamentId + " not found");
        }
        Optional<Tournament> tournament = tournamentRepository.findById(tournamentId);

        if (!tournament.isPresent()) {
            throw new ResourceNotFoundException("Tournament with id " + tournamentId + " not found");
        }

        Tournament tournament1 = tournament.get();
        tournament1.setReward_amount(tournamentRequest.getReward_amount());

        return tournamentRepository.save(tournament1);

    }
}

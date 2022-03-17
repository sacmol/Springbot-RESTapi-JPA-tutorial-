package com.paf.exercise.tournament.services;

import com.paf.exercise.tournament.entity.Tournament;
import com.paf.exercise.tournament.exception.GlobalExceptionHandler;
import com.paf.exercise.tournament.exception.ResourceNotFoundException;
import com.paf.exercise.tournament.repository.TournamentRepository;
import com.paf.exercise.tournament.service.TournamentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TournamentServiceTest {

    @Autowired
    TournamentService tournamentService;
    @MockBean
    TournamentRepository tournamentRepository;




    @Test
    public void get_tournament_by_id(){
        Tournament t = new Tournament();
        t.setTournament_id(1L);
        t.setReward_amount("55");

        doReturn(true).when(tournamentRepository).existsById(1l);
        doReturn(Optional.of(t)).when(tournamentRepository).findById(1l);

        Optional<Tournament> returnedTournament = tournamentService.getTournamentById(1l);

        Assertions.assertTrue(returnedTournament.isPresent(), "Tournament was not found");
        Assertions.assertSame(returnedTournament.get(), t, "The tournament returned was not the same as the mock");

    }

    @Test
    public void test_get_all_tournaments(){
        Tournament t = new Tournament();
        t.setTournament_id(1L);
        t.setReward_amount("55");
        Tournament tt = new Tournament();
        tt.setTournament_id(2L);
        tt.setReward_amount("66");

        doReturn(Arrays.asList(t, tt)).when(tournamentRepository).findAll();

        // Execute the service call
        List<Tournament> tournamentList = tournamentService.getTournaments();

        // Assert the response
        assertEquals(2, tournamentList.size(), "findAll should return 2 tournaments");

    }

    @Test
    public void add_tournament(){
        Tournament t = new Tournament();
        t.setTournament_id(1L);
        t.setReward_amount("55");

        doReturn(t).when(tournamentRepository).save(Mockito.any());

        // Execute the service call
        Tournament returnedTournament = tournamentService.createTournament(t);

        // Assert the response
        Assertions.assertNotNull(returnedTournament, "The saved tournament should not be null");

    }

    @Test
    public void test_find_byId_not_found(){

        // Setup our mock repository
        doReturn(Optional.empty()).when(tournamentRepository).findById(1l);

        ResourceNotFoundException notFoundException = assertThrows(ResourceNotFoundException.class,
                () -> tournamentService.getTournamentById(1L));

        assertEquals("Tournament with id "+ 1 +" not found", notFoundException.getMessage());
    }

    @Test()
    public void delete_tournament_by_id(){
        Tournament tournament = new Tournament();
        tournament.setTournament_id(1L);
        tournament.setReward_amount("55");

        when(tournamentRepository.existsById(tournament.getTournament_id())).thenReturn(true);

        tournamentService.deleteTournamentById(tournament.getTournament_id());
        verify(tournamentRepository).deleteById(tournament.getTournament_id());
    }
}

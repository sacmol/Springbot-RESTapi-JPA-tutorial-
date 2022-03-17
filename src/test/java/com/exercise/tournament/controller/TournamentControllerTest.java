package com.exercise.tournament.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.exercise.tournament.entity.Tournament;
import com.exercise.tournament.repository.TournamentRepository;
import com.exercise.tournament.service.PlayerService;
import com.exercise.tournament.service.TournamentService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WebMvcTest(TournamentController.class)
public class TournamentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TournamentService tournamentService;
    @MockBean
    PlayerService playerService;
    @MockBean
    TournamentRepository tournamentRepository;

    @Autowired
    ObjectMapper mapper;

    Tournament t = new Tournament();
    Tournament t1 = new Tournament();
    Tournament t2 = new Tournament();

    @BeforeAll
    public void setUppAll(){
        t.setTournament_id(1L);
        t.setReward_amount("111");
        t1.setTournament_id(2L);
        t1.setReward_amount("222");
        t2.setTournament_id(3L);
        t2.setReward_amount("333");
    }
    @Test
    public void get_all_tournaments_success() throws Exception{
//        t.setTournament_id(1L);
//        t.setReward_amount("111");
//        t1.setTournament_id(2L);
//        t1.setReward_amount("222");
//        t2.setTournament_id(3L);
//        t2.setReward_amount("333");

        List<Tournament> tournamentList = new ArrayList<>(Arrays.asList(t,t1,t2));

        Mockito.when(tournamentService.getTournaments()).thenReturn(tournamentList);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/tournament")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].reward_amount", is("333")));
    }

    @Test
    public void get_tournament_by_id_success() throws Exception {
        Mockito.when(tournamentService.getTournamentById(t.getTournament_id())).thenReturn(java.util.Optional.of(t));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/tournament/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.tournament_id", is(1)));

    }
    @Test
    public void add_tournament_success() throws Exception {
        Tournament t = new Tournament();
        t.setReward_amount("2222");


        Mockito.when(tournamentService.createTournament(Mockito.any())).thenReturn(t);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/v1/tournament")
               .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
               .content(this.mapper.writeValueAsString(t));

           mockMvc.perform(mockRequest)
               .andExpect(status().isOk())
                   .andDo(print())
                    .andExpect(jsonPath("$", notNullValue()))
                    .andExpect(jsonPath("$.reward_amount", is("2222")));

    }

    @Test
    public void update_tournament_success() throws Exception {

        Tournament updatedTournament = new Tournament();
        updatedTournament.setTournament_id(1L);
        updatedTournament.setReward_amount("88");

        Mockito.when(tournamentService.getTournamentById(Mockito.any())).thenReturn(java.util.Optional.of(t));
        Mockito.when(tournamentService.updateTournamentById(Mockito.any(),Mockito.any())).thenReturn(updatedTournament);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/v1/tournament/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(t));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.reward_amount", is("88")));

    }

    @Test
    public void delete_tournament_byId_success() throws Exception {

        Mockito.when(tournamentService.getTournamentById(t1.getTournament_id())).thenReturn(Optional.of(t1));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/v1/tournament/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String resultDOW = result.getResponse().getContentAsString();
        System.out.println(resultDOW + "ddddd");
        assertNotNull(resultDOW);
    }
}

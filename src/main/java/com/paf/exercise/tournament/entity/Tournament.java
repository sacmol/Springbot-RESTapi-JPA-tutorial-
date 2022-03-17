package com.paf.exercise.tournament.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="tournament")
public class Tournament implements Serializable {

    @Column(name = "ID", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long tournament_id;

    @Column(name = "RewardAmount")
    private String reward_amount;

    @OneToMany(mappedBy = "tournament",cascade = CascadeType.ALL,  fetch = FetchType.LAZY)
    private Set<Player> players = new HashSet<Player>();

    public Tournament() {
    }

    public long getTournament_id() {
        return tournament_id;
    }

    public void setTournament_id(long tournament_id) {
        this.tournament_id = tournament_id;
    }

    public String getReward_amount() {
        return reward_amount;
    }

    public void setReward_amount(String reward_amount) {
        this.reward_amount = reward_amount;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

    public void addPlayer(Player p) {
        players.add(p);
        p.setTournament(this);
    }

    public void removePlayer(Player p) {
        players.remove(p);
        p.setTournament(null);
    }

    @Override
    public String toString() {
        return "Tournament{" +
                "tournament_id=" + tournament_id +
                ", reward_amount='" + reward_amount + '\'' +
                ", players=" + players +
                '}';
    }
}

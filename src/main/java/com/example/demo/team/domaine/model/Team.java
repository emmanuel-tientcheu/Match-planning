package com.example.demo.team.domaine.model;

import com.example.demo.core.domain.model.BaseEntity;
import com.example.demo.player.domain.model.Player;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "teams")
public class Team extends BaseEntity<Team> {
    @Column()
    private String name;
    @OneToMany(
            mappedBy = "team",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Set<TeamMember> members;

    public Team() {}
    public Team(String id, String name) {
        super(id);
        this.name = name;
        this.members = new HashSet<>();
    }

    private Team(String id, String name, Set<TeamMember> members) {
        this.id = id;
        this.name = name;
        this.members = members;
    }

    public String getName() {
        return name;
    }
    public Boolean isInComplete() { return this.members.size() == 5;}

    public Set<TeamMember> getMembers() { return members;}
    
    public void addMember(String playerId, Role role) {
        if(this.members.stream().anyMatch(member -> member.getPlayerId().equals(playerId))) {
            throw new IllegalArgumentException("THIS PLAYER IS ALREADY IN THE TEAM");
        }

        if(this.members.stream().anyMatch(member -> member.getRole().equals(role))) {
            throw new IllegalArgumentException("THIS ROLE IS ALREADY TAKEN");
        }


        var member = new TeamMember(UUID.randomUUID().toString(), playerId, role, this.id);
        this.members.add(member);
    }

    public boolean hasMember(String playerId, Role role) {
       return this.members
                .stream()
                .anyMatch(member -> member.getPlayerId().equals(playerId) && member.getRole().equals(role));
    }

    public void removePLayer(String playerId) {
        if(this.members.stream().noneMatch(member -> member.getPlayerId().equals(playerId))) {
            throw new IllegalArgumentException("THIS NOT IS TEAM");
        }
        this.members.removeIf(member -> member.getPlayerId().equals(playerId));
    }

    @Override
    public Team deepClone() {
        return new Team(
                this.id,
                this.name,
                this.members.stream().map(TeamMember::deepClone).collect(Collectors.toSet())

        );
    }


    @Entity
    @Table(name = "team_members")
    public static class TeamMember extends BaseEntity<TeamMember>{
        @Column(name = "player_id")
        private String playerId;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "player_id", insertable = false, updatable = false)
        @MapsId("playerId")
        private Player player;

        @Column(name = "team_id")
        private String teamId;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "team_id", insertable = false, updatable = false)
        @MapsId("teamId")
        private Team team;

        @Column()
        @Enumerated(EnumType.STRING)
        private Role role;

        public TeamMember() {}
        public TeamMember(String id, String playerId, Role role, String teamId) {
            super(id);
            this.playerId = playerId;
            this.role = role;
            this.teamId = teamId;
        }

        public String getPlayerId() {
            return playerId;
        }

        public Role getRole() {
            return role;
        }

        public Player getPlayer() {
            return player;
        }

        @Override
        public TeamMember deepClone() {
            return new TeamMember(this.id, this.playerId, this.role, this.teamId);
        }
    }
}

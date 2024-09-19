package com.example.demo.team.domaine;

import com.example.demo.team.domaine.model.Role;
import com.example.demo.team.domaine.model.Team;
import org.junit.Assert;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class TeamTest {
   @Nested
    class AddMember{
       @Test
       void shouldJoinTeam() {
           var team = new Team("123", "team 1");
           team.addMember("player 1", Role.TOP);

           Assert.assertTrue(team.hasMember("player 1", Role.TOP));
       }

       @Test
       void shouldWhenPlayerIsAlreadyInTheTeam_shouldThrow() {
           var team = new Team("123", "team 1");
           team.addMember("player 1", Role.TOP);

           Assert.assertThrows(
                   IllegalArgumentException.class,
                   () -> team.addMember("player 1", Role.TOP)
           );
       }

       @Test
       void shouldWhenRoleIsAlreadyInTheTeam_shouldThrow() {
           var team = new Team("123", "team 1");
           team.addMember("player 1", Role.TOP);

           Assert.assertThrows(
                   IllegalArgumentException.class,
                   () -> team.addMember("player 2", Role.TOP)
           );
       }
   }

   @Nested
    class RemoveRemember{
       @Test
       void shouldRemoveMember() {
           var team = new Team("123", "team1");
           team.addMember("player1", Role.JUNGLE);
           team.removePLayer("player1");

           Assert.assertFalse(team.hasMember("player1", Role.JUNGLE));
       }
   }

    @Test
    void whenTeamIsInComplete_ShouldFail() {
        var team = new Team("123", "team1");
        team.addMember("player1", Role.JUNGLE);
       Assert.assertFalse(team.isInComplete());
    }

    @Test
    void whenTeamInComplete() {
        var team = new Team("123", "team1");
        team.addMember("player1", Role.JUNGLE);
        team.addMember("player2", Role.BOTTOM);
        team.addMember("player3", Role.SUPPORT);
        team.addMember("player4", Role.MIDDLE);
        team.addMember("player5", Role.TOP);
        Assert.assertTrue(team.isInComplete());
    }
}

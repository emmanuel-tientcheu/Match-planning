package com.example.demo.schedule.domaine;

import com.example.demo.schedule.domaine.model.Moment;
import com.example.demo.schedule.domaine.model.ScheduleDay;
import com.example.demo.team.domaine.model.Role;
import com.example.demo.team.domaine.model.Team;
import org.junit.Assert;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class ScheduleDayTests {

    private Team createTeam(String id) {
        var name = "TEAM " + id;
        var team = new Team(id, name);
        team.addMember(id + "-1", Role.TOP);
        team.addMember(id + "-2", Role.JUNGLE);
        team.addMember(id + "-3", Role.MIDDLE);
        team.addMember(id + "-4", Role.SUPPORT);
        team.addMember(id + "-5", Role.BOTTOM);
        return team;
    }

    private Team createIncompleteTeam(String id) {
        var name = "TEAM " + id;
        var team = new Team(id, name);
        team.addMember(id + "-1", Role.TOP);
        team.addMember(id + "-2", Role.JUNGLE);
        team.addMember(id + "-3", Role.MIDDLE);
        team.addMember(id + "-4", Role.SUPPORT);
        return team;
    }

    @Nested
    class Organize {
        @Test
        void shouldOrganise() {
            var t1 = createTeam("t1");
            var t2 = createTeam("t2");
            var moment = Moment.MORNING;

            var scheduleDay = new ScheduleDay("1", LocalDate.now());
            scheduleDay.organize(t1, t2, moment);

            var match = scheduleDay.getAt(Moment.MORNING);
            Assert.assertTrue(match.isPresent());
        }

        @Test
        void whenMomentUnavailable_ShouldThrow() {
            var t1 = createTeam("t1");
            var t2 = createTeam("t2");
            var t3 = createTeam("t3");
            var t4 = createTeam("t4");
            var moment = Moment.MORNING;

            var scheduleDay = new ScheduleDay("1", LocalDate.now());
            scheduleDay.organize(t1, t2, moment);

            Assert.assertThrows(
                    IllegalStateException.class,
                    () -> scheduleDay.organize(t3, t4, moment)
            );
        }

        @Test
        void whenTeamAlreadyPlayerInMorning_ShouldThrow() {
            var t1 = createTeam("t1");
            var t2 = createTeam("t2");
            var t3 = createTeam("t3");

            var moment = Moment.MORNING;

            var scheduleDay = new ScheduleDay("1", LocalDate.now());
            scheduleDay.organize(t1, t2, moment);

            Assert.assertThrows(
                    IllegalStateException.class,
                    () -> scheduleDay.organize(t3, t2, Moment.AFTERNOON)
            );
        }

        @Test
        void whenFirstTeamIsIncomplete_ShouldThrow() {
            var t1 = createIncompleteTeam("t1");
            var t2 = createTeam("t2");

            var scheduleDay = new ScheduleDay("1", LocalDate.now());

            Assert.assertThrows(
                    IllegalStateException.class,
                    () -> scheduleDay.organize(t1, t2, Moment.AFTERNOON)
            );
        }
    }

    @Nested
    class Cancel {
        @Test
        void ShouldCancelMatch() {
            var t1 = createTeam("t1");
            var t2 = createTeam("t2");

            var scheduleDay = new ScheduleDay("1", LocalDate.now());
            var organizeMatch = scheduleDay.organize(t1, t2, Moment.MORNING);

            scheduleDay.cancel(organizeMatch.getId());
            var match = scheduleDay.getAt(Moment.MORNING);

            Assert.assertTrue(match.isEmpty());
        }
    }
}

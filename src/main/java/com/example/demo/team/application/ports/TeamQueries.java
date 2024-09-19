package com.example.demo.team.application.ports;

import com.example.demo.team.domaine.viewmodel.TeamViewModel;

public interface TeamQueries {
    TeamViewModel getTeamById(String id);
}

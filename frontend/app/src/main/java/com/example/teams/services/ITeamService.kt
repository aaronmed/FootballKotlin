package com.example.teams.services

import android.content.Context
import com.example.teams.models.Team

interface ITeamService {
    fun getAll(context: Context, completionHandler: (response: ArrayList<Team>?) -> Unit)

    fun getById(context: Context, teamId: Int, completionHandler: (response: Team?) -> Unit)

    fun deleteById(context: Context, teamId: Int, completionHandler: () -> Unit)

    fun updateTeam(context: Context, team: Team, completionHandler: () -> Unit)

    fun createTeam(context: Context, team: Team, completionHandler: () -> Unit)
}
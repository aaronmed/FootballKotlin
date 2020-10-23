package com.example.teams.services

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.example.teams.models.Team
import org.json.JSONObject

class TeamServiceImpl : ITeamService {
    override fun getAll(context: Context, completionHandler: (response: ArrayList<Team>?) -> Unit) {
        val path = TeamSingleton.getInstance(context).baseUrl + "/api/teams"
        val arrayRequest = JsonArrayRequest(Request.Method.GET, path, null,
            { response ->
                var teams: ArrayList<Team> = ArrayList()
                for (i in 0 until response.length()) {
                    val team = response.getJSONObject(i)
                    val id = team.getInt("id")
                    val name = team.getString("name")
                    val stadium = team.getString("stadium")
                    val country = team.getString("country")
                    teams.add(Team(id, name, stadium, country))
                }
                completionHandler(teams)
            },
            { error ->
                completionHandler(ArrayList<Team>())
            })
        TeamSingleton.getInstance(context).addToRequestQueue(arrayRequest)
    }

    override fun getById(
        context: Context,
        teamId: Int,
        completionHandler: (response: Team?) -> Unit
    ) {
        val path = TeamSingleton.getInstance(context).baseUrl + "/api/teams/" + teamId
        val objectRequest = JsonObjectRequest(Request.Method.GET, path, null,
            { response ->
                if (response == null) completionHandler(null)

                val id = response.getInt("id")
                val name = response.getString("name")
                val stadium = response.getString("stadium")
                val country = response.getString("country")

                val team = Team(id, name, stadium, country)
                completionHandler(team)
            },
            { error ->
                completionHandler(null)
            })
        TeamSingleton.getInstance(context).addToRequestQueue(objectRequest)
    }

    override fun deleteById(context: Context, teamId: Int, completionHandler: () -> Unit) {
        val path = TeamSingleton.getInstance(context).baseUrl + "/api/teams/" + teamId
        val objectRequest = JsonObjectRequest(Request.Method.DELETE, path, null,
            { response ->
                completionHandler()
            },
            { error ->
                completionHandler()
            })
        TeamSingleton.getInstance(context).addToRequestQueue(objectRequest)
    }

    override fun updateTeam(context: Context, team: Team, completionHandler: () -> Unit) {
        val path = TeamSingleton.getInstance(context).baseUrl + "/api/teams/" + team.id
        val teamJson: JSONObject = JSONObject()
        teamJson.put("id", team.id.toString())
        teamJson.put("name", team.name)
        teamJson.put("stadium", team.stadium)
        teamJson.put("country", team.country)

        val objectRequest = JsonObjectRequest(Request.Method.PUT, path, teamJson,
            { response ->
                completionHandler()
            },
            { error ->
                completionHandler()
            })
        TeamSingleton.getInstance(context).addToRequestQueue(objectRequest)
    }

    override fun createTeam(context: Context, team: Team, completionHandler: () -> Unit) {
        val path = TeamSingleton.getInstance(context).baseUrl + "/api/teams"
        val teamJson: JSONObject = JSONObject()
        teamJson.put("id", team.id.toString())
        teamJson.put("name", team.name)
        teamJson.put("stadium", team.stadium)
        teamJson.put("country", team.country)

        val objectRequest = JsonObjectRequest(Request.Method.POST, path, teamJson,
            { response -> completionHandler() },
            { error -> completionHandler() })
        TeamSingleton.getInstance(context).addToRequestQueue(objectRequest)
    }
}
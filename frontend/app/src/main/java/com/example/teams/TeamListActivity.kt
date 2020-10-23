package com.example.teams

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teams.models.Team
import com.example.teams.services.TeamServiceImpl
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TeamListActivity : AppCompatActivity() {
    private lateinit var teams: ArrayList<Team>
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: TeamAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_list)

        teams = ArrayList<Team>()

        viewManager = LinearLayoutManager(this)
        viewAdapter = TeamAdapter(teams, this)

        recyclerView = findViewById<RecyclerView>(R.id.recyclerViewTeams)

        recyclerView.layoutManager = viewManager
        recyclerView.adapter = viewAdapter

        getAllTeams()

        val fab: FloatingActionButton = findViewById(R.id.floatingActionButton)
        fab.setOnClickListener {
            val intent = Intent(this, TeamDetailActivity::class.java)
            intent.putExtra("state", "Adding")
            startActivity(intent)
        }
    }

    private fun getAllTeams() {
        val teamServiceImpl = TeamServiceImpl()
        teamServiceImpl.getAll(this) { response ->
            run {
                if (response != null) {
                    viewAdapter.teamList = response
                }
                viewAdapter.notifyDataSetChanged()
            }
        }
    }
}

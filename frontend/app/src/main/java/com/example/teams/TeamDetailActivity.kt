package com.example.teams

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.example.teams.models.Team
import com.example.teams.services.TeamServiceImpl
import com.example.teams.services.TeamSingleton
import com.google.android.material.textfield.TextInputEditText
import com.squareup.picasso.Picasso

class TeamDetailActivity : AppCompatActivity() {
    private lateinit var state: String
    private lateinit var textInputEditTextName: EditText
    private lateinit var textInputEditTextStadium: EditText
    private lateinit var textInputEditTextCountry: EditText
    private lateinit var buttonEdit: Button
    private lateinit var buttonDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        state = this.intent.getStringExtra("state").toString()

        val teamId = this.intent.getIntExtra("teamId", 1)

        textInputEditTextName = findViewById(R.id.TextInputEditTextName)
        textInputEditTextStadium = findViewById(R.id.TextInputEditTextStadium)
        textInputEditTextCountry = findViewById(R.id.TextInputEditTextCountry)

        buttonDelete = findViewById(R.id.buttonDelete)
        buttonDelete.setOnClickListener {
            deleteTeam(teamId)
        }

        if (state == "Showing") getTeam(teamId)

        buttonEdit = findViewById(R.id.buttonEdit)
        buttonEdit.setOnClickListener {
            when (state) {
                "Showing" -> {
                    changeButtonsToEditing()
                }
                "Editing" -> {
                    val team = Team(
                        teamId,
                        textInputEditTextName.text.toString(),
                        textInputEditTextStadium.text.toString(),
                        textInputEditTextCountry.text.toString()
                    )
                    updateTeam(team)
                }
                "Adding" -> {
                    val team = Team(
                        teamId,
                        textInputEditTextName.text.toString(),
                        textInputEditTextStadium.text.toString(),
                        textInputEditTextCountry.text.toString()
                    )
                    createTeam(team)
                }
            }
        }
        if (state == "Adding") changeButtonsToAdding()
    }

    private fun getTeam(teamId: Int) {
        val teamServiceImpl = TeamServiceImpl()
        teamServiceImpl.getById(this, teamId) { response ->
            run {
                val txt_name: TextInputEditText = findViewById(R.id.TextInputEditTextName)
                val txt_stadium: TextInputEditText = findViewById(R.id.TextInputEditTextStadium)
                val txt_country: TextInputEditText = findViewById(R.id.TextInputEditTextCountry)
                val img: ImageView = findViewById(R.id.imageViewTeam)

                txt_name.setText(response?.name ?: "")
                txt_stadium.setText(response?.stadium ?: "")
                txt_country.setText(response?.country ?: "")

                val url = TeamSingleton.getInstance(this).baseUrl + "/img/teams-"
                val imageUrl = url + (response?.id.toString() ?: "0") + ".jpg"
                Picasso.with(this).load(imageUrl).into(img);
            }
        }
    }

    private fun createTeam(team: Team) {
        val teamServiceImpl = TeamServiceImpl()
        teamServiceImpl.createTeam(this, team) { ->
            run {
                changeButtonsToShowing(team.id)
                val intent = Intent(this, TeamListActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun updateTeam(team: Team) {
        val teamServiceImpl = TeamServiceImpl()
        teamServiceImpl.updateTeam(this, team) { ->
            run {
                changeButtonsToShowing(team.id)
                val intent = Intent(this, TeamListActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun deleteTeam(teamId: Int) {
        val teamServiceImpl = TeamServiceImpl()
        teamServiceImpl.deleteById(this, teamId) { ->
            run {
                val intent = Intent(this, TeamListActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun changeButtonsToAdding() {
        buttonDelete.visibility = View.GONE
        buttonDelete.isEnabled = false
        buttonEdit.setText("Add Team")
        textInputEditTextName.isEnabled = true
        textInputEditTextStadium.isEnabled = true
        textInputEditTextCountry.isEnabled = true
        state = "Adding"
    }

    private fun changeButtonsToShowing(teamId: Int) {
        buttonDelete.visibility = View.VISIBLE
        buttonDelete.isEnabled = true
        buttonEdit.setText("Edit Team")
        textInputEditTextName.isEnabled = true
        textInputEditTextStadium.isEnabled = true
        textInputEditTextCountry.isEnabled = true
        state = "Showing"
    }

    private fun changeButtonsToEditing() {
        buttonDelete.visibility = View.GONE
        buttonDelete.isEnabled = false
        buttonEdit.setText("Apply changes")
        textInputEditTextName.isEnabled = true
        textInputEditTextStadium.isEnabled = true
        textInputEditTextCountry.isEnabled = true
        state = "Editing"
    }
}
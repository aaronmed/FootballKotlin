package com.example.teams

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.teams.models.Team
import com.squareup.picasso.Picasso


class TeamAdapter(var teamList: ArrayList<Team>, val context: Context) : RecyclerView.Adapter<TeamAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.team_list_row, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(teamList[position], context)
    }

    override fun getItemCount(): Int {
        return teamList.size;
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(t: Team, context: Context){
            val url = "http://192.168.1.68:8080/img/teams-"
            val txt_name: TextView = itemView.findViewById(R.id.textViewName)
            val txt_stadium: TextView = itemView.findViewById(R.id.textViewStadium)
            val txt_country: TextView = itemView.findViewById(R.id.textViewCountry)
            val img: ImageView = itemView.findViewById(R.id.imageViewTeam)

            txt_name.text = t.name
            txt_stadium.text = t.stadium
            txt_country.text = t.country

            val imageUrl = url + t.id.toString() + ".jpg"
            Picasso.with(context).load(imageUrl).into(img);

            itemView.setOnClickListener {
                val intent = Intent(context, TeamDetailActivity::class.java)
                intent.putExtra("teamId", t.id)
                intent.putExtra("state", "Showing")
                context.startActivity(intent)
            }
        }
    }
}
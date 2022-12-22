package com.maruthimourya.games.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.maruthimourya.databinding.GameItemBinding
import com.maruthimourya.games.model.ResultsItem
import com.maruthimourya.games.view.GameResultsFragmentDirections

class GamesListAdapter(private val gamesList: List<ResultsItem>) :
    RecyclerView.Adapter<GameItemHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameItemHolder {
        this.context = parent.context
        val inflater = LayoutInflater.from(context)
        val binding = GameItemBinding.inflate(inflater, parent, false)
        return GameItemHolder(binding)
    }

    override fun onBindViewHolder(holder: GameItemHolder, position: Int) {
        val gameName = gamesList[position].name
        val url = gamesList[position].image.icon_url
        val imageUrl = gamesList[position].image.medium_url
        holder.gameTitle.text = gameName
        Glide.with(holder.gameIcon).load(url).into(holder.gameIcon)
        holder.itemView.setOnClickListener {
            val action =
                GameResultsFragmentDirections.actionGameResultsFragmentToGameDetailFragment(
                    gameName,
                    imageUrl
                )
            Navigation.findNavController(holder.itemView).navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return gamesList.size
    }
}

class GameItemHolder(binding: GameItemBinding) : RecyclerView.ViewHolder(binding.root) {
    var gameTitle = binding.gameTitle
    var gameIcon = binding.gameImage
}
package com.maruthimourya.games.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.maruthimourya.databinding.FragmentGameDetailBinding

class GameDetailFragment : Fragment() {

    private lateinit var gameDetailBinding: FragmentGameDetailBinding
    private val args: GameDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        gameDetailBinding = FragmentGameDetailBinding.inflate(layoutInflater, container, false)
        return gameDetailBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gameName = args.gameTitle
        val gamePhoto = args.gameImage

        gameDetailBinding.apply {
            gameTitle.text = gameName
            Glide.with(gameImage).load(gamePhoto).into(gameImage)
        }
    }

}
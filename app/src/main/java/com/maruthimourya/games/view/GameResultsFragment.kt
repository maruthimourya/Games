package com.maruthimourya.games.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.maruthimourya.databinding.FragmentGameResultsBinding
import com.maruthimourya.games.adapter.GamesListAdapter
import com.maruthimourya.games.viewModel.GameResultsFragmentViewModel
import io.reactivex.rxjava3.core.Observable


class GameResultsFragment : Fragment() {

    private lateinit var gameResultsBinding: FragmentGameResultsBinding
    private lateinit var dialog: LoadingDialog
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: GamesListAdapter


    private val viewModel: GameResultsFragmentViewModel by lazy {
        ViewModelProvider(this)[GameResultsFragmentViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        gameResultsBinding = FragmentGameResultsBinding.inflate(layoutInflater, container, false)
        return gameResultsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog = LoadingDialog(this.requireActivity())
        dialog.startLoading()

        viewModel.getResultsFromWeb()

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage.getContentIfNotHandled()?.let {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }

        layoutManager = LinearLayoutManager(context)
        gameResultsBinding.gamesList.layoutManager = layoutManager

        viewModel.list.observe(viewLifecycleOwner) { gameList ->
            if (gameList != null) {
                adapter = GamesListAdapter(gameList)
                adapter.notifyDataSetChanged()
                gameResultsBinding.gamesList.adapter = adapter
                dialog.dismiss()
            }
        }

    }

    fun SearchView.addRxTextWatcher(): Observable<String?> {

        val flowable = Observable.create<String?> {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String?): Boolean {
                    TODO("Not yet implemented")
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    TODO("Not yet implemented")
                }

            })
        }

        return flowable
    }

}
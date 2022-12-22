package com.maruthimourya.games.view

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.maruthimourya.R
import com.maruthimourya.databinding.FragmentGameResultsBinding
import com.maruthimourya.games.adapter.GamesListAdapter
import com.maruthimourya.games.extension.addRxTextWatcherSearchView
import com.maruthimourya.games.viewModel.GameResultsFragmentViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit


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
    ): View {
        // Inflate the layout for this fragment
        gameResultsBinding = FragmentGameResultsBinding.inflate(layoutInflater, container, false)
        return gameResultsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getGameList(view)

        errorObserver()

        setUpRecyclerView()

        listObserver()

    }

    private fun getGameList(view: View) {
        val apiKey = getString(R.string.api_key)
        val format = getString(R.string.json_text)
        val searchQuery = getString(R.string.name_text)
        gameResultsBinding.apply {
            searchText.addRxTextWatcherSearchView()
                .debounce(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (!TextUtils.isEmpty(it)) {
                        //DO api request
                        view.hideKeyboard()
                        searchText.clearFocus()
                        dialog = LoadingDialog(requireActivity())
                        dialog.startLoading()
                        viewModel.getResultsFromWeb(apiKey, format, searchQuery + it, getString(R.string.results_text))
                    } else {
                        view.hideKeyboard()
                        searchText.clearFocus()
                        gamesList.visibility = View.GONE
                    }
                }
        }
    }

    private fun setUpRecyclerView() {
        layoutManager = LinearLayoutManager(context)
        gameResultsBinding.gamesList.layoutManager = layoutManager
    }

    private fun errorObserver() {
        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            dialog.dismiss()
            gameResultsBinding.gamesList.visibility = View.GONE
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }
    }


    private fun listObserver() {
        val message = getString(R.string.results_text)
        gameResultsBinding.apply {
            viewModel.gameList.observe(viewLifecycleOwner) { gameList ->
                gamesList.visibility = View.VISIBLE
                if (gameList != null) {
                    adapter = GamesListAdapter(gameList)
                    gamesList.adapter = adapter
                    dialog.dismiss()
                } else {
                    gamesList.visibility = View.GONE
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun View.hideKeyboard() {
        val inputManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

}
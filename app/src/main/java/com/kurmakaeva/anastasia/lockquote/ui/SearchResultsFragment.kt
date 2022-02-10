package com.kurmakaeva.anastasia.lockquote.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.kurmakaeva.anastasia.lockquote.R
import com.kurmakaeva.anastasia.lockquote.adapter.SongSearchAdapter
import com.kurmakaeva.anastasia.lockquote.adapter.SongSearchAdapterListener
import com.kurmakaeva.anastasia.lockquote.databinding.FragmentSearchResultsBinding
import com.kurmakaeva.anastasia.lockquote.viewmodel.SearchViewModel
import com.kurmakaeva.anastasia.lockquote.viewmodel.SearchViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchResultsFragment : Fragment(), SongSearchAdapterListener {

    private lateinit var binding: FragmentSearchResultsBinding
    private var currentSearch: String? = null
    private lateinit var adapter: SongSearchAdapter

    private val viewModel: SearchViewModel by viewModels()

    private val args by navArgs<SearchResultsFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_search_results,
            container,
            false
        )

        viewModel.clearSnackbarErrors()

        if (currentSearch == null) { currentSearch = args.query }
        currentSearch?.let { performSearch(it) }

        adapterSetup()

        searchViewSetUp()

        viewModel.showSnackbar.observe(viewLifecycleOwner, Observer {
            it?.let {
                val errorText = it
                showSnackbarWithError(it.replace(errorText, getString(R.string.error_something_wrong)))
            }
        })

        return binding.root
    }

    override fun onShowDetails(position: Int) {
        val action = SearchResultsFragmentDirections.actionSearchResultsFragmentToLyricWebViewFragment(position)
        this.findNavController().navigate(action)
    }

    private fun performSearch(term: String) {
        showLoadingProgress()
        viewModel.refresh(term)
    }

    private fun searchViewSetUp() {
        val searchView: SearchView = binding.searchViewSearchResults
        searchView.queryHint = args.query
        searchView.onActionViewExpanded()

        val backgroundView = searchView.findViewById<View>(R.id.search_plate)
        backgroundView.background = null

        Handler().postDelayed({ searchView.clearFocus() }, 0)

        val searchManager = this.activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchView.clearFocus()
                currentSearch = query
                performSearch(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchView.queryHint = getString(R.string.query_hint)
                return false
            }
        })
    }

    private fun adapterSetup() {
        adapter = SongSearchAdapter(this, requireContext())
        binding.searchResultRecyclerView.adapter = adapter
        binding.searchResultRecyclerView.setHasFixedSize(true)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.searchResults.collect {
                    when (it) {
                        SearchViewState.Error -> {
                            // handle error
                        }
                        SearchViewState.Loading -> {
                           showLoadingProgress()
                        }
                        is SearchViewState.Success -> {
                            adapter.submitList(it.listOfSongs)
                            hideLoadingProgress()
                        }
                    }
                }
            }
            showLoadingProgress()
        }
    }

    private fun showLoadingProgress() {
        binding.loadingSearchResults.visibility = View.VISIBLE
        binding.searchResultRecyclerView.visibility = View.GONE

    }

    private fun hideLoadingProgress() {
        binding.loadingSearchResults.visibility = View.GONE
        binding.searchResultRecyclerView.visibility = View.VISIBLE
    }

    private fun showSnackbarWithError(errorMessage: String) {
        Snackbar
            .make(requireActivity()
                .findViewById(android.R.id.content), errorMessage, Snackbar.LENGTH_LONG)
            .setTextColor(
                ContextCompat.getColor(requireContext(), R.color.whiteColor)
            )
            .show()
    }
}
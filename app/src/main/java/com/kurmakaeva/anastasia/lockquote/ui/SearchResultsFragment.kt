package com.kurmakaeva.anastasia.lockquote.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.kurmakaeva.anastasia.lockquote.R
import com.kurmakaeva.anastasia.lockquote.adapter.SongSearchAdapter
import com.kurmakaeva.anastasia.lockquote.adapter.SongSearchAdapterListener
import com.kurmakaeva.anastasia.lockquote.databinding.FragmentSearchResultsBinding
import com.kurmakaeva.anastasia.lockquote.viewmodel.SearchViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchResultsFragment : Fragment(), SongSearchAdapterListener {

    private lateinit var binding: FragmentSearchResultsBinding
    private var currentSearch: String? = null
    private lateinit var adapter: SongSearchAdapter

    private val viewModel by viewModel<SearchViewModel>()

    private val args by navArgs<SearchResultsFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_search_results,
            container,
            false
        )

        binding.lifecycleOwner = this

        if (currentSearch == null) { currentSearch = args.query }
        currentSearch?.let { performSearch(it) }

        adapterSetup()

        searchViewSetUp()

//        viewModel.showSnackbar.observe(viewLifecycleOwner, Observer {
//            if (it == true) {
//                Snackbar
//                    .make(requireActivity()
//                    .findViewById(android.R.id.content), getString(R.string.error_something_wrong), Snackbar.LENGTH_LONG)
//                    .setTextColor(ContextCompat.getColor(requireContext(), R.color.whiteColor)
//                    )
//                    .show()
//             //   binding.internetStatusLL.visibility = View.VISIBLE
//            }
//        })

        return binding.root
    }

    override fun onShowDetails(position: Int) {
        val action = SearchResultsFragmentDirections.actionSearchResultsFragmentToLyricWebViewFragment(position)
        this.findNavController().navigate(action)
    }

    private fun performSearch(term: String) {
        showLoadingProgress()
        viewModel.getSearchResults(term)
    }

    private fun searchViewSetUp() {
        val searchView: SearchView = binding.searchViewSearchResults
        searchView.queryHint = args.query
        searchView.onActionViewExpanded()

        val backgroundView = searchView.findViewById<View>(androidx.appcompat.R.id.search_plate)
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
            showLoadingProgress()
            viewModel.searchResults.observe(viewLifecycleOwner, Observer {
                adapter.submitList(it)
                hideLoadingProgress()
            })
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
}
package com.example.android.lockquote

import android.app.ActionBar
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.lockquote.adapter.GeniusSongSearchAdapter
import com.example.android.lockquote.repository.GeniusRepo
import com.example.android.lockquote.service.GeniusSearchService
import com.example.android.lockquote.viewmodel.SearchViewModel
import com.example.android.lockquote.viewmodel.SearchViewModel.*
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity(), GeniusSongSearchAdapter.SongSearchAdapterListener {

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var songSearchAdapter: GeniusSongSearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setTitle(R.string.search_results)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupViewModels()
        updateControls()

        searchViewSetUp()

        handleIntent(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home ->
                this.onBackPressed()
            // Respond to the action bar's Up/Home button
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }

    override fun onShowDetails(songSummaryViewData: SongSummaryViewData) {

    }

    private fun performSearch(term: String) {
        searchViewModel.searchSongs(term) { results ->
            songSearchAdapter.setSearchData(results)
        }
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra("query")
            performSearch(query)
        }
    }

    private fun setupViewModels() {
        val searchService = GeniusSearchService.instance
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        searchViewModel.geniusRepo = GeniusRepo(searchService)
    }

    private fun updateControls() {
        searchResultRecyclerView.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(this)
        searchResultRecyclerView.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(searchResultRecyclerView.context, layoutManager.orientation)
        searchResultRecyclerView.addItemDecoration(dividerItemDecoration)

        songSearchAdapter = GeniusSongSearchAdapter(null, this, this)
        searchResultRecyclerView.adapter = songSearchAdapter
    }

    private fun searchViewSetUp() {
        val searchView: SearchView = findViewById(R.id.search_view_search_results)
        searchView.queryHint = intent.getStringExtra("query")
        searchView.onActionViewExpanded()

        val backgroundView = searchView.findViewById<View>(androidx.appcompat.R.id.search_plate)
        backgroundView.background = null

        Handler().postDelayed({ searchView.clearFocus() }, 0)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(
            searchManager.getSearchableInfo(componentName))

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchView.clearFocus()
                performSearch(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchView.queryHint = getString(R.string.query_hint)
                return false
            }
        })
    }
}

package com.example.android.lockquote

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import android.os.Handler
import android.util.Log
import android.view.View
import com.example.android.lockquote.repository.GeniusRepo
import com.example.android.lockquote.service.GeniusSearchService

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchViewSetUp()
    }

    private fun searchViewSetUp() {
        val searchView: SearchView = findViewById(R.id.search_view_main)
        searchView.queryHint = getString(R.string.query_hint)
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
                val intent = Intent(this@MainActivity, SearchActivity::class.java)
                intent.putExtra("query", query)
                intent.action = Intent.ACTION_SEARCH
                startActivity(intent)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }
}
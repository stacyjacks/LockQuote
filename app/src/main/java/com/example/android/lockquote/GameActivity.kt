package com.example.android.lockquote

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cdflynn.android.library.checkview.CheckView
import com.example.android.lockquote.adapter.EditTextRecyclerViewAdapter
import com.example.android.lockquote.adapter.RecyclerViewEditTextListener


class GameActivity : AppCompatActivity(), RecyclerViewEditTextListener {
    lateinit var editTextRecyclerView: RecyclerView
    lateinit var adapter: EditTextRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        handleIntent(intent)

        setTitle(R.string.generated_pass_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val taskOneTextView = findViewById<TextView>(R.id.helpfulTextTaskOne)
        val taskOneHelpfulText = getString(R.string.taskOneInputFullPassword)

        taskOneTextView.text = taskOneHelpfulText
        editTextRecyclerView = findViewById(R.id.editTextRV)
        editTextRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adapter = EditTextRecyclerViewAdapter(this, passwordString())
        editTextRecyclerView.adapter = adapter
        editTextRecyclerView.setHasFixedSize(true)

        val clearButton = findViewById<Button>(R.id.clearPassButton)
        clearButton.setOnClickListener {
            editTextRecyclerView.adapter = null
            editTextRecyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home ->
                this.onBackPressed()
            // Respond to the action bar's Up/Home button
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCorrectTextInputCallback() {
        hideSoftKeyboard(this)
        editTextRecyclerView.addOnItemTouchListener(object: RecyclerView.SimpleOnItemTouchListener() {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                return true
            }
        })
        val clearButton = findViewById<Button>(R.id.clearPassButton)
        clearButton.visibility = View.GONE
        val checkAnimation = findViewById<CheckView>(R.id.checkViewAnimation)
        checkAnimation.visibility = CheckView.VISIBLE
        checkAnimation.check()
        val continueButton = findViewById<Button>(R.id.continueButtonTaskOne)
        continueButton.visibility = View.VISIBLE
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        passwordString()
    }

    private fun passwordString(): String {
        return intent.getStringExtra("modPasswordString")
    }
    
    fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager: InputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            activity.currentFocus?.windowToken, 0
        )
    }
}

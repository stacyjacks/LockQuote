package com.example.android.lockquote.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.android.lockquote.R

class GameActivity : AppCompatActivity(),
    OnDataPass {

    companion object {
        const val TAG_GAME_TASK_ONE_FRAGMENT = "GameTaskOneFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        showGameTaskOneFragment()
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
    }

    override fun onDataPass(data: String) {
    }

    override fun passwordString(): String {
        return intent.getStringExtra("modPasswordString")
    }

    override fun selectedLyric(): String {
        return intent.getStringExtra("selectedLyric")
    }

    private fun createGameTaskOneFragment(): GameTaskOneFragment {
        var gameTaskOneFragment = supportFragmentManager.findFragmentByTag(
            TAG_GAME_TASK_ONE_FRAGMENT
        ) as GameTaskOneFragment?

        if (gameTaskOneFragment == null) {
            gameTaskOneFragment =
                GameTaskOneFragment.newInstance()
        }
        return gameTaskOneFragment
    }

    private fun showGameTaskOneFragment() {
        val gameTaskOneFragment = createGameTaskOneFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.frameFragmentGame, gameTaskOneFragment,
                TAG_GAME_TASK_ONE_FRAGMENT
            )
            .commit()
    }

    fun showError(context: Context, message: String) {
        AlertDialog.Builder(context)
            .setMessage(message)
            .setPositiveButton(context.getString(R.string.ok_button), null)
            .create()
            .show()
    }
}

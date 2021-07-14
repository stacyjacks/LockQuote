package com.kurmakaeva.anastasia.lockquote.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.kurmakaeva.anastasia.lockquote.R

class GameActivity : AppCompatActivity() {
    private val appBarConfiguration =
        AppBarConfiguration(setOf(R.id.main_navigation, R.id.game_navigation))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_game)

        val navController = this.findNavController(R.id.nav_game_host_fragment)
        navController.setGraph(R.navigation.game_navigation, intent.extras)
        setupActionBarWithNavController(navController, appBarConfiguration)

        supportActionBar?.elevation = 0f

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = (ContextCompat.getColor(this, R.color.colorAccentDark))

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_game_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun showError(context: Context, message: String) {
        AlertDialog.Builder(context)
            .setMessage(message)
            .setPositiveButton(context.getString(R.string.ok_button), null)
            .create()
            .show()
    }
}

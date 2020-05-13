package com.example.android.lockquote
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.plattysoft.leonids.ParticleSystem


class GameResultFragment: Fragment(), OnDataPass {

    lateinit var dataPass: OnDataPass

    companion object {
        fun newInstance(): GameResultFragment {
            return GameResultFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_game_result, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.about_item) {
            showInfo() }
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fireworks = view.findViewById<ImageView>(R.id.fireworks)
        val password = view.findViewById<TextView>(R.id.passwordFinal)
        val anotherGo = view.findViewById<Button>(R.id.anotherGoButton)

        password.text = passwordString()
        password.setTextIsSelectable(true)
        fireworks.setOnClickListener { fireworkAnimation(view) }
        anotherGo.setOnClickListener {
            activity?.finish()
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataPass = context as OnDataPass
    }

    override fun onDataPass(data: String) {
        dataPass.onDataPass(data)
    }

    override fun passwordString(): String {
        return dataPass.passwordString()
    }

    override fun selectedLyric(): String {
        return dataPass.selectedLyric()
    }

    private fun fireworkAnimation(view: View) {
        ParticleSystem(activity, 100, R.drawable.firework_red, 5000)
            .setSpeedRange(0.1f, 0.25f)
            .setRotationSpeedRange(90f, 180f)
            .setInitialRotationRange(0, 360)
            .emit(view.findViewById(R.id.fireworks), 100)
    }

    private fun showInfo() {
        val dialogTitle = getString(R.string.about_title, BuildConfig.VERSION_NAME)
        val dialogMessage = getString(R.string.about_message)
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(dialogTitle)
        builder.setMessage(dialogMessage)
        builder.create().show()
    }
}
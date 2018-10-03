package ch.liip.sweetpreferencesdemo

import android.app.Activity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import ch.liip.sweetpreferences.SweetPreferences

class MainActivity : Activity() {

    private lateinit var preferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sweetPreferences = SweetPreferences.Builder().withDefaultSharedPreferences(this).build()
        preferences = UserPreferences(sweetPreferences)

        button.setOnClickListener {
            preferences.counter += 1
            setButtonValue()
        }

        setButtonValue()
    }

    private fun setButtonValue() {
        button.text = preferences.counter.toString()
    }
}

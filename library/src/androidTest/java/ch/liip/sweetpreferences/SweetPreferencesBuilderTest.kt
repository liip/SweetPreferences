package ch.liip.sweetpreferences

import android.content.Context
import android.preference.PreferenceManager
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test


class SweetPreferencesBuilderTest {

    @Test(expected = IllegalStateException::class)
    fun throwsWithoutPreferences() {
        val builder = SweetPreferences.Builder()

        // Exception
        builder.build()
    }

    @Test
    fun takesTheGivenPreference() {

        val context = InstrumentationRegistry.getInstrumentation().context
        val systemPrefs = context.getSharedPreferences("test", Context.MODE_PRIVATE)
        systemPrefs.edit().clear().commit()

        val preferences = SweetPreferences.Builder().with(systemPrefs).build()

        preferences.set("key", "value")

        // Make sure they saved in the same preference
        assertTrue(systemPrefs.contains("key"))
    }

    @Test
    fun takesTheDefaultPreference() {

        val context = InstrumentationRegistry.getInstrumentation().context
        val defaultPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        defaultPrefs.edit().clear().commit()
        val testPrefs = context.getSharedPreferences("test", Context.MODE_PRIVATE)
        testPrefs.edit().clear().commit()

        val preferences = SweetPreferences.Builder().withDefaultSharedPreferences(context).build()

        preferences.set("key", "value")

        // Make sure they saved in the same preference
        assertTrue(defaultPrefs.contains("key"))
        assertFalse(testPrefs.contains("key"))
    }
}
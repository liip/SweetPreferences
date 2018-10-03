package ch.liip.sweetpreferences

import android.content.Context
import android.preference.PreferenceManager
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SweetPreferencesBuilderTest {

    @Test(expected = IllegalStateException::class)
    fun throwsWithoutPreferences() {
        val builder = SweetPreferences.Builder()

        // Exception
        builder.build()
    }

    @Test
    fun takesTheGivenPreference() {

        val context = InstrumentationRegistry.getTargetContext()
        val systemPrefs = context.getSharedPreferences("test", Context.MODE_PRIVATE)
        systemPrefs.edit().clear().commit()

        val preferences = SweetPreferences.Builder().with(systemPrefs).build()

        preferences.set("key", "value")

        // Make sure they saved in the same preference
        assertTrue(systemPrefs.contains("key"))
    }

    @Test
    fun takesTheDefaultPreference() {

        val context = InstrumentationRegistry.getTargetContext()
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
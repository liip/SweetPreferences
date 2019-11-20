package ch.liip.sweetpreferences

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SweetPreferencesTest {

    private lateinit var prefs: SweetPreferences
    private lateinit var testClass: PreferenceTestClass

    @Before
    fun setup() {

        val context = InstrumentationRegistry.getInstrumentation().context
        val systemPrefs = context.getSharedPreferences("test", Context.MODE_PRIVATE)
        systemPrefs.edit().clear().commit()

        prefs = SweetPreferences(systemPrefs)
        testClass = PreferenceTestClass(prefs)
    }

    @Test
    fun values() {

        // Empty values
        assertFalse(testClass.boolean)
        assertEquals("cool", testClass.string)
        assertNull(testClass.optString)
        assertEquals(1, testClass.int)
        assertEquals(1f, testClass.float)
        assertEquals(1, testClass.long)

        testClass.boolean = true
        assertTrue(testClass.boolean)

        testClass.string = "Yeah"
        assertEquals("Yeah", testClass.string)

        testClass.optString = "Yeah"
        assertEquals("Yeah", testClass.optString)

        testClass.int = 1337
        assertEquals(1337, testClass.int)

        testClass.float = 1337f
        assertEquals(1337f, testClass.float)

        testClass.long = 1337
        assertEquals(1337, testClass.long)
    }

    @Test
    fun contains() {
        assertFalse(prefs.contains("boolean"))
        assertFalse(prefs.contains("string"))
        assertFalse(prefs.contains("optString"))
        assertFalse(prefs.contains("int"))
        assertFalse(prefs.contains("float"))
        assertFalse(prefs.contains("long"))

        testClass.boolean = true
        testClass.string = "Yeah"
        testClass.optString = "Yeah"
        testClass.int = 1337
        testClass.float = 1337f
        testClass.long = 1337

        assertTrue(prefs.contains("boolean"))
        assertTrue(prefs.contains("string"))
        assertTrue(prefs.contains("optString"))
        assertTrue(prefs.contains("int"))
        assertTrue(prefs.contains("float"))
        assertTrue(prefs.contains("long"))
    }

    @Test
    fun remove() {
        testClass.boolean = true
        testClass.string = "Yeah"
        testClass.optString = "Yeah"
        testClass.int = 1337
        testClass.float = 1337f
        testClass.long = 1337

        prefs.remove("boolean")
        prefs.remove("string")
        prefs.remove("optString")
        prefs.remove("int")
        prefs.remove("float")
        prefs.remove("long")

        assertFalse(prefs.contains("boolean"))
        assertFalse(prefs.contains("string"))
        assertFalse(prefs.contains("optString"))
        assertFalse(prefs.contains("int"))
        assertFalse(prefs.contains("float"))
        assertFalse(prefs.contains("long"))
    }

    @Test
    fun clear() {
        testClass.boolean = true
        testClass.string = "Yeah"
        testClass.optString = "Yeah"
        testClass.int = 1337
        testClass.float = 1337f
        testClass.long = 1337

        prefs.clear()

        assertFalse(prefs.contains("boolean"))
        assertFalse(prefs.contains("string"))
        assertFalse(prefs.contains("optString"))
        assertFalse(prefs.contains("int"))
        assertFalse(prefs.contains("float"))
        assertFalse(prefs.contains("long"))
    }
}

private class PreferenceTestClass(preferences: SweetPreferences) {
    var boolean: Boolean by preferences.delegate(false)
    var string: String by preferences.delegate("cool")
    var optString: String? by preferences.delegate(null)
    var int: Int by preferences.delegate(1)
    var float: Float by preferences.delegate(1f)
    var long: Long by preferences.delegate(1)
}

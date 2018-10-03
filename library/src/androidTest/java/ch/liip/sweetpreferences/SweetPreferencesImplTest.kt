package ch.liip.sweetpreferences

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SweetPreferencesImplTest {

    private lateinit var prefs: SweetPreferencesImpl
    private lateinit var testClass: PreferenceTestClass

    @Before
    fun setup() {

        val context = InstrumentationRegistry.getTargetContext()
        val systemPrefs = context.getSharedPreferences("test", Context.MODE_PRIVATE)
        systemPrefs.edit().clear().commit()

        prefs = SweetPreferencesImpl(systemPrefs)
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

private class PreferenceTestClass(preferencesImpl: SweetPreferencesImpl) {
    var boolean: Boolean by preferencesImpl.delegate(false)
    var string: String by preferencesImpl.delegate("cool")
    var optString: String? by preferencesImpl.delegate(null)
    var int: Int by preferencesImpl.delegate(1)
    var float: Float by preferencesImpl.delegate(1f)
    var long: Long by preferencesImpl.delegate(1)
}

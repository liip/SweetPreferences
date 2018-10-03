package ch.liip.sweetpreferences

import android.content.SharedPreferences
import kotlin.reflect.KClass


internal class SweetPreferencesImpl constructor(
        private val sharedPreferences: SharedPreferences
) : SweetPreferences {

    override fun set(key: String, value: Any?) {
        when (value) {
            is String? -> edit { putString(key, value) }
            is Int -> edit { putInt(key, value) }
            is Boolean -> edit { putBoolean(key, value) }
            is Float -> edit { putFloat(key, value) }
            is Long -> edit { putLong(key, value) }
            else -> throw UnsupportedOperationException("Could not serialize preference $key")
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T> get(key: String, defaultValue: T, klass: KClass<*>): T {
        return when (klass) {
            String::class -> sharedPreferences.getString(key, defaultValue as? String) as T
            Int::class -> sharedPreferences.getInt(key, defaultValue as? Int ?: -1) as T
            Boolean::class -> sharedPreferences.getBoolean(key, defaultValue as? Boolean
                    ?: false) as T
            Float::class -> sharedPreferences.getFloat(key, defaultValue as? Float ?: -1f) as T
            Long::class -> sharedPreferences.getLong(key, defaultValue as? Long ?: -1) as T
            else -> throw UnsupportedOperationException("Could not deserialize preference $key")
        }
    }

    override fun contains(key: String): Boolean {
        return sharedPreferences.contains(key)
    }

    override fun remove(key: String) {
        edit { remove(key) }
    }

    override fun clear() {
        edit { clear() }
    }

    private inline fun edit(operation: SharedPreferences.Editor.() -> Unit) {
        val editor = sharedPreferences.edit()
        operation(editor)
        editor.apply()
    }
}

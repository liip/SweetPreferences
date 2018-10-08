package ch.liip.sweetpreferences

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty


class SweetPreferences internal constructor(
        private val sharedPreferences: SharedPreferences
) {

    /**
     * puts a key value pair in shared prefs if doesn't exists, otherwise updates value on given [key]
     */
    fun set(key: String, value: Any?) {
        when (value) {
            is String? -> edit { putString(key, value) }
            is Int -> edit { putInt(key, value) }
            is Boolean -> edit { putBoolean(key, value) }
            is Float -> edit { putFloat(key, value) }
            is Long -> edit { putLong(key, value) }
            else -> throw UnsupportedOperationException("Could not serialize preference $key")
        }
    }

    /**
     * finds value on given key.
     * [T] is the type of value
     * @param defaultValue optional default value - will take null for strings, false for bool and -1 for numeric values if [defaultValue] is not specified
     */
    @Suppress("UNCHECKED_CAST")
    fun <T> get(key: String, defaultValue: T, klass: KClass<*>): T {
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

    fun contains(key: String): Boolean {
        return sharedPreferences.contains(key)
    }

    fun remove(key: String) {
        edit { remove(key) }
    }

    fun clear() {
        edit { clear() }
    }

    inline fun <reified T> get(key: String, defaultValue: T? = null): T? {
        return this.get(key, defaultValue, T::class)
    }

    inline fun <reified T> delegate(
            defaultValue: T,
            key: String? = null
    ): ReadWriteProperty<Any, T> {
        return object : ReadWriteProperty<Any, T> {
            override fun getValue(thisRef: Any, property: KProperty<*>) = this@SweetPreferences.get(key
                    ?: property.name, defaultValue, T::class)

            override fun setValue(thisRef: Any, property: KProperty<*>, value: T) = this@SweetPreferences.set(key
                    ?: property.name, value)
        }
    }

    private inline fun edit(operation: SharedPreferences.Editor.() -> Unit) {
        val editor = sharedPreferences.edit()
        operation(editor)
        editor.apply()
    }

    class Builder {
        private var sharedPreferences: SharedPreferences? = null

        fun with(sharedPreferences: SharedPreferences) = apply {
            this.sharedPreferences = sharedPreferences
        }

        fun withDefaultSharedPreferences(context: Context) = apply {
            this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        }

        fun build(): SweetPreferences {
            val prefs = sharedPreferences
                    ?: throw IllegalStateException("Must pass a shared preference")
            return SweetPreferences(prefs)
        }
    }
}

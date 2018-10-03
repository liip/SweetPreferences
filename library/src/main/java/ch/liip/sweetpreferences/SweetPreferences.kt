package ch.liip.sweetpreferences

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty


interface SweetPreferences {
    /**
     * puts a key value pair in shared prefs if doesn't exists, otherwise updates value on given [key]
     */
    fun set(key: String, value: Any?)

    /**
     * finds value on given key.
     * [T] is the type of value
     * @param defaultValue optional default value - will take null for strings, false for bool and -1 for numeric values if [defaultValue] is not specified
     */
    fun <T> get(key: String, defaultValue: T, klass: KClass<*>): T

    fun contains(key: String): Boolean

    fun remove(key: String)

    fun clear()

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
            return SweetPreferencesImpl(prefs)
        }
    }
}

inline fun <reified T> SweetPreferences.get(key: String, defaultValue: T? = null): T? {
    return this.get(key, defaultValue, T::class)
}

inline fun <reified T> SweetPreferences.delegate(
        defaultValue: T,
        key: String? = null
): ReadWriteProperty<Any, T> {
    return object : ReadWriteProperty<Any, T> {
        override fun getValue(thisRef: Any, property: KProperty<*>) = this@delegate.get(key
                ?: property.name, defaultValue, T::class)

        override fun setValue(thisRef: Any, property: KProperty<*>, value: T) = this@delegate.set(key
                ?: property.name, value)
    }
}

package org.optima.provider

import org.optima.ConfigServiceHolder
import kotlin.reflect.KProperty

class AutoConfigDelegate<T : Any>(
    private val clazz: Class<T>
) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        val serviceKey = ConfigRegistry.getServiceKey(clazz)
        val service = ConfigServiceHolder.getService(serviceKey)
        return service.getConfig(clazz.toSnakeCase())
    }
}

fun Class<*>.toSnakeCase(): String =
    simpleName
        .replace(Regex("([a-z])([A-Z])"), "$1_$2")
        .lowercase()

class NamedConfigDelegate<T : Any>(
    private val name: String,
    private val clazz: Class<T>
) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        val serviceKey = ConfigRegistry.getServiceKey(clazz)
        val service = ConfigServiceHolder.getService(serviceKey)
        return service.getConfig(name)
    }
}

inline fun <reified T : Any> config() =
    AutoConfigDelegate(T::class.java)

inline fun <reified T : Any> config(name: String) =
    NamedConfigDelegate(name, T::class.java)



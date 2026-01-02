package org.optima.provider

import org.optima.ConfigServiceHolder
import kotlin.reflect.KProperty

class ConfigDelegate<T : Any>(private val clazz: Class<T>) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        val serviceKey = ConfigRegistry.getServiceKey(clazz)
        val service = ConfigServiceHolder.getService(serviceKey)
        val name = clazz.simpleName.replaceFirstChar { it.lowercase() }
        return service.getConfig(name)
    }
}

inline fun <reified T : Any> config() = ConfigDelegate(T::class.java)

package org.optima.provider

object ConfigRegistry {
    private val typeToServiceKey = mutableMapOf<Class<*>, String>()

    fun <T : Any> register(clazz: Class<T>, serviceKey: String) {
        if (typeToServiceKey.containsKey(clazz)) {
            throw IllegalArgumentException("Config ${clazz.simpleName} уже зарегистрирован к сервису ${typeToServiceKey[clazz]}")
        }
        typeToServiceKey[clazz] = serviceKey
    }

    fun removeService(serviceKey: String) {
        val keysToRemove = typeToServiceKey.filterValues { it == serviceKey }.keys
        for (clazz in keysToRemove) {
            typeToServiceKey.remove(clazz)
        }
    }

    fun <T : Any> getServiceKey(clazz: Class<T>): String {
        return typeToServiceKey[clazz] ?: error("Config ${clazz.simpleName} не зарегистрирован ни к одному сервису")
    }
}
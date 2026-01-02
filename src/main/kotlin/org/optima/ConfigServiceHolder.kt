package org.optima

import org.optima.config.ConfigService

object ConfigServiceHolder {

    private val services = mutableMapOf<String, ConfigService>()

    fun registerService(service: ConfigService) {
        val key = service.serviceKey
        if (services.containsKey(key)) throw IllegalArgumentException("Service $key already exists")
        services[key] = service
    }

    fun removeService(service: ConfigService) {
        services.remove(service.serviceKey)
    }

    fun removeService(key: String) {
        services.remove(key)
    }


    fun getService(key: String): ConfigService {
        return services[key] ?: throw IllegalArgumentException("Service $key is not registered")
    }
}
package org.optima

import org.optima.config.ConfigService

object ConfigServiceHolder {

    private val services = mutableMapOf<String, ConfigService>()

    fun registerService(key: String, service: ConfigService) {
        if (services.containsKey(key)) throw IllegalArgumentException("Service $key already exists")
        services[key] = service
    }

    fun getService(key: String): ConfigService {
        return services[key] ?: throw IllegalArgumentException("Service $key is not registered")
    }
}
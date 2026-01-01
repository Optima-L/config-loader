package org.optima

import org.optima.config.ConfigService

object ConfigServiceHolder {
    lateinit var service: ConfigService
        private set

    fun init(service: ConfigService) {
        this.service = service
    }
    val isInitialized: Boolean
        get() = ::service.isInitialized
}
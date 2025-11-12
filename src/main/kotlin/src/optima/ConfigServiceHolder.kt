package src.optima

import ConfigService

object ConfigServiceHolder {
    lateinit var service: ConfigService
        private set

    fun init(service: ConfigService) {
        this.service = service
    }
    val isInitialized: Boolean
        get() = ::service.isInitialized
}
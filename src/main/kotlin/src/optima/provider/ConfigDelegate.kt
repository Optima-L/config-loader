import src.optima.ConfigServiceHolder
import kotlin.reflect.KProperty

class ConfigDelegate<T : Any>(private val name: String) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        check(ConfigServiceHolder.isInitialized) {
            "ConfigService is not initialized! Call ConfigServiceHolder.init() first."
        }
        return ConfigServiceHolder.service.getConfig(name)
    }
}

inline fun <reified T : Any> configRef(name: String) = ConfigDelegate<T>(name)

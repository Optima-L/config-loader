import kotlin.reflect.KProperty
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class ConfigDelegate<T : Any>(
    private val name: String
) : KoinComponent {

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return get<ConfigService>().getConfig(name)
    }
}

inline fun <reified T : Any> configRef(name: String) = ConfigDelegate<T>(name)

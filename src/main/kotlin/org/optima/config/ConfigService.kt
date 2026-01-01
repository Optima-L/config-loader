package org.optima.config

import com.charleskorn.kaml.Yaml
import kotlinx.serialization.KSerializer

interface ConfigService {
    fun <T : Any> register(name: String , serializer: KSerializer<T> , default: T , providedYaml: Yaml? = null)
    fun <T : Any> getConfig(name: String): T
    fun reload(name: String)
    fun reloadAll()
    fun getRegisterNames(): Set<String>
    fun <T : Any> save(name: String, newValue: (T) -> T)
    fun saveAll()
}

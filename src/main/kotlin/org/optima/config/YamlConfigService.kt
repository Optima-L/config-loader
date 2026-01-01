package org.optima.config

import com.charleskorn.kaml.Yaml
import kotlinx.serialization.KSerializer
import java.io.File
import kotlin.collections.iterator

class YamlConfigService(
    private val baseDir: File,
    private val yaml: Yaml
) : ConfigService {

    private data class Entry<T : Any>(
        val serializer: KSerializer<T>,
        val default: T,
        var value: T
    )

    private val registry = mutableMapOf<String, Entry<*>>()

    init {
        if (!baseDir.exists()) baseDir.mkdirs()
    }

    override fun <T : Any> register(name: String , serializer: KSerializer<T> , default: T , providedYaml: Yaml?) {
        val file = configFile(name)
        val finallyYaml = providedYaml ?: yaml
        val value = if (file.exists()) {
            finallyYaml.decodeFromString(serializer, file.readText())
        } else {
            file.writeText(finallyYaml.encodeToString(serializer, default))
            default
        }
        registry[name] = Entry(serializer, default, value)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> getConfig(name: String): T {
        val entry = registry[name] ?: error("No entry for $name")
        return (entry as Entry<T>).value
    }

    override fun reload(name: String) {
        val entry = registry[name] ?: error("Config '$name' is not registered.")
        reloadEntry(entry, configFile(name))
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> save(name: String, newValue: (T) -> T) {
        val entry = registry[name] ?: error("Config '$name' is not registered.")
        val e = entry as Entry<T>
        e.value = newValue(e.value)
        saveEntry(entry, configFile(name))
    }

    override fun saveAll() {
        for ((name, entry) in registry) {
            saveEntry(entry, configFile(name))
        }
    }

    override fun reloadAll() {
        for ((name, entry) in registry) {
            reloadEntry(entry, configFile(name))
        }
    }

    override fun getRegisterNames(): Set<String> = registry.keys

    @Suppress("UNCHECKED_CAST")
    private fun saveEntry(entry: Entry<*>, file: File) {
        val e = entry as Entry<Any>
        file.writeText(yaml.encodeToString(e.serializer, e.value))
    }

    @Suppress("UNCHECKED_CAST")
    private fun reloadEntry(entry: Entry<*>, file: File) {
        val e = entry as Entry<Any>
        e.value = yaml.decodeFromString(e.serializer, file.readText())
    }

    private fun configFile(name: String): File = File(baseDir, "$name.yml")
}

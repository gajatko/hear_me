package yacekbass.ear.clientmodel

import yacekbass.ear.training.ConfigEntry

data class ConfigMapEntry (val confKey: String, val confValue: Any, val confType: String) {
    companion object {
        fun buildConfigMapEntry(key: String, value: ConfigEntry): ConfigMapEntry {
            val typedValue : Any = when (value.type) {
                "boolean" -> value.value == "true"
                "int" -> value.value.toInt()
                else -> value.value
            }
            return ConfigMapEntry(key, typedValue, value.type)
        }

    }
}




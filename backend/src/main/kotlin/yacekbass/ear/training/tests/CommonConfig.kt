package yacekbass.ear.training.tests

import org.jfugue.pattern.Pattern
import yacekbass.ear.training.ConfigEntry


fun Pattern.applyCommonConfig(config: Map<String, ConfigEntry>) : Pattern {
    setTempo((config["tempo"]?.value ?: DEFAULT_TEMPO).toInt())
    setInstrument(config["instrument"]?.value ?: DEFAULT_INSTRUMENT)
    return this
}

const val DEFAULT_TEMPO = "200"

const val DEFAULT_INSTRUMENT = "Piano"

val TEMPO_CONFIG_ENTRY = ConfigEntry(DEFAULT_TEMPO, "int")

val INSTRUMENT_CONFIG_ENTRY = ConfigEntry(DEFAULT_INSTRUMENT, "string")



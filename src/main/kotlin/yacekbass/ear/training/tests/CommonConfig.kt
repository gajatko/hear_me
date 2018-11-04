package yacekbass.ear.training.tests

import org.jfugue.pattern.Pattern



fun Pattern.applyCommonConfig(config: Map<String, String>) : Pattern {
    setTempo((config[TEMPO_CONFIG_ENTRY] ?: DEFAULT_TEMPO).toInt())
    setInstrument(config[INSTRUMENT_CONFIG_ENTRY] ?: DEFAULT_INSTRUMENT)
    return this
}

const val TEMPO_CONFIG_ENTRY = "tempo"
const val DEFAULT_TEMPO = "200"

const val INSTRUMENT_CONFIG_ENTRY = "instrument"
const val DEFAULT_INSTRUMENT = "Piano"

val commonConfig = listOf(TEMPO_CONFIG_ENTRY to DEFAULT_TEMPO, INSTRUMENT_CONFIG_ENTRY to DEFAULT_INSTRUMENT)


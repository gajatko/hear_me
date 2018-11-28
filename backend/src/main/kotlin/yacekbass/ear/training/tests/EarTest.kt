package yacekbass.ear.training.tests

import yacekbass.ear.api.CurrentTestContext
import yacekbass.ear.training.ConfigEntry
import yacekbass.ear.clientmodel.TestQuestion

interface EarTest {
    fun nextQuestion(config: Map<String, ConfigEntry>, context: CurrentTestContext): TestQuestion
    fun defaultConfig(): Map<String, ConfigEntry>
    val name : String

    val commonConfig get() = mapOf("tempo" to TEMPO_CONFIG_ENTRY, "instrument" to INSTRUMENT_CONFIG_ENTRY)
}
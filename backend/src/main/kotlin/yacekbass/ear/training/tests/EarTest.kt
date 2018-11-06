package yacekbass.ear.training.tests

import yacekbass.ear.clientmodel.TestQuestion

interface EarTest {
    fun nextQuestion(config: Map<String, String>): TestQuestion
    fun defaultConfig(): Map<String, String>
    val name : String
}
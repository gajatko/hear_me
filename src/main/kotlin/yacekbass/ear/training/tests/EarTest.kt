package yacekbass.ear.training.tests

interface EarTest {
    fun nextQuestion(config: Map<String, String>): TestQuestion
    fun defaultConfig(): Map<String, String>
    val name : String
}
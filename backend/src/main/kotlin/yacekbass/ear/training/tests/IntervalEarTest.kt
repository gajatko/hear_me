package yacekbass.ear.training.tests

import org.jfugue.pattern.Pattern
import org.jfugue.theory.Intervals
import org.springframework.stereotype.Service
import yacekbass.ear.training.generators.IRandomMusicProvider

@Service
class IntervalEarTest (private val randomMusicProvider : IRandomMusicProvider) : EarTest {
    private val allIntervals = listOf("1", "b2", "2", "b3", "3", "4", "b5", "5",
            "b6", "6", "b7", "7", "8", "b9", "9", "b10", "10")

    override val name: String = "intervals"

    override fun nextQuestion(config: Map<String, String>): TestQuestion {
        val activeOptions = allIntervals.filter { interval -> config[interval] == "true" }
        if (activeOptions.isEmpty()) {
            throw IllegalArgumentException("At least one interval must be active.")
        }

        val intervalString = randomMusicProvider.nextFromList(activeOptions) // eg. "4"
        val rootNote = randomMusicProvider.nextNote() // "C3"
        val interval = Intervals(intervalString).setRoot(rootNote) // "F3"
        val intervalMusicText = rootNote.toString() + "+" + interval.pattern // "C3+F3"
        return TestQuestion(
                possibleAnswers = activeOptions,
                audioPattern = Pattern(intervalMusicText).applyCommonConfig(config).toString(),
                correctAnswer = intervalString
        )
    }

    override fun defaultConfig(): Map<String, String> {
        val config = mutableMapOf(*allIntervals.map { i -> i to "false" }.toTypedArray())
        listOf("1", "b3", "3", "5", "8").forEach {
            intervalActiveByDefault -> config[intervalActiveByDefault] = "true"
        }
        config.putAll(commonConfig)
        return config
    }
}
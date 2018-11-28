package yacekbass.ear.training.tests

import org.jfugue.pattern.Pattern
import org.jfugue.theory.Intervals
import org.springframework.stereotype.Service
import yacekbass.ear.api.Answer
import yacekbass.ear.api.CurrentTestContext
import yacekbass.ear.training.ConfigEntry
import yacekbass.ear.clientmodel.TestQuestion
import yacekbass.ear.training.generators.IRandomMusicProvider
import kotlin.math.ceil

@Service
class IntervalEarTest (private val randomMusicProvider : IRandomMusicProvider) : EarTest {
    private val allIntervals = listOf("1", "b2", "2", "b3", "3", "4", "b5", "5",
            "b6", "6", "b7", "7", "8", "b9", "9", "b10", "10")

    override val name: String = "intervals"

    override fun nextQuestion(config: Map<String, ConfigEntry>, context: CurrentTestContext): TestQuestion {
        val activeOptions = allIntervals.filter { interval -> config[interval]?.value == "true" }
        if (activeOptions.isEmpty()) {
            throw IllegalArgumentException("At least one interval must be active.")
        }
        val distribution: MutableMap<String, Int> = calculateDistribution(context.answerHistory, activeOptions)

        log(context, distribution)

        val intervalString = randomMusicProvider.nextFromList(activeOptions, distribution) // eg. "4"
        val rootNote = randomMusicProvider.nextNote() // "C3"
        val interval = Intervals(intervalString).setRoot(rootNote) // "F3"
        val intervalMusicText = rootNote.toString() + "+" + interval.pattern // "C3+F3"
        return TestQuestion(
                possibleAnswers = activeOptions,
                audioPattern = Pattern(intervalMusicText).applyCommonConfig(config).toString(),
                correctAnswer = intervalString
        )
    }

    private fun calculateDistribution(answerHistory: List<Answer>, activeOptions: List<String>): MutableMap<String, Int> {
        val distributionFromHistory = answerHistory
                .filter { a -> !a.isCorrect }
                .flatMap { a -> listOf(a.correctAnswer to 3, a.userAnswer to 2) }
                .groupBy { (answer, _) -> answer }
                .map { (answer, ones) -> answer to ones.size }
                .toMap()
        val INITIAL_DISTR_VALUE = 2
        val distribution = randomMusicProvider.defaultDistribution(activeOptions)
                .mapValues { INITIAL_DISTR_VALUE + ceil(Math.pow(answerHistory.size.toDouble(), 0.8)).toInt() }
                .toMutableMap()
        distributionFromHistory.forEach { (answer, weight) ->
            distribution[answer] = (distribution[answer] ?: 0) + weight
        }
        return distribution
    }

    private fun log(context: CurrentTestContext, distribution: MutableMap<String, Int>) {
        println()
        println(ceil(Math.pow(context.answerHistory.size.toDouble(), 0.7)).toInt())
        println("Answer history: ${context.answerHistory}")
        println("Distribution: $distribution.")
    }

    override fun defaultConfig(): Map<String, ConfigEntry> {
        val config = mutableMapOf(
                *allIntervals.map { i -> i to ConfigEntry("false", "boolean") }
                        .toTypedArray())
        listOf("1", "b3", "3", "5", "8").forEach {
            intervalActiveByDefault -> config[intervalActiveByDefault]?.value = "true"
        }
        config.putAll(commonConfig)
        return config
    }
}



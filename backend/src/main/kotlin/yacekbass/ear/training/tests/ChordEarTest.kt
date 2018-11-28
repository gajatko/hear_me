package yacekbass.ear.training.tests

import org.jfugue.pattern.Pattern
import org.jfugue.theory.Chord
import org.springframework.stereotype.Service
import yacekbass.ear.api.CurrentTestContext
import yacekbass.ear.training.ConfigEntry
import yacekbass.ear.clientmodel.TestQuestion
import yacekbass.ear.training.generators.IRandomMusicProvider

@Service
class ChordEarTest(private val randomMusicProvider: IRandomMusicProvider) : EarTest {
    private val allChords = Chord.chordMap.keys.map { k -> k!! }

    override fun nextQuestion(config: Map<String, ConfigEntry>, context: CurrentTestContext): TestQuestion {
        val activeOptions = allChords.filter { chord -> config[chord]?.value == "true" }
        if (activeOptions.isEmpty()) {
            throw IllegalArgumentException("At least one chord type must be active.")
        }
        val rootNote = randomMusicProvider.nextNote() // eg. "C3"
        val chordName = randomMusicProvider.nextFromList(activeOptions) // "MAJ"
        val chord = Chord(rootNote, Chord.chordMap[chordName]) // "C3MAJ"
        return TestQuestion(
                possibleAnswers = activeOptions,
                audioPattern = Pattern(chord).applyCommonConfig(config).toString(),
                correctAnswer = chordName
        )
    }

    override fun defaultConfig(): Map<String, ConfigEntry> {
        val config = mutableMapOf(
                *allChords.map { i -> i to ConfigEntry("false", "boolean") }.toTypedArray())
        listOf("MAJ", "MIN", "D7", "AUG", "DIM").forEach {
            chordActiveByDefault -> config[chordActiveByDefault] = ConfigEntry("true", "boolean")
        }
        config.putAll(commonConfig)
        return config
    }

    override val name: String = "chords"
}
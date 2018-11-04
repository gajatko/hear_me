package yacekbass.ear.training.tests

import org.jfugue.pattern.Pattern
import org.jfugue.theory.Chord
import org.springframework.stereotype.Service
import yacekbass.ear.training.generators.IRandomMusicProvider

@Service
class ChordEarTest(private val randomMusicProvider: IRandomMusicProvider) : EarTest {
    private val allChords = Chord.chordMap.keys.map { k -> k!! }

    override fun nextQuestion(config: Map<String, String>): TestQuestion {
        val activeOptions = allChords.filter { chord -> config[chord] == "true" }
        if (activeOptions.isEmpty()) {
            throw IllegalArgumentException("At least one chord type must be active.")
        }
        val rootNote = randomMusicProvider.nextNote() // eg. "C3"
        val chordName = randomMusicProvider.nextFromList(activeOptions) // "MAJ"
        val chord = Chord(rootNote, Chord.chordMap[chordName]) // "C3MAJ"
        return TestQuestion(possibleAnswers = activeOptions,
                audioPattern = Pattern(chord).applyCommonConfig(config).toString(),
                correctAnswer = chordName
        )
    }

    override fun defaultConfig(): Map<String, String> {
        val config = mutableMapOf(*allChords.map { i -> i to "false" }.toTypedArray())
        listOf("MAJ", "MIN", "D7", "AUG", "DIM").forEach {
            chordActiveByDefault -> config[chordActiveByDefault] = "true"
        }
        config.putAll(commonConfig)
        return config
    }

    override val name: String = "chords"
}
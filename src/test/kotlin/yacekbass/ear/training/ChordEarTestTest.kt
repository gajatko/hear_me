package yacekbass.ear.training

import org.jfugue.theory.Note
import org.junit.Assert
import org.junit.Test
import yacekbass.ear.training.generators.IRandomMusicProvider
import yacekbass.ear.training.generators.RandomMusicProvider
import yacekbass.ear.training.tests.ChordEarTest
import yacekbass.ear.training.tests.INSTRUMENT_CONFIG_ENTRY
import yacekbass.ear.training.tests.TEMPO_CONFIG_ENTRY
import yacekbass.ear.training.tests.commonConfig

class ChordEarTestTest {

    @Test
    fun nextQuestionCMaj() {
        val test = ChordEarTest(object : IRandomMusicProvider {
            override fun nextNote() = Note("C4")

            override fun nextFromList(options: List<String>): String = "MAJ"
        })
        val configuration = mapOf(
                "MAJ" to "true",
                "MIN" to "true",
                "AUG" to "false",
                TEMPO_CONFIG_ENTRY to "400",
                INSTRUMENT_CONFIG_ENTRY to "Banjo"
        )
        val q = test.nextQuestion(configuration)
        Assert.assertEquals("T400 I[Banjo] C4MAJ", q.audioPattern)
        Assert.assertEquals("MAJ", q.correctAnswer)
        Assert.assertArrayEquals(arrayOf("MAJ", "MIN"), q.possibleAnswers.toTypedArray())
    }

    @Test
    fun nextQuestionRandomWithSingleChoice() {
        val test = ChordEarTest(RandomMusicProvider())
        val config = mutableMapOf(*commonConfig.toTypedArray())
        config["MIN"] = "true"
        val q = test.nextQuestion(config)
        println("${q.correctAnswer} ${q.audioPattern} ${q.possibleAnswers}")
        Assert.assertEquals("MIN", q.correctAnswer)
    }
}
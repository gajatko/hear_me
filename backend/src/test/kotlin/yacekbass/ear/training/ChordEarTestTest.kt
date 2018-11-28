package yacekbass.ear.training

import org.jfugue.theory.Note
import org.junit.Assert
import org.junit.Test
import yacekbass.ear.api.CurrentTestContext
import yacekbass.ear.clientmodel.TestQuestion
import yacekbass.ear.training.generators.IRandomMusicProvider
import yacekbass.ear.training.generators.RandomMusicProvider
import yacekbass.ear.training.tests.ChordEarTest
import yacekbass.ear.training.tests.EarTest

class ChordEarTestTest {

    @Test
    fun nextQuestionCMaj() {
        val test = ChordEarTest(object : IRandomMusicProvider {
            override fun nextNote() = Note("C4")

            override fun nextFromList(options: List<String>, distribution: Map<String, Int>): String = "MAJ"
        })
        val configuration = mapOf(
                "MAJ" to ConfigEntry("true", "boolean"),
                "MIN" to ConfigEntry("true", "boolean"),
                "AUG" to ConfigEntry("false", "boolean"),
                "tempo" to ConfigEntry("400", "int"),
                "instrument" to ConfigEntry("Banjo", "string")
        )
        val q = test.nextQuestion(configuration, CurrentTestContext(mapOf(), ""))
        Assert.assertEquals("T400 I[Banjo] C4MAJ", q.audioPattern)
        Assert.assertEquals("MAJ", q.correctAnswer)
        Assert.assertArrayEquals(arrayOf("MAJ", "MIN"), q.possibleAnswers.toTypedArray())
    }

    private fun getConf(): MutableMap<String, ConfigEntry> {
        val x = object : EarTest{
            override fun nextQuestion(config: Map<String, ConfigEntry>, context: CurrentTestContext): TestQuestion =
                    TODO("not implemented")

            override fun defaultConfig(): Map<String, ConfigEntry> =
                    TODO("not implemented")

            override val name: String
                get() = TODO("not implemented")

        }
        return x.commonConfig.toMutableMap()
    }

    @Test
    fun nextQuestionRandomWithSingleChoice() {
        val test = ChordEarTest(RandomMusicProvider())
        val config = getConf()
        config["MIN"] = ConfigEntry("true", "boolean")
        val q = test.nextQuestion(config, CurrentTestContext(mapOf(), ""))
        println("${q.correctAnswer} ${q.audioPattern} ${q.possibleAnswers}")
        Assert.assertEquals("MIN", q.correctAnswer)
    }
}
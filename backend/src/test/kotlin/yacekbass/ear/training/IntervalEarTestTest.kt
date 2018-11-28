package yacekbass.ear.training

import org.jfugue.theory.Note
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import yacekbass.ear.api.CurrentTestContext
import yacekbass.ear.clientmodel.TestQuestion
import yacekbass.ear.training.generators.IRandomMusicProvider
import yacekbass.ear.training.tests.EarTest
import yacekbass.ear.training.tests.IntervalEarTest

class IntervalEarTestTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun nextQuestionTritone() {
        val test = IntervalEarTest(TestRandom("F4", "b5"))
        val configuration = mutableMapOf(
                "3" to ConfigEntry("true", "boolean"),
                "b5" to ConfigEntry("true", "boolean"),
                "tempo" to ConfigEntry("500", "int"),
                "instrument" to ConfigEntry("Guitar", "String")
        )
        val q = test.nextQuestion(configuration, CurrentTestContext(mapOf(), ""))
        Assert.assertEquals("b5", q.correctAnswer)
        Assert.assertEquals("T500 I[Guitar] F4+B4", q.audioPattern)
        Assert.assertArrayEquals(arrayOf("3", "b5"), q.possibleAnswers.toTypedArray())
    }

    @Test
    fun nextQuestionZeroConf() {
        val test = IntervalEarTest(TestRandom("C4", "4"))
        val configuration = getConf()
        try {
            test.nextQuestion(configuration, CurrentTestContext(mapOf(), ""))
            Assert.fail()
        } catch (e : IllegalArgumentException) {
        }
    }
    class TestRandom(private val note: String, private val interval:String) : IRandomMusicProvider {
        override fun nextNote(): Note = Note(note)

        override fun nextFromList(options: List<String>, distribution: Map<String, Int>): String = interval
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

}
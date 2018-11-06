package yacekbass.ear.training

import org.jfugue.theory.Note
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import yacekbass.ear.training.generators.IRandomMusicProvider
import yacekbass.ear.training.tests.INSTRUMENT_CONFIG_ENTRY
import yacekbass.ear.training.tests.IntervalEarTest
import yacekbass.ear.training.tests.TEMPO_CONFIG_ENTRY
import yacekbass.ear.training.tests.commonConfig

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
        val configuration = mapOf(
                "3" to "true",
                "b5" to "true",
                TEMPO_CONFIG_ENTRY to "500",
                INSTRUMENT_CONFIG_ENTRY to "Guitar"
        )
        val q = test.nextQuestion(configuration)
        Assert.assertEquals("b5", q.correctAnswer)
        Assert.assertEquals("T500 I[Guitar] F4+B4", q.audioPattern)
        Assert.assertArrayEquals(arrayOf("3", "b5"), q.possibleAnswers.toTypedArray())
    }

    @Test
    fun nextQuestionZeroConf() {
        val test = IntervalEarTest(TestRandom("C4", "4"))
        val configuration = mapOf(*commonConfig.toTypedArray())
        try {
            test.nextQuestion(configuration)
            Assert.fail()
        } catch (e : IllegalArgumentException) {
        }
    }
    class TestRandom(private val note: String, private val interval:String) : IRandomMusicProvider {
        override fun nextNote(): Note = Note(note)

        override fun nextFromList(options: List<String>): String = interval
    }
}
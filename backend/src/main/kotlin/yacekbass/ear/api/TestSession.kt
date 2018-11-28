package yacekbass.ear.api

import yacekbass.ear.clientmodel.TestQuestion
import yacekbass.ear.training.ConfigEntry
import java.io.Serializable

open class TestSession : Serializable {

    var testContext : CurrentTestContext? = null
    var currentQuestion : TestQuestion? = null

    fun wasQuestionAsked() = testContext != null && currentQuestion != null


    companion object {
        private const val serialVersionUID: Long = 1
    }
}

class CurrentTestContext(val currentConfig: Map<String, ConfigEntry>, val currentTest: String) {
    var answerHistory: List<Answer> = listOf()
}


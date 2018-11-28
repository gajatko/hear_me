package yacekbass.ear.clientmodel

import java.io.Serializable

class TestQuestion(val possibleAnswers: List<String>, val audioPattern: String, val correctAnswer: String)
    : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1
    }
}

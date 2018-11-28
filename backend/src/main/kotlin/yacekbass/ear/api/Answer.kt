package yacekbass.ear.api

class Answer(val userAnswer: String, val correctAnswer: String) {
    val isCorrect = userAnswer == correctAnswer

    override fun toString(): String {
        return "($userAnswer, $correctAnswer, $isCorrect)"
    }
}

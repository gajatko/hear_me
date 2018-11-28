package yacekbass.ear.api

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import yacekbass.ear.audio.MidiRenderer
import yacekbass.ear.clientmodel.ConfigMapEntry
import yacekbass.ear.training.ConfigEntry
import yacekbass.ear.training.tests.EarTest
import yacekbass.ear.clientmodel.TestQuestion
import java.lang.IllegalStateException
import javax.servlet.http.HttpServletResponse

@RestController
@CrossOrigin
@RequestMapping("/ear")
class EarTrainingController constructor (
        val tests : Array<EarTest>,
        val midi2Wav: MidiRenderer,
        var session: TestSession){

    @GetMapping("/what_is_the_best_company")
    fun whatIsTheBestCompany(): ResponseEntity<String> {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body("""{ "x": "My Own."""")

    }
    @GetMapping("/hello")
    fun hello() = TestQuestion(
            possibleAnswers = listOf("a", "b", "c"),
            audioPattern = "afda",
            correctAnswer = "far"
    )
    @GetMapping("/answer/{answer}")
    fun answer(@PathVariable("answer") answer: String): Boolean {
        if (!session.wasQuestionAsked()) {
            throw IllegalStateException("No test in session")
        }
        val context = session.testContext!!
        val correctAnswer = session.currentQuestion!!.correctAnswer
        val isCorrect = answer == correctAnswer
        context.answerHistory += Answer(answer, correctAnswer)
        return isCorrect
    }

    @PostMapping("/test/{test_name}")
    fun test(@PathVariable("test_name") testName: String, @RequestBody config: List<ConfigMapEntry>):
            TestQuestion {
        val test = tests.single { t -> t.name == testName }
        val conf = config.map { entry -> entry.confKey to ConfigEntry(entry.confValue.toString(), entry.confType) }.toMap()
        if (!session.wasQuestionAsked() || session.testContext?.currentTest != testName) {
            session.testContext = CurrentTestContext(conf, testName)
        }
        val question = test.nextQuestion(conf, session.testContext!!)
        session.currentQuestion = question
        return question
    }

    @GetMapping("/restart")
    fun restart() {
        session.testContext?.answerHistory = listOf()
    }


    @GetMapping("/get_config/{test_name}")
    fun getConfig(@PathVariable("test_name") testName : String): List<ConfigMapEntry> {
        val test = tests.single { t -> t.name == testName }
        return test.defaultConfig().entries
                .map { entry -> ConfigMapEntry.buildConfigMapEntry(entry.key, entry.value) }
    }

    @PostMapping("/audio")
    fun audio(@RequestBody audioPattern: String, response: HttpServletResponse) {
        response.setHeader("Content-Disposition", "attachment; filename=\"eartest.wav\"")
        midi2Wav.render(audioPattern, response.outputStream)
    }
}
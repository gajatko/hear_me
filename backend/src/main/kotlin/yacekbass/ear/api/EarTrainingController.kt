package yacekbass.ear.api

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import yacekbass.ear.audio.MidiRenderer
import yacekbass.ear.clientmodel.ConfigMapEntry
import yacekbass.ear.training.ConfigEntry
import yacekbass.ear.training.tests.EarTest
import yacekbass.ear.clientmodel.TestQuestion
import javax.servlet.http.HttpServletResponse

@RestController
@CrossOrigin
@RequestMapping("/ear")
class EarTrainingController constructor (val tests : Array<EarTest>, val midi2Wav: MidiRenderer){

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

    @PostMapping("/test/{test_name}")
    fun test(@PathVariable("test_name") testName: String, @RequestBody config: List<ConfigMapEntry>):
            TestQuestion {
        val test = tests.single { t -> t.name == testName }
        val conf = config.map { entry -> entry.confKey to ConfigEntry(entry.confValue, entry.confType) }.toMap()
        return test.nextQuestion(conf)
    }

    @GetMapping("/get_config/{test_name}")
    fun getConfig(@PathVariable("test_name") testName : String): List<ConfigMapEntry> {
        val test = tests.single { t -> t.name == testName }
        return test.defaultConfig().entries
                .map { entry -> ConfigMapEntry(entry.key, entry.value.value, entry.value.type) }
    }

    @PostMapping("/audio")
    fun audio(@RequestBody audioPattern: String, response: HttpServletResponse) {
        response.setHeader("Content-Disposition", "attachment; filename=\"eartest.wav\"")
        midi2Wav.render(audioPattern, response.outputStream)
    }
}
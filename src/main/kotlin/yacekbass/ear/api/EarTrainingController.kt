package yacekbass.ear.api

import org.springframework.web.bind.annotation.*
import yacekbass.ear.audio.MidiRenderer
import yacekbass.ear.training.tests.EarTest
import yacekbass.ear.training.tests.TestQuestion
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/ear")
class EarTrainingController constructor (val tests : Array<EarTest>, val midi2Wav: MidiRenderer){

    @GetMapping("/what_is_the_best_company")
    fun whatIsTheBestCompany() = "Networked Assets"

    @PostMapping("/test/{test_name}")
    fun test(@PathVariable("test_name") testName: String, @RequestBody config: Map<String, String>): TestQuestion {
        val test = tests.single { t -> t.name == testName }
        return test.nextQuestion(config)
    }

    @GetMapping("/get_config/{test_name}")
    fun getConfig(@PathVariable("test_name") testName : String): Map<String, String> {
        val test = tests.single { t -> t.name == testName }
        return test.defaultConfig()
    }

    @PostMapping("/audio")
    fun audio(@RequestBody audioPattern: String, response: HttpServletResponse) {
        response.setHeader("Content-Disposition", "attachment; filename=\"eartest.wav\"")
        midi2Wav.render(audioPattern, response.outputStream)
    }
}
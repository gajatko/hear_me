package yacekbass.ear

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EarApplication

fun main(args: Array<String>) {
    runApplication<EarApplication>(*args)
}

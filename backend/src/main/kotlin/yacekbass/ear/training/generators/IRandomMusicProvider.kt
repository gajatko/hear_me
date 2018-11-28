package yacekbass.ear.training.generators

import org.jfugue.theory.Note

interface IRandomMusicProvider {
    fun nextNote(): Note
    fun nextFromList(options: List<String>, distribution: Map<String, Int>): String

    fun nextFromList(options: List<String>): String {
        val uniformDistr = defaultDistribution(options)
        return nextFromList(options, uniformDistr)
    }

    fun defaultDistribution(options: List<String>) =
            options.map { i -> Pair(i, 1) }.toMap()
}
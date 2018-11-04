package yacekbass.ear.training.generators

import org.jfugue.theory.Note

interface IRandomMusicProvider {
    fun nextNote(): Note
    fun nextFromList(options : List<String>): String
}
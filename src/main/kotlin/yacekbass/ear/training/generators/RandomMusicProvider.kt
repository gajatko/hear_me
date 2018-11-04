package yacekbass.ear.training.generators

import org.jfugue.theory.Note
import org.jfugue.theory.Note.NOTE_NAMES_COMMON
import org.springframework.stereotype.Service
import java.util.*

@Service
class RandomMusicProvider : IRandomMusicProvider {

    val random = Random()

    companion object {
        private const val OCTAVE_LOWER_BOUND = 3
        private const val OCTAVE_RANGE = 3
    }

    override fun nextNote(): Note {
        val octave = OCTAVE_LOWER_BOUND + random.nextInt(OCTAVE_RANGE)
        val noteLetter = NOTE_NAMES_COMMON[random.nextInt(NOTE_NAMES_COMMON.size)] + octave
        return Note(noteLetter)
    }

    override fun nextFromList(options : List<String>): String {
        if (options.isEmpty()) throw IllegalArgumentException("option list must not be empty")
        return options[random.nextInt(options.size)]
    }
}
package yacekbass.ear.audio

import org.jfugue.player.Player
import org.springframework.stereotype.Service
import java.io.OutputStream


@Service
class MidiRenderer(val renderer: Midi2WaveRenderer) {
    fun render(pattern : String, outputStream: OutputStream) {
        val player = Player()
        val seq = player.getSequence(pattern)
        renderer.createWavAndSendToOutputStream(seq, outputStream)
    }
}
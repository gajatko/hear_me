package yacekbass.ear.audio;

import com.sun.media.sound.AudioSynthesizer;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.sound.midi.*;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/*
 * JFugue - API for Music Programming
 * Copyright (C) 2003-2008  Karl Helgason and David Koelle
 *
 * http://www.jfugue.org
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 */

@Service
public class Midi2WaveRenderer {
    private AudioSynthesizer synth;


    public Midi2WaveRenderer() throws MidiUnavailableException {
        this.synth = findAudioSynthesizer(); // MidiSystem.getSynthesizer();
    }

    /**
     * Creates a WAV file based on the Sequence, using the default soundbank.
     */
    public void createWavFile(Sequence sequence, File outputWavFile) throws MidiUnavailableException, IOException {
        AudioInputStream stream = getAudioInputStream(sequence);

        // Write WAVE file to disk.
        AudioSystem.write(stream, AudioFileFormat.Type.WAVE, outputWavFile);
        this.synth.close();
    }


    @NotNull
    private AudioInputStream getAudioInputStream(Sequence sequence) throws MidiUnavailableException {
        //AudioFormat format = new AudioFormat(96000, 24, 2, true, false);
        //  AudioFormat format = new AudioFormat(22050, 16, 1, true, false);  // das
        AudioFormat format = new AudioFormat(44100, 16, 1, true, false);  // worked!
        Map<String, Object> p = new HashMap<>();
        p.put("interpolation", "sinc");
        p.put("max polyphony", "1024");
        synth.close();
        AudioInputStream stream = synth.openStream(format, p);

        // Play Sequence into AudioSynthesizer Receiver.
        double total = send(sequence, synth.getReceiver());

        // Calculate how long the WAVE file needs to be.
        long len = (long) (stream.getFormat().getFrameRate() * (total + 4));
        stream = new AudioInputStream(stream, stream.getFormat(), len);
        return stream;
    }

    public void createWavAndSendToOutputStream(Sequence sequence, OutputStream outputStream) throws MidiUnavailableException, IOException {
        AudioInputStream stream = getAudioInputStream(sequence);

        AudioSystem.write(stream, AudioFileFormat.Type.WAVE, outputStream);
        this.synth.close();
    }


    /**
     * Find available AudioSynthesizer.
     */
    private AudioSynthesizer findAudioSynthesizer() throws MidiUnavailableException {
        // First check if default synthesizer is AudioSynthesizer.
        Synthesizer synth = MidiSystem.getSynthesizer();
        if (synth instanceof AudioSynthesizer) {
            return (AudioSynthesizer) synth;
        }

        // If default synthesizer is not AudioSynthesizer, check others.
        MidiDevice.Info[] midiDeviceInfo = MidiSystem.getMidiDeviceInfo();
        for (MidiDevice.Info aMidiDeviceInfo : midiDeviceInfo) {
            MidiDevice dev = MidiSystem.getMidiDevice(aMidiDeviceInfo);
            if (dev instanceof AudioSynthesizer) {
                return (AudioSynthesizer) dev;
            }
        }
        // No AudioSynthesizer was found, return null.
        return null;
    }

    /**
     * Send entry MIDI Sequence into Receiver using timestamps.
     */
    private double send(Sequence seq, Receiver recv) {
        float divtype = seq.getDivisionType();
        assert (seq.getDivisionType() == Sequence.PPQ);
        Track[] tracks = seq.getTracks();
        int[] trackspos = new int[tracks.length];
        int mpq = 500000;
        int seqres = seq.getResolution();
        long lasttick = 0;
        long curtime = 0;
        while (true) {
            MidiEvent selevent = null;
            int seltrack = -1;
            for (int i = 0; i < tracks.length; i++) {
                int trackpos = trackspos[i];
                Track track = tracks[i];
                if (trackpos < track.size()) {
                    MidiEvent event = track.get(trackpos);
                    if (selevent == null
                            || event.getTick() < selevent.getTick()) {
                        selevent = event;
                        seltrack = i;
                    }
                }
            }
            if (seltrack == -1)
                break;
            trackspos[seltrack]++;
            long tick = selevent.getTick();
            if (divtype == Sequence.PPQ)
                curtime += ((tick - lasttick) * mpq) / seqres;
            else
                curtime = (long) ((tick * 1000000.0 * divtype) / seqres);
            lasttick = tick;
            MidiMessage msg = selevent.getMessage();
            if (msg instanceof MetaMessage) {
                if (divtype == Sequence.PPQ)
                    if (((MetaMessage) msg).getType() == 0x51) {
                        byte[] data = ((MetaMessage) msg).getData();
                        mpq = ((data[0] & 0xff) << 16)
                                | ((data[1] & 0xff) << 8) | (data[2] & 0xff);
                    }
            } else {
                if (recv != null)
                    recv.send(msg, curtime);
            }
        }
        return curtime / 1000000.0;
    }

}

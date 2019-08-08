package midi;

import javax.sound.midi.MidiMessage;

/**
 *
 * @author groowy
 */

public class DefaultMidiMessage extends MidiMessage {

    public DefaultMidiMessage(byte[] data) {
        super(data);    
    }
    
    @Override
    public Object clone() {
        return new DefaultMidiMessage(super.data);
    }
}
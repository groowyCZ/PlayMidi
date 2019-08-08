package midi;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import main.MainWindow;

/**
 *
 * @author groowy
 */
public class MidiRecordingReceiver implements Receiver {

    private long pTime;
    
    @Override
    public void send(MidiMessage message, long timeStamp) {
        byte[] msg = message.getMessage();
        if(msg.length == 3) {
            if(MainWindow.midiRecording.isEmpty()){
                this.pTime = System.currentTimeMillis();
            }
            MainWindow.midiRecording.add(msg[0] + "," + msg[1] + "," + msg[2]
                    + "," + (System.currentTimeMillis() - this.pTime));
            this.pTime = System.currentTimeMillis();
        }
    }

    @Override
    public void close() {
        this.close();
    }
    
}

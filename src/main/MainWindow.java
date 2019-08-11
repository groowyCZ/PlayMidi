package main;


import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import utils.Utils;
import midi.MidiHandler;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import midi.DefaultMidiMessage;
import midi.MidiRecordingReceiver;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author groowy
 */
public class MainWindow extends javax.swing.JFrame {
    private ArrayList<MidiDevice> inputDevices;
    private ArrayList<MidiDevice> outputDevices;
    private MidiDevice inputDevice;
    private MidiDevice outputDevice;
    public  Thread player;
    public static ArrayList<String> midiRecording;
    
    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        initComponents();
        refreshDevices();
        this.outputStopButton.setEnabled(false);
        midiRecording = new ArrayList();
    }
    
    private void refreshInputDevices() throws MidiUnavailableException {
        this.inputDevices = virtualDevicesCheckBox.isSelected() ?
                MidiHandler.getInputDevices() : MidiHandler.getInputHardwareDevices();
        DefaultComboBoxModel<String> inputChooserModel = new DefaultComboBoxModel<>();
        for(MidiDevice dev : inputDevices) {
            inputChooserModel.addElement(dev.getDeviceInfo().getName());
        }
        this.inputChooser.setModel(inputChooserModel);
    }
    
    private void refreshOutputDevices() throws MidiUnavailableException {
        this.outputDevices = virtualDevicesCheckBox.isSelected() ?
                MidiHandler.getOutputDevices() : MidiHandler.getOutputHardwareDevices();
        DefaultComboBoxModel<String> outputChooserModel = new DefaultComboBoxModel<>();
        for(MidiDevice dev : outputDevices) {
            outputChooserModel.addElement(dev.getDeviceInfo().getName());
        }
        this.outputChooser.setModel(outputChooserModel);
    }
    
    private void refreshDevices() {
        try {
            this.refreshInputDevices();
            this.refreshOutputDevices();
        } catch(Exception e) {
            Utils.showError("Couldn't refresh MIDI devices", this);
        }
    }

    
    class MidiPlayer implements Runnable {

        private Receiver piano;

        public MidiPlayer(Receiver piano) {
            this.piano = piano;
        }

        @Override
        public void run() {
            for(String message : MainWindow.midiRecording) {
                String[] msgData = message.split(",");
                byte[] data = new byte[msgData.length - 1];
                for (int i = 0; i < msgData.length - 1 /*last value is time*/; i++) {
                    data[i] = Byte.valueOf(msgData[i]);
                }
                try {
                    Thread.sleep(Long.valueOf(msgData[msgData.length - 1]));
                } catch (InterruptedException ex) {
                    // it's fine because this was most likely caused by stop() ;)
                    outputDevice.close();
                    break;
                }
                piano.send(new DefaultMidiMessage(data), 0);
            }
            enableButtons();
            outputStopButton.setEnabled(false);
            outputDevice.close();
            // this thread should be garbage collected after the for loop
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        recordButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        inputChooser = new javax.swing.JComboBox<>();
        playButton = new javax.swing.JButton();
        outputChooser = new javax.swing.JComboBox<>();
        virtualDevicesCheckBox = new javax.swing.JCheckBox();
        refreshButton = new javax.swing.JButton();
        inputStopButton = new javax.swing.JButton();
        outputStopButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        loadButton = new javax.swing.JButton();
        saveMidiButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        recordButton.setText("Record");
        recordButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recordButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Midi input devices");

        jLabel2.setText("Midi output devices");

        inputChooser.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        playButton.setText("Play");
        playButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playButtonActionPerformed(evt);
            }
        });

        outputChooser.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        virtualDevicesCheckBox.setText("Show virtual midi devices");

        refreshButton.setText("Refresh device list");
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        inputStopButton.setText("Stop");
        inputStopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputStopButtonActionPerformed(evt);
            }
        });

        outputStopButton.setText("Stop");
        outputStopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                outputStopButtonActionPerformed(evt);
            }
        });

        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        loadButton.setText("Load");
        loadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadButtonActionPerformed(evt);
            }
        });

        saveMidiButton.setText("Save as midi");
        saveMidiButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveMidiButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(recordButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inputStopButton))
                            .addComponent(inputChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(playButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(outputStopButton))
                            .addComponent(jLabel2)
                            .addComponent(outputChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(virtualDevicesCheckBox)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(refreshButton)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(saveMidiButton)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(saveButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(loadButton)))))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inputChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(outputChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(playButton)
                        .addComponent(outputStopButton))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(recordButton)
                        .addComponent(inputStopButton)))
                .addGap(18, 18, 18)
                .addComponent(virtualDevicesCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(refreshButton)
                    .addComponent(saveButton)
                    .addComponent(loadButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(saveMidiButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        this.refreshDevices();
    }//GEN-LAST:event_refreshButtonActionPerformed
    
    private String openFileDialog() {
        JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("MidiPlay files", "mpf"));
        int r = fileChooser.showOpenDialog(this);
        if(r == 0) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        }
        return null;
    }                                             
    
    private String saveFileDialog(String ext, String extDescription) {
        JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
        fileChooser.setFileFilter(new FileNameExtensionFilter(extDescription, ext));
        fileChooser.setSelectedFile(new File("unnamed." + ext));
        int r = fileChooser.showSaveDialog(this);
        if(r == 0) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        }
        return null;
    }
    
    private void loadRecording(String path) {
        if(path == null) return;
        try {
            midiRecording = (ArrayList<String>) Files.readAllLines(
                    Paths.get(path),
                    Charset.forName("UTF-8")
            );
        } catch (IOException ex) {
            Utils.showError("Error when loading " + path, this);
        }
    }
    
    private void saveRecording(String path) {
        if(path == null) return;
        try {
            Files.write(Paths.get(path), midiRecording, Charset.forName("UTF-8"));
        } catch (IOException ex) {
            Utils.showError("Error when saving file to " + path, this);
        }
    }

    private void recordButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recordButtonActionPerformed
        this.inputDevice = inputDevices.get(inputChooser.getSelectedIndex());
        try {
            this.inputDevice.open();
        } catch (MidiUnavailableException ex) {
            Utils.showError("Couldn't open recording device", this);
            return;
        }
        try {
            this.inputDevice.getTransmitter().setReceiver(new MidiRecordingReceiver());
        } catch (MidiUnavailableException ex) {
            Utils.showError("Could assign receiver of recording device", this);
            return;
        }
        midiRecording = new ArrayList();
        recordButton.setEnabled(false);
        playButton.setEnabled(false);
        inputStopButton.setEnabled(true);
    }//GEN-LAST:event_recordButtonActionPerformed

    private void inputStopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputStopButtonActionPerformed
        this.inputDevice.close();
        this.enableButtons();
        inputStopButton.setEnabled(true);
    }//GEN-LAST:event_inputStopButtonActionPerformed

    private void playButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playButtonActionPerformed
        if(midiRecording.isEmpty()) return;
        this.outputDevice = outputDevices.get(outputChooser.getSelectedIndex());
        try {
            this.outputDevice.open();
        } catch (MidiUnavailableException ex) {
            Utils.showError("Couldn't open playback device", this);
            return;
        }
        try {
            this.player = new Thread(new MidiPlayer(this.outputDevice.getReceiver()));
            this.player.start();
        } catch (MidiUnavailableException ex) {
            Utils.showError("Could get receiver of playback device", this);
            return;
        }
        this.recordButton.setEnabled(false);
        this.playButton.setEnabled(false);
        this.outputStopButton.setEnabled(true);
    }//GEN-LAST:event_playButtonActionPerformed

    private void outputStopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_outputStopButtonActionPerformed
        this.player.interrupt();
        this.enableButtons();
        this.outputStopButton.setEnabled(false);
        this.outputDevice.close();
    }//GEN-LAST:event_outputStopButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        saveRecording(saveFileDialog("mpf", "MidiPlay file"));
    }//GEN-LAST:event_saveButtonActionPerformed

    private void loadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadButtonActionPerformed
        loadRecording(openFileDialog());
    }//GEN-LAST:event_loadButtonActionPerformed

    private void saveMidiButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveMidiButtonActionPerformed
        if(midiRecording.isEmpty()) return;
        
        String path = saveFileDialog("mid", "midi file");
        if(path == null) return;
        try {
            MidiHandler.saveRecordingAsMidiFile(path);
        } catch (IOException e) {
            Utils.showError("Error when saving file to " + path, this);
        } catch (InvalidMidiDataException ex) {
            Utils.showError("Couldn't create midi sequence", this);
        }
    }//GEN-LAST:event_saveMidiButtonActionPerformed
    
    public void enableButtons() {
        this.recordButton.setEnabled(true);
        this.playButton.setEnabled(true);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> inputChooser;
    private javax.swing.JButton inputStopButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton loadButton;
    private javax.swing.JComboBox<String> outputChooser;
    private javax.swing.JButton outputStopButton;
    private javax.swing.JButton playButton;
    private javax.swing.JButton recordButton;
    private javax.swing.JButton refreshButton;
    private javax.swing.JButton saveButton;
    private javax.swing.JButton saveMidiButton;
    private javax.swing.JCheckBox virtualDevicesCheckBox;
    // End of variables declaration//GEN-END:variables
}

package main.Gui;//imports de bibliotecas.
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.sound.midi.InvalidMidiDataException;
import javax.swing.*;

import main.PatternConversion.*;
import org.jfugue.midi.MidiFileManager;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

public class Gui implements ActionListener
{
    private final int LENGTH = 500;
    private final int WIDTH  = 500;
    private final Color MINT_GREEN = new Color(0,255,128);

    private JPanel backgroundPanel;
    private JPanel importExportPanel;
    private JPanel instrumentsPanel;
    private JPanel rythmScalePanel;
    private JPanel playPanel;
    private JPanel invisiblePanel;
    private JPanel invisiblePanel2;
    private JPanel invisiblePanel3;
    private JPanel invisiblePanel4;

    private JButton buttonImportTxt = new JButton("Open Txt");
    private JButton buttonExportTxt = new JButton("Save Txt");
    private JButton buttonExportMid = new JButton("Save Mid");
    private JButton button_play   = new JButton("Play");
    private JButton buttonImportAndPlayMid   = new JButton("Play Mid");

    private JTextArea textArea = new JTextArea();
    private JScrollPane scrollableTextArea = new JScrollPane(textArea);

    public Gui()
    {
        this.backgroundPanel = new JPanel();
        this.importExportPanel = new JPanel();
        this.instrumentsPanel = new JPanel();
        this.rythmScalePanel = new JPanel();
        this.playPanel = new JPanel();
        this.invisiblePanel = new JPanel();
        this.invisiblePanel2 = new JPanel();
        this.invisiblePanel3 = new JPanel();
        this.invisiblePanel4 = new JPanel();

        setInvisiblePanels();
        setBackgroundPanel();
        createButtons();
        //Create text input area
        scrollableTextArea.setBounds(WIDTH/10, 2*(LENGTH /10), WIDTH-(2*(WIDTH/10)), LENGTH /3);

        createFrame();
    }

    private void createFrame() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(importExportPanel);
        frame.add(instrumentsPanel);
        frame.add(scrollableTextArea);
        frame.add(rythmScalePanel);
        frame.add(playPanel);
        frame.add(backgroundPanel);
        frame.setTitle("GUI");
        frame.setSize(WIDTH, LENGTH);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    private void createButtons() {
        setButtonsLayout();

        buttonImportTxt.addActionListener(this);
        buttonExportTxt.addActionListener(this);
        button_play.addActionListener(this);
        buttonExportMid.addActionListener(this);
        buttonImportAndPlayMid.addActionListener(this);
    }

    private void setButtonsLayout() {
        importExportPanel.setLayout(new BoxLayout(importExportPanel, 0));
        importExportPanel.setBounds(WIDTH/10, LENGTH /10,  WIDTH-(2*(WIDTH/10)), LENGTH /10);
        importExportPanel.setBackground(MINT_GREEN);

        instrumentsPanel.setLayout(new BoxLayout(instrumentsPanel, 0));
        instrumentsPanel.setBounds(WIDTH/10, 2*(LENGTH /10)+(LENGTH /3),  WIDTH-(2*(WIDTH/10)), LENGTH /10);
        instrumentsPanel.setBackground(MINT_GREEN);

        rythmScalePanel.setLayout(new BoxLayout(rythmScalePanel, 0));
        rythmScalePanel.setBounds(WIDTH/10, 6* LENGTH /10,  WIDTH-WIDTH/10, LENGTH /10);
        rythmScalePanel.setBackground(MINT_GREEN);

        playPanel.setLayout(new BoxLayout(playPanel, 0));
        playPanel.setBounds((WIDTH/2)-WIDTH/20, 7* LENGTH /10,  WIDTH-WIDTH/10, LENGTH /10);
        playPanel.setBackground(MINT_GREEN);

        importExportPanel.add(buttonImportTxt);
        importExportPanel.add(invisiblePanel3);
        importExportPanel.add(buttonExportTxt);
        importExportPanel.add(invisiblePanel4);
        importExportPanel.add(buttonExportMid);
        importExportPanel.add(buttonImportAndPlayMid);
        playPanel.add(button_play);
    }

    private void setBackgroundPanel() {
        backgroundPanel.setBackground(MINT_GREEN);
        backgroundPanel.setBounds(0,0, WIDTH, LENGTH);
    }

    private void setInvisiblePanels() {
        //Invisible Panels are used to space out buttons
        invisiblePanel.setBackground(MINT_GREEN);
        invisiblePanel2.setBackground(MINT_GREEN);
        invisiblePanel3.setBackground(MINT_GREEN);
        invisiblePanel4.setBackground(MINT_GREEN);
    }

    @Override
    public void actionPerformed(ActionEvent selectedButton){
        if(selectedButton.getSource()== buttonImportTxt){
            importTxt();
        }

        if(selectedButton.getSource()== buttonExportTxt){
            exportTxt();
        }

        if(selectedButton.getSource()==button_play){
            playMusic();
        }

        if(selectedButton.getSource()== buttonExportMid){
            exportMid();
        }

        if(selectedButton.getSource()==buttonImportAndPlayMid){
            importAndPlayMid();
        }
    }

    private void importAndPlayMid(){
        JFileChooser chooseArq = new JFileChooser();
        int response = chooseArq.showOpenDialog(null);
        if(response == JFileChooser.APPROVE_OPTION) {
            Path filePath = Path.of(chooseArq.getSelectedFile().getAbsolutePath());
            try {
                Pattern pattern = MidiFileManager.loadPatternFromMidi(new File(filePath.toUri()));
                Player player = new Player();
                player.play(pattern);
            } catch (IOException | InvalidMidiDataException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void exportMid() {
        JFileChooser chooseArq = new JFileChooser();
        int response = chooseArq.showOpenDialog(null);
        if(response == JFileChooser.APPROVE_OPTION) {
            Path filePath = Path.of(chooseArq.getSelectedFile().getAbsolutePath());
            String inputText;
            inputText = textArea.getText();
            Pattern pattern = StringToPattern.convert(inputText);

            try {
                MidiFileManager.savePatternToMidi(pattern, new File(filePath.toUri()));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void playMusic() {
        String inputText;
        inputText = textArea.getText();
        Pattern pattern = StringToPattern.convert(inputText);
        Player player = new Player();
        player.play(pattern);
    }

    private void exportTxt() {
        String inputText;
        JFileChooser chooseArq = new JFileChooser();
        int response = chooseArq.showSaveDialog(null);

        if(response == JFileChooser.APPROVE_OPTION){
            Path filePath = Path.of(chooseArq.getSelectedFile().getAbsolutePath());
            inputText = textArea.getText();

            try {
                FileWriter fileToSave = new FileWriter(String.valueOf(filePath));
                fileToSave.write(inputText);
                fileToSave.close();
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    private void importTxt() {
        String inputText;
        JFileChooser chooseArq = new JFileChooser();
        int response = chooseArq.showOpenDialog(null);

        if(response == JFileChooser.APPROVE_OPTION){
            Path filePath = Path.of(chooseArq.getSelectedFile().getAbsolutePath());
            try {
                inputText = Files.readString(filePath);
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
            textArea.setText(inputText);
        }
    }
}

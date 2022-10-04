package main;

import main.Gui.Gui;

import javax.sound.midi.InvalidMidiDataException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InvalidMidiDataException {
        new Gui();
    }
}
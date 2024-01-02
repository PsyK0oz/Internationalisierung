package com.exemple;

import java.text.SimpleDateFormat;
import java.util.*;

public class ConsoleThermometerI18N {

    public static void main(String[] args) {
        final int MEASUREMENTS = 3; // number of measurements per series

        // Ask the user for their preferred language
        Scanner scanner = new Scanner(System.in);
        System.out.println(
                "Bitte wählen Sie Ihre bevorzugte Sprache: en für Englisch, fr für Französisch, de für Deutsch");
        String language = scanner.nextLine();

        // Set the default locale based on the user's input
        switch (language) {
            case "en":
                Locale.setDefault(Locale.forLanguageTag("en-GB"));
                break;
            case "fr":
                Locale.setDefault(Locale.forLanguageTag("fr-FR"));
                break;
            case "de":
                Locale.setDefault(Locale.forLanguageTag("de-DE"));
                break;
            default:
                System.out.println("Nicht unterstützte Sprache. Es wird die Systemsprache verwendet.");
                break;
        }

        // turn on thermometer
        ConsoleThermometer t = new ConsoleThermometer();
        for (int counterMeasurements = 0; counterMeasurements < MEASUREMENTS; ++counterMeasurements) {
            // measure actual temperature
            boolean ok = t.measure();
            if (ok) {
                // show actual temperature
                t.show();
            }
        }
        // show min max temperature of the last 5 measures
        t.printMinMax();
        // reset the thermometer, in particular the min/max functionality.
        t.reset();
        // turn off thermometer
        t.turnOff();
        t = null;
    }
}

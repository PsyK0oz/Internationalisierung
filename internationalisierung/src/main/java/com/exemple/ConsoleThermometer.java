package com.exemple;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

class ConsoleThermometer {
    private static final double TEMP_MIN = -20.0;
    private static final double TEMP_MAX = 50.0;

    private double minTemp;
    private double maxTemp;
    private double currentTemp;
    private boolean ready;

    private Scanner sc = new Scanner(System.in);
    private ResourceBundle messages;

    public ConsoleThermometer() {
        Locale currentLocale = Locale.getDefault();
        messages = ResourceBundle.getBundle("MessagesBundle", currentLocale);

        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, currentLocale);
        String formattedDate = df.format(new Date());

        String[] sprichwoerter = messages.getString("proverbs").split(",");
        int i = ((int) (Math.random() * 10)) % sprichwoerter.length;
        String proverb = sprichwoerter[i];

        MessageFormat mf = new MessageFormat(messages.getString("proverb"), currentLocale);

        System.out.println(messages.getString("thermometerOn"));
        this.reset();
        System.out.println(mf.format(new Object[] { formattedDate, proverb }));

        double fee = Math.random() * 10000;
        // Print the license fee
        NumberFormat nf = NumberFormat.getCurrencyInstance(currentLocale);
        System.out.println(messages.getString("license") + nf.format(fee));
    }

    public boolean measure() {
        if (sc == null) {
            System.out.println(messages.getString("thermometerOff"));
            return false;
        }
        System.out.print(messages.getString("measure"));
        double inputTemp = sc.nextDouble();

        if (inputTemp < TEMP_MIN || inputTemp > TEMP_MAX) {
            System.out.println(messages.getString("measureError"));
            return false;
        }

        currentTemp = inputTemp;

        if (!ready) {
            ready = true;
            minTemp = currentTemp;
            maxTemp = currentTemp;
        } else if (inputTemp < minTemp) {
            minTemp = inputTemp;
        } else if (inputTemp > maxTemp) {
            maxTemp = inputTemp;
        }
        return true;
    }

    public void printMinMax() {
        Locale currentLocale = Locale.getDefault();
        NumberFormat nf = NumberFormat.getPercentInstance(currentLocale);
        nf.setMaximumFractionDigits(2);

        // Define the percentage format for different languages
        MessageFormat mf = new MessageFormat("{0,number,percent}", currentLocale);

        if (sc == null) {
            System.out.println(messages.getString("thermometerOff"));
            return;
        }
        if (ready) {
            System.out.println(
                    "\n" + messages.getString("min") + minTemp + " " + messages.getString("max") + maxTemp + "\n");
            // Print the relative minimum and maximum
            System.out.println(messages.getString("relativeMin")
                    + mf.format(new Object[] { (currentTemp - minTemp) / minTemp }));
            System.out.println(messages.getString("relativeMax")
                    + mf.format(new Object[] { (currentTemp - maxTemp) / maxTemp }));
        } else {
            System.out.println("\n" + messages.getString("noValidValues") + "\n");
        }
    }

    public void show() {
        if (sc == null) {
            System.out.println(messages.getString("thermometerOff"));
            return;
        }
        if (ready) {
            System.out.println(messages.getString("currentTemp") + currentTemp);
        } else {
            System.out.println("\n" + messages.getString("noValidValues") + "\n");
        }
    }

    public void reset() {
        minTemp = Double.MAX_VALUE;
        maxTemp = Double.MIN_VALUE;
        currentTemp = Double.MAX_VALUE;
        ready = false;
    }

    public void turnOff() {
        System.out.println(messages.getString("thermometerOff"));
        sc.close();
        sc = null;
    }

}

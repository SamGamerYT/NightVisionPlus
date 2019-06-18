package com.samgameryt.nightvisionplus.utilities;

public class Logger {

    public void printLine(String givenPrefix, String givenMessage, LogType type) {
        if(type.equals(LogType.INFO)) {
            System.out.println("\u001B[36m" + "[" + givenPrefix + "] " + givenMessage + "\u001B[0m");
        } else {
            if(type.equals(LogType.WARNING)) {
                System.out.println("\u001B[33m" + "[" + givenPrefix + "] " + givenMessage + "\u001B[0m");
            } else {
                if(type.equals(LogType.ERROR)) {
                    System.out.println("\u001B[31m" + "[" + givenPrefix + "] " + givenMessage + "\u001B[0m");
                }
            }
        }
    }

}

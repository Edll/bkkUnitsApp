package de.edlly.bkkstundenplan.bkkstundenplan.model.data;

public abstract class FieldStatics {
    /**
     * Konstanten für die die Tage
      */
    public final static int MON = 1;
    public final static int DIN = 2;
    public final static int MIT = 3;
    public final static int DON = 4;
    public final static int FRI = 5;
    public final static int SAM = 6;
    public final static int SON = 7;



    public static String getDayName(int day){
        switch (day){
            case MON:
                return "Montag";
            case DIN:
                return "Dienstag";
            case MIT:
                return "Mittwoch";
            case DON:
                return "Donnerstag";
            case FRI:
                return "Freitag";
            case SAM:
                return "Samstag";
            case SON:
                return "Sonntag";
            default:
                return null;

        }
    }
}
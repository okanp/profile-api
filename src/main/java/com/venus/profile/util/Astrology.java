package com.venus.profile.util;

import java.time.ZonedDateTime;
import java.util.Random;

public class Astrology {

    private static final Random RANDOM = new Random();

    private enum Zodiac {
        AIRES, TAURUS, GEMINI, CANCER, LEO, VIRGO, LIBRA, SCORPIO, SAGITTARIUS, CAPRICORN, AQUARIUS, PISCES
    }

    public static Zodiac zodiac(ZonedDateTime birthday) {
        int month = birthday.getMonthValue();
        int day = birthday.getDayOfMonth();

        if ((month == 12 && day >= 22 && day <= 31) || (month == 1 && day >= 1 && day <= 19)) {
            return Zodiac.CAPRICORN;
        } else if ((month == 1 && day >= 20 && day <= 31) || (month == 2 && day >= 1 && day <= 17)) {
            return Zodiac.AQUARIUS;
        } else if ((month == 2 && day >= 18 && day <= 29) || (month == 3 && day >= 1 && day <= 19)) {
            return Zodiac.PISCES;
        } else if ((month == 3 && day >= 20 && day <= 31) || (month == 4 && day >= 1 && day <= 19)) {
            return Zodiac.AIRES;
        } else if ((month == 4 && day >= 20 && day <= 30) || (month == 5 && day >= 1 && day <= 20)) {
            return Zodiac.TAURUS;
        } else if ((month == 5 && day >= 21 && day <= 31) || (month == 6 && day >= 1 && day <= 20)) {
            return Zodiac.GEMINI;
        } else if ((month == 6 && day >= 21 && day <= 30) || (month == 7 && day >= 1 && day <= 22)) {
            return Zodiac.CANCER;
        } else if ((month == 7 && day >= 23 && day <= 31) || (month == 8 && day >= 1 && day <= 22)) {
            return Zodiac.LEO;
        } else if ((month == 8 && day >= 23 && day <= 31) || (month == 9 && day >= 1 && day <= 22)) {
            return Zodiac.VIRGO;
        } else if ((month == 9 && day >= 23 && day <= 30) || (month == 10 && day >= 1 && day <= 22)) {
            return Zodiac.LIBRA;
        } else if ((month == 10 && day >= 23 && day <= 31) || (month == 11 && day >= 1 && day <= 21)) {
            return Zodiac.SCORPIO;
        } else { // month == 12 && day >= 1 && day <= 21
            return Zodiac.SAGITTARIUS;
        }
    }

    /*
     * yukselen hesabi
     */
    public static Zodiac ascendant(ZonedDateTime birthdayAndTime, double lat, double lon) {
        Zodiac[] zodiacs = Zodiac.values();
        return zodiacs[RANDOM.nextInt(zodiacs.length)];
    }
}

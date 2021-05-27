package com.venus.profile.util.astrology;

import com.venus.profile.util.astrology.Astrology.Zodiac;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;

import static java.util.Map.entry;

public class ZodiacBasedPairing {

    // https://www.self.com/story/zodiac-matches-that-make-the-best-couples
    private static Map bestCouples = Map.ofEntries(
            entry(Pair.of(Zodiac.AIRES, Zodiac.AQUARIUS), "There's never a dull moment between an Aries and Aquarius, which makes their relationship extremely exciting"),
            entry(Pair.of(Zodiac.TAURUS, Zodiac.CANCER), "Taurus and Cancer seriously get each other. These two zodiac signs work well with one another because they hold a tight connection both physically and emotionally"),
            entry(Pair.of(Zodiac.GEMINI, Zodiac.AQUARIUS), "A Gemini and Aquarius have a crazy mental and emotional connection"),
            entry(Pair.of(Zodiac.CANCER, Zodiac.PISCES), "Cancer and Pisces are two cool water signs and instinctively have one massive cosmic connection"),
            entry(Pair.of(Zodiac.LEO, Zodiac.SAGITTARIUS), "The passion is high between Leo and Sagittarius, as both signs both enjoy life and love others who feel the same."),
            entry(Pair.of(Zodiac.VIRGO, Zodiac.TAURUS), "As both are earth signs, Virgo and Taurus really hit it off. Easygoing and practical in their everyday lives, their relationship is cool, calm and collected"),
            entry(Pair.of(Zodiac.LIBRA, Zodiac.GEMINI), "A relationship between a Libra and Gemini is all about a strong intellectual connection."),
            entry(Pair.of(Zodiac.SCORPIO, Zodiac.CANCER), "Sometimes having two passionate people in a relationship doesn't work. However, if one person is a Scorpio and the other is a Cancer, it can be perfect"),
            entry(Pair.of(Zodiac.SAGITTARIUS, Zodiac.AIRES), "Sagittarius and Aries are both fire signs, so you can expect some serious hot passion between the two, making for a dynamite pair."),
            entry(Pair.of(Zodiac.CAPRICORN, Zodiac.TAURUS), "There's a reason these two signs have been reported to have more chemistry than any of the other astrological signs."),
            entry(Pair.of(Zodiac.AQUARIUS, Zodiac.GEMINI), "Aquarius and Gemini are both air signs that have a killer psychological connection. And it goes deep—really deep, like finishing each other's sentences. ")
    );

    public static boolean isBestCouple(Zodiac z1, Zodiac z2) {
        return bestCouples.containsKey(Pair.of(z1, z2));
    }

}

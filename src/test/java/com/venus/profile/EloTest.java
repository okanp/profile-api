package com.venus.profile;

import com.venus.profile.util.Elo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@ActiveProfiles("test")
class EloTest {

    @Test
    void should_calculate_elo() {
        int oldA = 1200, oldB = 1000;
        int[] results = Elo.game(oldA, oldB, true);

        int diffA1 = Math.abs(results[0] - oldA);
        int diffB1 = Math.abs(results[1] - oldB);
        assertThat(diffA1 > 0, is(true));
        assertThat(diffB1 < 0, is(true));

        int oldA2 = 1200, oldB2 = 1200;
        int[] results2 = Elo.game(oldA2, oldB2, true);
        int diffA2 = Math.abs(results2[0] - oldA2);
        int diffB2 = Math.abs(results2[1] - oldB2);
        assertThat(diffA2 > diffA1, is(true));
        assertThat(diffB2 > diffB1, is(true));
    }
}

package io.github.wingedpanther.cleanmytv.home

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class BandsTest {
    /** A 1024-point capture has 512 bins; the visualiser reads up to 80 % of them. */
    private val top = 409

    @Test
    fun everyBarHasItsOwnBins() {
        for (n in listOf(8, 16, 32, 48, 64)) {
            val e = logBands(n, top)
            assertEquals(n + 1, e.size)
            assertEquals(1, e[0])
            assertEquals(top, e[n])
            for (b in 0 until n) assertTrue("bar $b of $n", e[b + 1] > e[b])
        }
    }

    @Test
    fun wideBandsStayLogSpaced() {
        val e = logBands(8, top)
        // Each band covers about twice the bins of the one before once they are wider than one bin.
        assertTrue(e[7] - e[6] > e[6] - e[5])
    }

    @Test
    fun moreBarsThanBinsStayInRange() {
        val e = logBands(64, 51)
        for (b in 0 until 64) {
            assertTrue(e[b] >= 1)
            assertTrue(e[b] < 51)
        }
    }
}

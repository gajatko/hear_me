package yacekbass.ear.training.generators

import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*

class RandomMusicProviderTest {

    @Test
    fun uniformDistr() {
        val provider = RandomMusicProvider()
        val uniform = provider.defaultDistribution(listOf("1", "3", "4", "5"))
        Assert.assertEquals(mapOf(0 to 1, 1 to 1, 2 to 1, 3 to 1), uniform)
    }
}
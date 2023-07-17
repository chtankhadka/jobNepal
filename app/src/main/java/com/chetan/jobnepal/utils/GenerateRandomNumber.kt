package com.chetan.jobnepal.utils

import kotlin.random.Random

object GenerateRandomNumber {
    fun generateRandomNumber(range: IntRange) : Int {
        return Random.nextInt(range.start, range.endInclusive + 1)
    }
}
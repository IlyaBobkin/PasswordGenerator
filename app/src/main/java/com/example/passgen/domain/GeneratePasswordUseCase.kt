package com.example.passgen.domain

import javax.inject.Inject
import kotlin.math.log2

class GeneratePasswordUseCase @Inject constructor() {
    operator fun invoke(length: Int, charset: String): Pair<String, Double> {
        val password = (1..length)
            .map { charset.random() }
            .joinToString("")
        val entropy = calculateEntropy(password)
        return password to entropy
    }

    private fun calculateEntropy(password: String): Double {
        val freq = password.groupingBy { it }.eachCount()
        val len = password.length.toDouble()
        return freq.values.sumOf { count ->
            val p = count / len
            -p * log2(p)
        }
    }
}
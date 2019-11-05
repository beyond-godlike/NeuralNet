package com.unava.dia.neuralnet

import java.util.*

/**
 * @author https://code.sololearn.com/c5xdmYzqoxBb/#kt
 *
 */
class NeuralNetwork {
    private val random = Random()

    private var weight1: Float = random.nextFloat() % 1
    private var weight2: Float = random.nextFloat() % 1

    fun think(input1: Int, input2: Int): Int {
        val output: Float = weight1 * input1 + weight2 * input2
        return output.toInt()
    }

    fun train(input1: Int, input2: Int, output: Int) {
        val out = think(input1, input2)
        val error = output - out
        weight1 += (0.01f * error * input1)
        weight2 += (0.01f * error * input2)
    }
}
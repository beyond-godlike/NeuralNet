package com.unava.dia.neuralnet

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val aiAdd = NeuralNetwork()
    private val aiMinus = NeuralNetwork()
    private val aiMultiply = NeuralNetwork()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.init()
    }

    private fun init() {
        this.trainSet(aiAdd, Int::plus)
        this.trainSet(aiMinus, Int::minus)
        this.trainSet(aiMultiply, Int::times)

        btLearnAdd.setOnClickListener {
            // HELP ME SOMEONE REFACTOR THIS SHIT
            val a = etFirst.getInt()
            val b = etSecond.getInt()
            aiAdd.train(a, b, a + b)
        }
        btLearnMultiply.setOnClickListener {
            val a = etFirst.getInt()
            val b = etSecond.getInt()
            aiMultiply.train(a, b, a * b)
        }
        btLearnMinus.setOnClickListener {
            val a = etFirst.getInt()
            val b = etSecond.getInt()
            aiMinus.train(a, b, a - b)
        }
        btAdd.setOnClickListener {
            calculate(aiAdd, " + ")
        }
        btMultiply.setOnClickListener {
            calculate(aiMultiply, " * ")
        }
        btMinus.setOnClickListener {
            calculate(aiMinus, " - ")
        }
    }

    private fun EditText.getInt() : Int {
        return text.trim().toString().toInt()
    }

    /**
     * Calculates user`s input
     *
     * @param ai One of 3 neural networks
     * @param sign Operation sign to show
     */
    private fun calculate(ai: NeuralNetwork, sign: String) {
        tvSign.text = sign
        val a = etFirst.text.trim().toString().toInt()
        val b = etSecond.text.trim().toString().toInt()
        val c = ai.think(a, b)
        tvResult.text = c.toString()
    }

    /**
     * Trains [ai] according to operation input
     *
     * @param ai One of 3 neural networks
     * @param op Operation type
     *
     */
    private fun trainSet(ai: NeuralNetwork, op: (Int, Int) -> Int) {
        repeat((0..10000).count()) {
            for (i in 1..10) {
                val x = i + 1
                ai.train(i, x, op(i, x))
            }
        }
        //Toast.makeText(applicationContext, "finished$op", Toast.LENGTH_SHORT).show()
    }
}

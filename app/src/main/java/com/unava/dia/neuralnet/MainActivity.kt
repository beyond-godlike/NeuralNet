package com.unava.dia.neuralnet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // going to make 3 different brains
    private val aiAdd = NeuralNetwork() // this one can add two single-digit numbers
    private val aiMinus = NeuralNetwork()
    private val aiMultiply = NeuralNetwork() // this one can add two numbers and multiply the result by 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init () {
        this.trainSet(aiAdd, Int::plus)
        this.trainSet(aiMinus, Int::minus)
        this.trainSet(aiMultiply, Int::times)
        //Int::div

        btLearnAdd.setOnClickListener {
            // HELP ME SOMEONE TO REFACTOR THIS SHIT
            val a = etFirst.text.trim().toString().toInt()
            val b = etSecond.text.trim().toString().toInt()
            aiAdd.train(a, b, a + b)
        }
        btLearnMultiply.setOnClickListener {
            val a = etFirst.text.trim().toString().toInt()
            val b = etSecond.text.trim().toString().toInt()
            aiMultiply.train(a, b, a * b)
        }
        btLearnMinus.setOnClickListener {
            val a = etFirst.text.trim().toString().toInt()
            val b = etSecond.text.trim().toString().toInt()
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

    private fun calculate(ai: NeuralNetwork, sign: String) {
        tvSign.text = sign
        val a = etFirst.text.trim().toString().toInt()
        val b = etSecond.text.trim().toString().toInt()
        val c = ai.think(a, b)
        tvResult.text = c.toString()
    }

    private fun trainSet(ai: NeuralNetwork, op: (Int, Int) -> Int) {
        repeat((0..10000).count()) {
            for(i in 1..10){
                val x = i + 1
                ai.train(i, x, op(i, x))
            }
        }
        Toast.makeText(applicationContext, "finished$op", Toast.LENGTH_SHORT).show()
    }
}

package com.example.myapplication

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val totalCost = intent.getIntExtra("TOTAL_COST", 0)
        val resultText = findViewById<TextView>(R.id.resultText)
        resultText.text = "Оплатить = $totalCost рублей"
    }
}
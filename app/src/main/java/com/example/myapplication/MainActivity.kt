package com.example.myapplication
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.math.BigDecimal
import java.math.RoundingMode

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputField = findViewById<EditText>(R.id.inputNumber)
        val calculateButton = findViewById<Button>(R.id.calculateButton)
        val resultText = findViewById<TextView>(R.id.resultText)
        calculateButton.setOnClickListener {
            try {
                val n = inputField.text.toString().toInt()
                if (n <= 0) {
                    Toast.makeText(this, "Введите положительное число", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val result = calculateSeriesSum(n)
                resultText.text = "Результат: $result"
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Введите корректное число", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
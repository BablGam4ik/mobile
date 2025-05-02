package com.example.myapplication
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputField = findViewById<EditText>(R.id.inputString)
        val processButton = findViewById<Button>(R.id.processButton)
        val resultText = findViewById<TextView>(R.id.resultText)
        val modifiedText = findViewById<TextView>(R.id.modifiedText)

        processButton.setOnClickListener {
            val inputString = inputField.text.toString()

            if (inputString.isEmpty()) {
                Toast.makeText(this, "Введите строку", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            val modifiedString = replaceDigitsWithSpaces(inputString)
            modifiedText.text = "Модифицированная строка: \"$modifiedString\""


            val lowercaseCount = countLowercaseLetters(inputString)
            resultText.text = "Количество строчных латинских букв: $lowercaseCount"
        }
    }
}

private fun MainActivity.replaceDigitsWithSpaces(string: String) {}

private fun MainActivity.countLowercaseLetters(string: String) {}

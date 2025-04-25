package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

private fun MainActivity.checkCharacter(string: String) {}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val textEdit = findViewById<EditText>(R.id.textEdit)
        val checkButton = findViewById<Button>(R.id.checkButton)

        checkButton.setOnClickListener {
            checkCharacter(textEdit.text.toString())
        }
        fun checkCharacter(input: String) {
            val trimmedInput = input.trim()

            if (trimmedInput.isEmpty()) {
                Toast.makeText(this, "Введите символ", Toast.LENGTH_SHORT).show()
                return
            }

            val ch = trimmedInput.first()
        }
    }
}
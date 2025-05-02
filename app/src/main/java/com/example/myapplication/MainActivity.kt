package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val inputField = findViewById<EditText>(R.id.inputString)
        val processButton = findViewById<Button>(R.id.processButton)
        val resultText = findViewById<TextView>(R.id.resultText)
        val modifiedText = findViewById<TextView>(R.id.modifiedText)
    }
}

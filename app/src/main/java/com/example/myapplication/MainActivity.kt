package com.example.myapplication
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val calculateButton = findViewById<Button>(R.id.calculateButton)
        calculateButton.setOnClickListener {
            calculateCost()
        }
    }

    private fun calculateCost() {
        val hoursInput = findViewById<EditText>(R.id.hoursInput)
        val hoursText = hoursInput.text.toString()

        if (hoursText.isEmpty()) {
            Toast.makeText(this, "Введите количество часов", Toast.LENGTH_SHORT).show()
            return
        }

        val hours = hoursText.toInt()
        if (hours <= 0) {
            Toast.makeText(this, "Количество часов должно быть больше 0", Toast.LENGTH_SHORT).show()
            return
        }

        val vehicleTypeGroup = findViewById<RadioGroup>(R.id.vehicleTypeGroup)
        val selectedId = vehicleTypeGroup.checkedRadioButtonId

        if (selectedId == -1) {
            Toast.makeText(this, "Выберите тип транспорта", Toast.LENGTH_SHORT).show()
            return
        }

        val pricePerHour = when (selectedId) {
            R.id.carRadio -> 2000
            R.id.minivanRadio -> 2500
            R.id.busRadio -> 3500
            else -> 0
        }

        val totalCost = hours * pricePerHour

        val intent = Intent(this, ResultActivity::class.java).apply {
            putExtra("TOTAL_COST", totalCost)
        }
        startActivity(intent)
    }
}
package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

private const val EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.android.geoquiz.answer_is_true"
private const val EXTRA_ANSWER_SHOWN = "com.example.myapplication.answer_shown"

class CheatActivity : AppCompatActivity() {

    private lateinit var answerTextView: TextView
    private lateinit var showAnswerButton: Button
    private lateinit var apiVersionText: TextView
    private var answerIsTrue = false
    private var cheatCount = 0
    private val MAX_CHEATS = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cheat)

        // Получаем правильный ответ из интента
        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)

        // Получаем количество использованных подсказок
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        cheatCount = sharedPref.getInt("cheat_count", 0)

        answerTextView = findViewById(R.id.answer_text_view)
        showAnswerButton = findViewById(R.id.show_answer_button)
        apiVersionText = findViewById(R.id.api_version_text)

        // Устанавливаем информацию о версии Android
        apiVersionText.text = "Android ${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})"

        showAnswerButton.setOnClickListener {
            if (cheatCount < MAX_CHEATS) {
                cheatCount++
                // Сохраняем новое количество
                sharedPref.edit().putInt("cheat_count", cheatCount).apply()

                val answerText = when {
                    answerIsTrue -> R.string.true_button
                    else -> R.string.false_button
                }
                answerTextView.setText(answerText)
                setAnswerShownResult(true)

                // Показываем оставшиеся попытки
                Toast.makeText(this, "Осталось подсказок: ${MAX_CHEATS - cheatCount}", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Вы использовали все подсказки!", Toast.LENGTH_LONG).show()
                showAnswerButton.isEnabled = false
            }
        }
    }

    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(RESULT_OK, data)
    }

    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }

        fun wasAnswerShown(result: Intent): Boolean {
            return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false)
        }
    }
}
package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var questionTextView: TextView

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    private var currentIndex = 0
    private var correctAnswers = 0
    private var answeredQuestions = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        // Восстановление состояния
        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(KEY_INDEX, 0)
            correctAnswers = savedInstanceState.getInt(KEY_CORRECT, 0)
            answeredQuestions = savedInstanceState.getInt(KEY_ANSWERED, 0)
        }

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener { _ -> checkAnswer(true) }
        falseButton.setOnClickListener { _ -> checkAnswer(false) }
        nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

        updateQuestion()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_INDEX, currentIndex)
        outState.putInt(KEY_CORRECT, correctAnswers)
        outState.putInt(KEY_ANSWERED, answeredQuestions)
    }

    private fun updateQuestion() {
        val question = questionBank.getOrNull(currentIndex) ?: run {
            Log.e(TAG, "Question index out of bounds: $currentIndex")
            return
        }
        questionTextView.setText(question.textResId)

        // Показываем кнопки для нового вопроса
        trueButton.visibility = View.VISIBLE
        falseButton.visibility = View.VISIBLE

        // Управление кнопкой Next
        if (currentIndex < questionBank.size - 1) {
            nextButton.isEnabled = true
            nextButton.visibility = View.VISIBLE
        } else {
            nextButton.isEnabled = false
            nextButton.visibility = View.INVISIBLE
        }
    }

    private fun checkAnswer(userAnswer: Boolean) {
        answeredQuestions++
        val correctAnswer = questionBank[currentIndex].answer
        val isCorrect = userAnswer == correctAnswer

        if (isCorrect) {
            correctAnswers++
        }

        val messageResId = if (isCorrect) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()

        // Скрываем кнопки после ответа
        trueButton.visibility = View.INVISIBLE
        falseButton.visibility = View.INVISIBLE

        // Если последний вопрос - показываем результат
        if (currentIndex == questionBank.size - 1) {
            nextButton.isEnabled = false
            nextButton.visibility = View.INVISIBLE
            showResult()
        }
    }

    private fun showResult() {
        val percentage = (correctAnswers.toFloat() / questionBank.size) * 100
        val resultMessage = "Правильных ответов: $correctAnswers из ${questionBank.size}\n" +
                "Результат: ${"%.1f".format(percentage)}%"

        AlertDialog.Builder(this)
            .setTitle("Результат викторины")
            .setMessage(resultMessage)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .setCancelable(false)
            .show()

        // Сброс счетчиков для повторного прохождения
        correctAnswers = 0
        answeredQuestions = 0
    }

    companion object {
        private const val KEY_INDEX = "index"
        private const val KEY_CORRECT = "correct_answers"
        private const val KEY_ANSWERED = "answered_questions"
    }
}






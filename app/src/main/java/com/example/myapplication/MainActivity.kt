package com.example.myapplication

import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider

private const val TAG = "MainActivity"


class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var cheatButton: Button
    private lateinit var questionTextView: TextView
    private lateinit var quizViewModel: QuizViewModel

    private var correctAnswers = 0
    private var answeredQuestions = 0


    private val cheatLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            result.data?.let {
                quizViewModel.isCheater = CheatActivity.wasAnswerShown(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        quizViewModel = ViewModelProvider(this).get(QuizViewModel::class.java)
        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")

        if (savedInstanceState != null) {
            correctAnswers = savedInstanceState.getInt(KEY_CORRECT, 0)
            answeredQuestions = savedInstanceState.getInt(KEY_ANSWERED, 0)
            quizViewModel.currentIndex = savedInstanceState.getInt(KEY_INDEX, 0)
        }

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        cheatButton = findViewById(R.id.cheat_button)
        questionTextView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener { checkAnswer(true) }
        falseButton.setOnClickListener { checkAnswer(false) }

        nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }


        cheatButton.setOnClickListener {
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)

            cheatLauncher.launch(intent)
        }

        updateQuestion()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i(TAG, "onSaveInstanceState")
        outState.putInt(KEY_CORRECT, correctAnswers)
        outState.putInt(KEY_ANSWERED, answeredQuestions)
        outState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }

    private fun updateQuestion() {
        questionTextView.text = getString(quizViewModel.currentQuestionText)
        trueButton.visibility = View.VISIBLE
        falseButton.visibility = View.VISIBLE

        nextButton.isEnabled = quizViewModel.hasNextQuestion()
        nextButton.visibility = if (quizViewModel.hasNextQuestion()) View.VISIBLE else View.INVISIBLE
    }

    private fun checkAnswer(userAnswer: Boolean) {
        answeredQuestions++
        val isCorrect = userAnswer == quizViewModel.currentQuestionAnswer

        if (quizViewModel.isCheater) {
            Toast.makeText(this, R.string.judgment_toast, Toast.LENGTH_LONG).show()
        }

        if (isCorrect) {
            correctAnswers++
        }

        val messageResId = if (isCorrect) R.string.correct_toast else R.string.incorrect_toast
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()

        trueButton.visibility = View.INVISIBLE
        falseButton.visibility = View.INVISIBLE

        if (!quizViewModel.hasNextQuestion()) {
            showResult()
        }
    }

    private fun showResult() {
        val totalQuestions = quizViewModel.getTotalQuestions()
        val percentage = (correctAnswers.toFloat() / totalQuestions) * 100
        val resultMessage = "Правильных ответов: $correctAnswers из $totalQuestions\n" +
                "Результат: ${"%.1f".format(percentage)}%"

        AlertDialog.Builder(this)
            .setTitle("Результат викторины")
            .setMessage(resultMessage)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .setCancelable(false)
            .show()

        correctAnswers = 0
        answeredQuestions = 0
        quizViewModel.reset()
    }

    companion object {
        private const val KEY_INDEX = "index"
        private const val KEY_CORRECT = "correct_answers"
        private const val KEY_ANSWERED = "answered_questions"

    }

}
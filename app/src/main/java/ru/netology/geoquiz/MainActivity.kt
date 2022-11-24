package ru.netology.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import ru.netology.geoquiz.databinding.ActivityMainBinding      // К имени текущего пакета добавили .databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding   // Через binding как свойства (через точку) будут доступны все элементы макета, имеющие id

/*    // Кнопки будут доступны через binding, поэтому объявлять их в этом месте нет смысла, раз объявлен уже binding
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button */

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            ActivityMainBinding.inflate(layoutInflater)  // ActivityMainBinding - это уже обработанный activity_main.xml (название ActivityMainBinding автоматическое)
        setContentView(binding.root)    // ВМЕСТО setContentView(R.layout.activity_main)

/*        // Кнопки доступны через binding, поэтому "надувать" их в этом месте нет смысла, раз "надут" binding
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button) */

        binding.trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
        }

        binding.falseButton.setOnClickListener { view: View ->
            checkAnswer(false)
        }

        // Логичнее было сделать private fun по аналогии с остальными, но хотелось протестить лямбду
        val nextPrevClickListener = { forward: Boolean ->
            val step = if (forward) 1 else -1
            currentIndex = (questionBank.size + currentIndex + step) % questionBank.size
            updateQuestion()
        }

        binding.nextButton.setOnClickListener {
            nextPrevClickListener(true)
        }

        binding.prevButton.setOnClickListener {
            nextPrevClickListener(false)
        }

        binding.questionTextView.setOnClickListener {
            nextPrevClickListener(true)
        }

        updateQuestion()
    }

    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        binding.questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer

        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(
            this,
            messageResId,
            Toast.LENGTH_SHORT
        ).show()
    }

}

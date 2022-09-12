package com.example.wordle

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    val wordList = FourLetterWord.FourLetterWordList
    var wordToGuess = wordList.getRandomFourLetterWord()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val correctWord = findViewById<TextView>(R.id.wordToGuess)
        correctWord.text = wordToGuess
        val guessBtn = findViewById<Button>(R.id.button)
        var guessNum = 1
        Log.d("WordToGuess", wordToGuess) // use this to log randomWord for debugging

        // I use the guess button as the reset button but could potentially
        // make a second button instead that's initially invisible
        guessBtn.setOnClickListener {
            // Hides keyboard when button is pressed
            this.currentFocus?.let { view ->
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(view.windowToken, 0)
            }

            // get word from editText, empty EditText, and compare word to wordToGuess
            val simpleEditText = findViewById<EditText>(R.id.et_simple)
            val strValue = simpleEditText.text.toString().uppercase()
            simpleEditText.setText("")
            var checkedGuess = ""
            if (strValue.length == 4) {
                checkedGuess = checkGuess(strValue)
            } else {
                // only display when not resetting
                if (guessNum < 5){
                    Toast.makeText(it.context, "Must be a 4 letter word!", Toast.LENGTH_SHORT).show()
                }
            }
            // get textView of words
            val guessOne = findViewById<TextView>(R.id.guessOne)
            val checkGuessOne = findViewById<TextView>(R.id.guessOneCheck)
            val guessTwo = findViewById<TextView>(R.id.guessTwo)
            val checkGuessTwo = findViewById<TextView>(R.id.guessTwoCheck)
            val guessThree = findViewById<TextView>(R.id.guessThree)
            val checkGuessThree = findViewById<TextView>(R.id.guessThreeCheck)

            // outer if statement prevents the insertion of words without a length of 4
            if (checkedGuess != "") {
                when (guessNum) {
                    1 -> {
                        guessOne.text = strValue
                        checkGuessOne.text = checkedGuess
                        guessNum++
                    }
                    2 -> {
                        guessTwo.text = strValue
                        checkGuessTwo.text = checkedGuess
                        guessNum++
                    }
                    3 -> {
                        guessThree.text = strValue
                        checkGuessThree.text = checkedGuess
                        guessNum++
                    }
                }
            }

            // Resets the wordle game and gets new random word
            if (guessNum > 4) {
                guessNum = 1
                guessOne.text = ""
                checkGuessOne.text = ""
                guessTwo.text = ""
                checkGuessTwo.text = ""
                guessThree.text = ""
                checkGuessThree.text = ""
                wordToGuess = wordList.getRandomFourLetterWord()
                correctWord.visibility = View.GONE
                correctWord.text = wordToGuess
                //guessBtn.text = "Guess!"
                guessBtn.setText(R.string.guessText)
                Log.d("WordToGuess", wordToGuess)
            }

            if (guessNum == 4 || checkedGuess == "OOOO") {
                correctWord.visibility = View.VISIBLE
                guessBtn.text = "Reset"
                guessNum = 5
                //guessBtn.isClickable = false
            }

        }
    }
    /**
     * Parameters / Fields:
     *   wordToGuess : String - the target word the user is trying to guess
     *   guess : String - what the user entered as their guess
     *
     * Returns a String of 'O', '+', and 'X', where:
     *   'O' represents the right letter in the right place
     *   '+' represents the right letter in the wrong place
     *   'X' represents a letter not in the target word
     */
    private fun checkGuess(guess: String) : String {
        var result = ""
        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                result += "O"
            }
            else if (guess[i] in wordToGuess) {
                result += "+"
            }
            else {
                result += "X"
            }
        }
        return result
    }
}




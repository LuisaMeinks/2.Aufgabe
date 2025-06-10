package com.example.quizapp

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.TextView

// Hauptklasse der App, die von Androids Activity-Klasse erbt
class MainActivity : Activity() {

    // Liste der Fragen, jede mit Text, Antwortmöglichkeiten und dem Index der richtigen Antwort
    private val questions = listOf(
        Question("In welchem Jahr fiel die Berliner Mauer?", listOf("1989", "1990", "1988"), 0),
        Question("Wann war der Erste Weltkrieg?", listOf("1914-1917", "1914-1918", "1913-1915"), 1),
        Question("Wie lange dauerte der Zweite Weltkrieg?", listOf("3 Jahre", "8 Jahre", "6 Jahre"), 2)
    )

    // Index der aktuellen Frage und aktueller Punktestand
    private var currentIndex = 0
    private var score = 0

    // UI-Elemente: Textfelder und Buttons
    private lateinit var questionText: TextView
    private lateinit var scoreText: TextView
    private lateinit var resultText: TextView
    private lateinit var answerButton1: Button
    private lateinit var answerButton2: Button
    private lateinit var answerButton3: Button

    // Wird beim Start der App aufgerufen
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Verknüpfung der UI-Elemente mit den Views aus dem XML-Layout
        questionText = findViewById(R.id.questionText)
        scoreText = findViewById(R.id.scoreText)
        resultText = findViewById(R.id.resultText)
        answerButton1 = findViewById(R.id.answerButton1)
        answerButton2 = findViewById(R.id.answerButton2)
        answerButton3 = findViewById(R.id.answerButton3)

        // Erste Frage anzeigen
        loadQuestion()
    }

    // Lädt die aktuelle Frage oder beendet das Quiz, wenn alle beantwortet wurden
    private fun loadQuestion() {
        if (currentIndex >= questions.size) {
            // Quiz ist beendet, Endnachricht anzeigen
            questionText.text = "Quiz beendet!"
            resultText.text = "Du hast $score von ${questions.size} richtig."
            // Buttons deaktivieren
            answerButton1.isEnabled = false
            answerButton2.isEnabled = false
            answerButton3.isEnabled = false
            return
        }

        // Aktuelle Frage abrufen
        val q = questions[currentIndex]

        // Frage und Punktestand anzeigen
        questionText.text = q.text
        scoreText.text = "Punkte: $score"
        resultText.text = ""

        // Antwortbuttons vorbereiten
        val buttons = listOf(answerButton1, answerButton2, answerButton3)
        for (i in buttons.indices) {
            val btn = buttons[i]
            btn.text = q.answers[i]
            btn.setBackgroundColor(Color.LTGRAY) // Zurücksetzen der Farbe

            // Klick-Listener für jede Antwort
            btn.setOnClickListener {
                // Überprüfung: War die Antwort richtig?
                if (i == q.correctAnswerIndex) {
                    btn.setBackgroundColor(Color.GREEN)
                    score++
                } else {
                    btn.setBackgroundColor(Color.RED)
                }

                // Nächste Frage nach 1 Sekunde anzeigen
                Handler().postDelayed({
                    currentIndex++
                    loadQuestion()
                }, 1000)
            }
        }
    }
}

// Datenklasse zur Darstellung einer einzelnen Quizfrage
data class Question(
    val text: String,                   // Die eigentliche Frage
    val answers: List<String>,         // Liste der Antwortmöglichkeiten
    val correctAnswerIndex: Int        // Index der richtigen Antwort (0-basiert)
)

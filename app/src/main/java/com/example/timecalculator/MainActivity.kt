package com.example.timecalculator

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {

    private lateinit var editText1: EditText
    private lateinit var editText2: EditText
    private lateinit var resultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        editText1 = findViewById(R.id.editText1)
        editText2 = findViewById(R.id.editText2)
        resultTextView = findViewById(R.id.textView3)

        findViewById<Button>(R.id.buttonPlus).setOnClickListener { onButtonClickPlus() }
        findViewById<Button>(R.id.buttonMinus).setOnClickListener { onButtonClickMinus() }
    }

    private fun onButtonClickPlus() {
        val time1 = editText1.text.toString()
        val time2 = editText2.text.toString()

        val totalSeconds = convertToSeconds(time1) + convertToSeconds(time2)
        val result = convertToTimeFormat(totalSeconds)
        resultTextView.text = result

        Toast.makeText(this, "Результат: $result", Toast.LENGTH_SHORT).show()
    }

    private fun onButtonClickMinus() {
        val time1 = editText1.text.toString()
        val time2 = editText2.text.toString()

        val totalSeconds = convertToSeconds(time1) - convertToSeconds(time2)
        val result = convertToTimeFormat(totalSeconds)
        resultTextView.text = result

        Toast.makeText(this, "Результат: $result", Toast.LENGTH_SHORT).show()
    }

    private fun convertToSeconds(time: String): Int {
        var totalSeconds = 0
        val regex = Regex("(\\d+)([hms])")
        val matches = regex.findAll(time)

        for (match in matches) {
            val value = match.groups[1]?.value?.toInt() ?: 0
            val unit = match.groups[2]?.value

            totalSeconds += when (unit) {
                "h" -> value * 3600
                "m" -> value * 60
                "s" -> value
                else -> 0
            }
        }
        return totalSeconds
    }

    private fun convertToTimeFormat(seconds: Int): String {
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val secs = seconds % 60

        return StringBuilder().apply {
            if (hours > 0) append("${hours}h ")
            if (minutes > 0) append("${minutes}m ")
            if (secs > 0) append("${secs}s")
        }.toString().trim().ifEmpty { "0s" }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_clear -> {
                clearFields()
                true
            }
            R.id.action_exit -> {
                exitApp()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun clearFields() {
        editText1.text.clear()
        editText2.text.clear()
        resultTextView.text = "Результат"
        resultTextView.setTextColor(0xFF000000.toInt())

        Toast.makeText(this, "Данные очищены", Toast.LENGTH_SHORT).show()
    }

    private fun exitApp() {
        finish()
        Toast.makeText(this, "Выход из приложения", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "Приложение закрыто", Toast.LENGTH_SHORT).show()
    }

    fun onButtonClickPlus(view: View) {}
    fun onButtonClickMinus(view: View) {}
}
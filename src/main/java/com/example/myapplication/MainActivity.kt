package com.example.myapplication

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val generateButton = findViewById<Button>(R.id.generate_button)
        val passwordTextView = findViewById<TextView>(R.id.password_text_view)
        var copyButton = findViewById<Button>(R.id.copy_button)

        copyButton.visibility = View.GONE

        generateButton.setOnClickListener {
            val random = Random
            val passwordLength = random.nextInt(9) + 8
            val password = CharArray(passwordLength) {
                val ranges = listOf('a'..'z', 'A'..'Z', '0'..'9')
                val range = ranges[random.nextInt(ranges.size)]
                range.elementAt(random.nextInt(range.count()))
            }
            passwordTextView.text = "Password: ${password.joinToString("")}"
            copyButton.visibility = View.VISIBLE
        }

        copyButton.setOnClickListener {
            val password = passwordTextView.text.toString().substringAfter(": ").trim()
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Password", password)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, "Password copied to clipboard", Toast.LENGTH_SHORT).show()
        }
    }
}
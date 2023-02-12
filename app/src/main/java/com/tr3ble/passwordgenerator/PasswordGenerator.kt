package com.tr3ble.passwordgenerator

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var passwordTextView: TextView
    private lateinit var passwordLengthSeekBar: SeekBar
    private lateinit var passwordLengthTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

        passwordTextView = findViewById(R.id.password_text_view)
        passwordLengthSeekBar = findViewById(R.id.password_length_seek_bar)
        passwordLengthTextView = findViewById(R.id.password_length_text_view)

            passwordLengthSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    passwordLengthTextView.text = (progress).toString()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })

            val generateButton = findViewById<Button>(R.id.generate_button)
        val copyButton = findViewById<Button>(R.id.copy_button)

        copyButton.visibility = View.GONE

        generateButton.setOnClickListener {
            val random = Random
            val passwordLength = passwordLengthSeekBar.progress
            val password = CharArray(passwordLength) {
                val ranges = listOf('a'..'z', 'A'..'Z', '0'..'9')
                val range = ranges.random(random)
                range.random(random)
            }
            passwordTextView.text = "Password: ${password.joinToString("")}"
            copyButton.visibility = View.VISIBLE
        }

        copyButton.setOnClickListener {
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Password", passwordTextView.text.toString().substring(10))
            Toast.makeText(this, "Password copied to clipboard", Toast.LENGTH_SHORT).show()
            clipboard.setPrimaryClip(clip)
        }
    }
}
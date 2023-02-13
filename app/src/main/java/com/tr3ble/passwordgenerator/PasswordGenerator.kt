package com.tr3ble.passwordgenerator
// hmm...
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
    private lateinit var generateButton: Button
    private lateinit var copyButton: Button
    private val passwordLengthSeekBarListener = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            passwordLengthTextView.text = (progress).toString()
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {}
        override fun onStopTrackingTouch(seekBar: SeekBar?) {}
    }
    private val passwordGenerationListener = View.OnClickListener {
        generatePassword()
    }
    private val passwordCopyListener = View.OnClickListener {
        copyPasswordToClipboard()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        setListeners()
    }

    private fun initViews() {
        passwordTextView = findViewById(R.id.password_text_view)
        passwordLengthSeekBar = findViewById(R.id.password_length_seek_bar)
        passwordLengthTextView = findViewById(R.id.password_length_text_view)
        generateButton = findViewById(R.id.generate_button)
        copyButton = findViewById(R.id.copy_button)
        passwordLengthSeekBar.setOnSeekBarChangeListener(passwordLengthSeekBarListener)
        copyButton.visibility = View.GONE
    }

    private fun setListeners() {
        generateButton.setOnClickListener(passwordGenerationListener)
        copyButton.setOnClickListener(passwordCopyListener)
    }

    private fun generatePassword() {
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

    private fun copyPasswordToClipboard() {
        val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Password", passwordTextView.text.toString().substring(10))
        Toast.makeText(this, "Password copied to clipboard", Toast.LENGTH_SHORT).show()
        clipboard.setPrimaryClip(clip)
    }
}

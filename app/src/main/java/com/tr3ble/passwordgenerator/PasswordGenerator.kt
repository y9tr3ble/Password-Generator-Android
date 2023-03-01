package com.tr3ble.passwordgenerator

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.google.android.material.color.DynamicColors
import com.google.android.material.slider.Slider
import com.tr3ble.passwordgenerator.databinding.ActivityMainBinding
import kotlin.random.Random

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val random = Random

    private var useNumbers = false
    private var useSpecialChars = false

    private var passwordLength = 12
        @SuppressLint("SetTextI18n")
        set(value) {
            field = value
            binding.passwordLengthTextView.text = "Password length: $value"
        }

    private var isGenerating = false

    override fun onCreate(savedInstanceState: Bundle?) {
        DynamicColors.applyToActivityIfAvailable(this)

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.passwordLengthSeekBar.addOnSliderTouchListener(object :
            Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {}

            override fun onStopTrackingTouch(slider: Slider) {
                generatePassword()
            }
        })

        binding.passwordLengthSeekBar.addOnChangeListener { _, value, _ ->
            passwordLength = value.toInt()
        }

        binding.useNumbersSwitch.setOnCheckedChangeListener { _, isChecked ->
            useNumbers = isChecked
            generatePassword()
        }

        binding.useSpecialCharsSwitch.setOnCheckedChangeListener { _, isChecked ->
            useSpecialChars = isChecked
            generatePassword()
        }

        binding.passwordTextView.addTextChangedListener {
            binding.copyButton.visibility = if ((it?.length ?: 0) >= 10) View.VISIBLE else View.INVISIBLE
        }

        binding.generateButton.setOnClickListener {
            generatePassword()
        }

        binding.copyButton.setOnClickListener {
            val clipboard = ContextCompat.getSystemService(applicationContext, ClipboardManager::class.java)
            val clip = ClipData.newPlainText("Password", binding.passwordTextView.text.toString().substring(10))
            clipboard?.setPrimaryClip(clip)
            Toast.makeText(this, "Password copied to clipboard", Toast.LENGTH_SHORT).show()
        }

        generatePassword()
    }
    private fun generatePassword() {
        if (isGenerating) {
            return
        }

        isGenerating = true
        val ranges = mutableListOf('a'..'z', 'A'..'Z')
        if (useNumbers) {
            ranges.add('0'..'9')
        }
        if (useSpecialChars) {
            ranges.add('!'..'/')
        }
        val password = CharArray(passwordLength) {
            val range = ranges.random(random)
            range.random(random)
        }

        "Password: ".also { binding.passwordTextView.text = it }
        binding.copyButton.visibility = View.INVISIBLE
        var currentIndex = 0
        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (currentIndex < password.size) {
                    binding.passwordTextView.append(password[currentIndex].toString())
                    currentIndex++
                    handler.postDelayed(this, 50)
                } else {
                    binding.copyButton.visibility = View.VISIBLE
                    isGenerating = false
                }
            }
        }, 50)
    }
}
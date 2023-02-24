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
import com.google.android.material.color.DynamicColors
import com.google.android.material.slider.Slider
import com.tr3ble.passwordgenerator.databinding.ActivityMainBinding
import kotlin.random.Random

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var useNumbers: Boolean = false
    private var useSpecialChars: Boolean = false

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        DynamicColors.applyToActivityIfAvailable(this)

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.passwordLengthSeekBar.addOnSliderTouchListener(object :
            Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {}

            override fun onStopTrackingTouch(slider: Slider) {
                binding.generateButton.callOnClick()
            }
        })

        binding.passwordLengthSeekBar.addOnChangeListener { _, value, _ ->
            binding.passwordLengthTextView.text = "Password length: ${value.toInt()}"
        }

        binding.generateButton.setOnClickListener {
            val random = Random
            val passwordLength = binding.passwordLengthSeekBar.value.toInt()
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

            binding.passwordTextView.text = "Password: "
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
                    }
                }
            }, 50)
        }

        binding.copyButton.setOnClickListener {
            val clipboard = ContextCompat.getSystemService(applicationContext, ClipboardManager::class.java)
            val clip = ClipData.newPlainText("Password", binding.passwordTextView.text.toString().substring(10))
            if (clipboard != null) {
                clipboard.setPrimaryClip(clip)
                Toast.makeText(this, "Password copied to clipboard", Toast.LENGTH_SHORT).show()
            }
        }

        binding.useNumbersSwitch.setOnCheckedChangeListener { _, isChecked ->
            useNumbers = isChecked
        }

        binding.useSpecialCharsSwitch.setOnCheckedChangeListener { _, isChecked ->
            useSpecialChars = isChecked
        }
    }
}
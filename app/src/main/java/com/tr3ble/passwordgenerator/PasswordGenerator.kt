package com.tr3ble.passwordgenerator
// hmm...
import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.color.DynamicColors
import com.google.android.material.slider.Slider
import com.tr3ble.passwordgenerator.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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
// я люблю чебуреки
        binding.generateButton.setOnClickListener {
            val random = Random
            val passwordLength = binding.passwordLengthSeekBar.value.toInt()
            val password = CharArray(passwordLength) {
                val ranges = listOf('a'..'z', 'A'..'Z', '0'..'9' )
                val range = ranges.random(random)
                range.random(random)
            }

            binding.passwordTextView.text = "Password: ${password.joinToString("")}"
            binding.copyButton.visibility = View.VISIBLE
        }

        binding.copyButton.setOnClickListener {
            val clipboard = ContextCompat.getSystemService(applicationContext, ClipboardManager::class.java)
            val clip = ClipData.newPlainText("Password", binding.passwordTextView.text.toString().substring(10))
            if (clipboard != null) {
                clipboard.setPrimaryClip(clip)
                Toast.makeText(this, "Password copied to clipboard", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
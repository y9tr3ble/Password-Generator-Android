package com.tr3ble.passwordgenerator
// hmm...
    import android.annotation.SuppressLint
    import android.content.ClipData
    import android.content.ClipboardManager
    import android.os.Bundle
    import android.view.View
    import android.widget.Button
    import android.widget.TextView
    import android.widget.Toast
    import androidx.appcompat.app.AppCompatActivity
    import com.google.android.material.color.DynamicColors
    import com.google.android.material.slider.Slider
    import com.tr3ble.passwordgenerator.R.*
    import kotlin.random.Random

    class MainActivity : AppCompatActivity() {

        private lateinit var passwordTextView: TextView
        private lateinit var passwordLengthSlider: Slider
        private lateinit var generateButton: Button
        private lateinit var copyButton: Button

        @SuppressLint("SetTextI18n")
        override fun onCreate(savedInstanceState: Bundle?) {
            DynamicColors.applyToActivityIfAvailable(this)
            super.onCreate(savedInstanceState)
            setContentView(layout.activity_main)

            passwordTextView = findViewById(id.password_text_view)
            passwordLengthSlider = findViewById(id.password_length_seek_bar)
            generateButton = findViewById(id.generate_button)
            copyButton = findViewById(id.copy_button)

            passwordLengthSlider.addOnChangeListener(object : Slider.OnChangeListener {
                override fun onValueChange(slider: Slider, value: Float, fromUser: Boolean) {
                    passwordTextView.text = "Password length: ${value.toInt()}"
                }
            })

            generateButton.setOnClickListener {
                generatePassword()
            }

            copyButton.setOnClickListener {
                copyPasswordToClipboard()
            }
        }

        private fun generatePassword() {
            val random = Random
            val passwordLength = passwordLengthSlider.value.toInt()
            val password = CharArray(passwordLength) {
                val ranges = listOf('a'..'z', 'A'..'Z', '0'..'9' )
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
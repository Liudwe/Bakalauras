package io.unlokk.onboarding

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.intern.databinding.ActivityLoginBinding
import com.example.intern.databinding.ActivitySignupBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        registerButton()
        returnBackButton()
    }

    fun registerButton() {
        binding.signupButton.setOnClickListener {
            val name = binding.nameEditText.text
            val password_text = binding.pswEditText.text

            app.emailPassword.registerUserAsync(name.toString(), password_text.toString()) {
                if (it.isSuccess) {
                    startActivity(Intent(this, LoginActivity::class.java))
                    Log.d("Tag", "User successfully registered")
                } else {
                    startActivity(Intent(this, LoginActivity::class.java))
                    Log.d("Tag", "Could not register user.")
                }
            }
        }
    }

    fun returnBackButton(){
        binding.backButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}

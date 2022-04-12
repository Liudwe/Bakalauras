package io.unlokk.onboarding

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.intern.databinding.ActivityLoginBinding
import com.example.intern.databinding.ActivitySignupBinding

class SignUpActivity: AppCompatActivity() {
        private lateinit var binding: ActivitySignupBinding

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivitySignupBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.signupButton.setOnClickListener {
                val password_text = binding.pswEditText.toString()
                val email_text = binding.emailEditText.text
                val email: String = email_text.toString().trim()
                val emailPattern = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")

                if (email.matches(emailPattern)) {
                    Toast.makeText(
                        getApplicationContext(),
                        "valid email address",
                        Toast.LENGTH_SHORT
                    ).show();
                } else {
                    Toast.makeText(
                        getApplicationContext(),
                        "Invalid email address",
                        Toast.LENGTH_SHORT
                    ).show();
                }

                Toast.makeText(this@SignUpActivity, email_text, Toast.LENGTH_SHORT).show()
                Log.d("Tag", email_text.toString())

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }

            binding.backButton.setOnClickListener {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

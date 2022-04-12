package io.unlokk.onboarding

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.intern.databinding.ActivityLoginBinding
import java.util.regex.Pattern

class LoginActivity: AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener{
            val password_text = binding.pswEditText.toString()
            val email_text = binding.emailEditText.text
            val email: String = email_text.toString().trim()
            val emailPattern = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")

            if (email.matches(emailPattern))
            {
                Toast.makeText(getApplicationContext(), "valid email address", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
            }

            Toast.makeText(this@LoginActivity, email_text, Toast.LENGTH_SHORT).show()
            Log.d("Tag", email_text.toString())

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

       binding.signupButton.setOnClickListener{
           val intent = Intent(this, SignUpActivity::class.java)
           startActivity(intent)
       }
    }
}
package io.unlokk.onboarding

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.intern.databinding.ActivityLoginBinding
import io.realm.mongodb.Credentials
import kotlin.math.log
import kotlin.math.sign


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        loginUserButton()
        signUpButton()
        setContentView(binding.root)
    }

    fun loginUserButton() {
        binding.loginButton.setOnClickListener {
            val password_text = binding.pswEditText.text.toString()
            val username_text = binding.usernameEditText.text.toString()

            val creds = Credentials.emailPassword(username_text, password_text)
            app.loginAsync(creds) {
                if (!it.isSuccess) {
                    Log.d("Tag", "User does not exist")
                } else {
                    Log.d("Tag", "User successfully logged on")
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
            binding.containerView.visibility = View.GONE
            binding.progress.visibility = View.VISIBLE
        }
    }

    fun signUpButton() {
        binding.signupButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}
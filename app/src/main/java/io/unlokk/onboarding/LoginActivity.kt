package io.unlokk.onboarding

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.intern.databinding.ActivityLoginBinding
import io.realm.mongodb.Credentials
import kotlin.math.log


class LoginActivity: AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener{
            val password_text = binding.pswEditText.text.toString()
            val username_text = binding.usernameEditText.text.toString()

            /*val email_text = binding.emailEditText.text.toString()
            val email: String = email_text.toString().trim()
            val emailPattern = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")

            if (email.matches(emailPattern))
            {
                Toast.makeText(getApplicationContext(), password_text, Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
            }

            Toast.makeText(this@LoginActivity, email_text, Toast.LENGTH_SHORT).show()
            Log.d("Tag", email_text)*/

            val creds = Credentials.emailPassword(username_text, password_text)
            app.loginAsync(creds) {
                if(!it.isSuccess) {
                    Log.d("Tag", "User does not exist")
                } else {
                    Log.d("Tag", "User successfully logged on")
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }

       binding.signupButton.setOnClickListener{
           val intent = Intent(this, SignUpActivity::class.java)
           startActivity(intent)
       }
    }
}
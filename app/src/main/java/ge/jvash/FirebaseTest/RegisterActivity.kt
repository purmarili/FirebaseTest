package ge.jvash.FirebaseTest

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class RegisterActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var passwordCheckEditText: EditText
    private lateinit var submit: Button

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        mAuth = FirebaseAuth.getInstance()
        init()
        registration()
    }

    private fun init() {
        emailEditText = findViewById(R.id.editTextTextEmailAddress)
        passwordEditText = findViewById(R.id.editTextTextPassword)
        passwordCheckEditText = findViewById(R.id.repeatPasswordTextView)
        submit = findViewById(R.id.submit)

    }

    private fun registration() {

        submit.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val passwordCheck = passwordCheckEditText.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Don't leave black anYThing!!!!!!!!!!!!!!", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            if (!validEmail(email)) {
                Toast.makeText(this, "Email is not VALID !!!!!!!!!!!!!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!validPassword(password)) {
                Toast.makeText(
                    this,
                    "Password must contain at least one letter and one number!!!!!!!!!!!!!",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            if (password != passwordCheck) {
                Toast.makeText(
                    this,
                    "Passwords Doesn't MATCH !!!!!!!!!!!!!!!!!!!!!!!!!!!",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            } else {
                mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { result ->
                        if (result.isSuccessful) {
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(
                                this,
                                "Authentication failed!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
            }


        }
    }

    private fun validPassword(password: String): Boolean {
        if (password.length < 8) return false
        if (password.contains(' ')) return false
        var hasDigit: Boolean = false
        var hasLetter: Boolean = false
        for (ch in password) {
            if (ch.isDigit()) hasDigit = true
            if (ch.isLetter()) hasLetter = true
        }
        return (hasDigit && hasLetter)
    }

    private fun validEmail(target: CharSequence): Boolean {
        return if (TextUtils.isEmpty(target)) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }

}
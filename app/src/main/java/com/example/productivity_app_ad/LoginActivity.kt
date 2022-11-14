package com.example.productivity_app_ad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private var emailTextView: EditText? = null
    private var passwordTextView: EditText? = null
    private var Btn: Button? = null
    private var toRegister: Button? = null
    private var progressbar: ProgressBar? = null
    private var mAuth: FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()
        if (mAuth!!.currentUser != null) {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
        }

        // initialising all views through id defined above
        emailTextView = findViewById(R.id.email)
        passwordTextView = findViewById(R.id.passwd)
        Btn = findViewById(R.id.btnlogin)
        toRegister = findViewById(R.id.btnregister)
        progressbar = findViewById(R.id.progressbar)

        // Set on Click Listener on Sign-in button
        Btn?.setOnClickListener(View.OnClickListener { loginUserAccount() })
        toRegister?.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        })
    }

    private fun loginUserAccount() {
        progressbar!!.visibility = View.VISIBLE
        val email: String
        val password: String
        val splitter = emailTextView!!.text.toString().split("\\s+").toTypedArray()
        email = splitter[0]
        password = passwordTextView!!.text.toString()
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(applicationContext, "Please enter email!", Toast.LENGTH_LONG).show()
            return
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(applicationContext, "Please enter password!", Toast.LENGTH_LONG).show()
            return
        }
        mAuth!!.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(applicationContext, "Login successful!", Toast.LENGTH_LONG).show()
                progressbar!!.visibility = View.GONE
                emailTextView!!.setText("")
                passwordTextView!!.setText("")
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(applicationContext, "Login failed!", Toast.LENGTH_LONG).show()
                progressbar!!.visibility = View.GONE
            }
        }
    }

}
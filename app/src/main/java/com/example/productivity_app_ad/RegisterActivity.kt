package com.example.productivity_app_ad

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class RegisterActivity : AppCompatActivity() {

    private var emailTextView: EditText? = null
    private var passwordTextView: EditText? = null
    private var Btn: Button? = null
    private var usernameTextView: EditText? = null
    private var toLogIn: Button? = null
    private var progressbar: ProgressBar? = null
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        mAuth = FirebaseAuth.getInstance()
        emailTextView = findViewById(R.id.email)
        passwordTextView = findViewById(R.id.passwd)
        usernameTextView = findViewById(R.id.username)
        Btn = findViewById(R.id.btnregister)
        toLogIn = findViewById(R.id.btnlogin)
        progressbar = findViewById(R.id.progressbar)
        Btn?.setOnClickListener(View.OnClickListener { registerNewUser() })
        toLogIn?.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
        })
    }

    private fun registerNewUser() {
        val username: String = usernameTextView!!.text.toString()
        //val email: String = emailTextView!!.text.toString()
        val splitter = emailTextView!!.text.toString().split("\\s+").toTypedArray()
        val email = splitter[0]
        val password: String = passwordTextView!!.text.toString()
        progressbar!!.visibility = View.VISIBLE
        if(TextUtils.isEmpty(username)){
            Toast.makeText(applicationContext, "Please enter a username!", Toast.LENGTH_LONG).show()
            return
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(applicationContext, "Please enter email!", Toast.LENGTH_LONG).show()
            return
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(applicationContext, "Please enter password!", Toast.LENGTH_LONG).show()
            return
        }
        mAuth!!.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(applicationContext, "Beep Boop You're In!", Toast.LENGTH_LONG).show()
                progressbar!!.visibility = View.GONE
                val user = mAuth!!.currentUser
                val profileUpdates = UserProfileChangeRequest.Builder().setDisplayName(username).build()
                user!!.updateProfile(profileUpdates)
                usernameTextView!!.setText("")
                emailTextView!!.setText("")
                passwordTextView!!.setText("")
                val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(
                    applicationContext,
                    "OOOOoooof Registration Failed!" + " Try again Later",
                    Toast.LENGTH_LONG
                ).show()
                progressbar!!.visibility = View.GONE
            }
        }
    }


}
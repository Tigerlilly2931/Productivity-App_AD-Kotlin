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
import com.google.firebase.auth.UserProfileChangeRequest
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private var emailTextView: EditText? = null
    private var passwordTextView: EditText? = null
    private var Btn: Button? = null
    private var toLogIn: Button? = null
    private var progressbar: ProgressBar? = null
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        mAuth = FirebaseAuth.getInstance()
        emailTextView = findViewById(R.id.email)
        passwordTextView = findViewById(R.id.passwd)
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
        val email: String
        val password: String
        val splitter = emailTextView!!.text.toString().split("\\s+").toTypedArray()
        email = splitter[0]
        password = passwordTextView!!.text.toString()
        progressbar!!.visibility = View.VISIBLE
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
                val split = email.split("@").toTypedArray()
                val name =
                    split[0].substring(0, 1).uppercase(Locale.getDefault()) + split[0].substring(1)
                        .lowercase(
                            Locale.getDefault()
                        )
                progressbar!!.visibility = View.GONE
                val user = mAuth!!.currentUser
                val profileUpdates = UserProfileChangeRequest.Builder().setDisplayName(name).build()
                user!!.updateProfile(profileUpdates)
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
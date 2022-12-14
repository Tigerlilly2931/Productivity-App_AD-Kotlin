package com.example.productivity_app_ad

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.database.FirebaseListAdapter
import com.firebase.ui.database.FirebaseListOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*


class MainActivity : AppCompatActivity() {
    private var adapter: FirebaseListAdapter<Checklist>? = null
    private var options: FirebaseListOptions<Checklist>? = null

    //private val checkBoxList: MutableList<String>? = null

    private val stuffs: TextView? = null
    private var fabb: FloatingActionButton? = null
    private var currentUser: FirebaseUser? = null
    private var mAuth: FirebaseAuth? = null
    private var userName: String? = null
    private var userNameHolder: TextView? = null
    //private var checkboxThing: CheckBox? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()
        currentUser = mAuth!!.currentUser
        userName = mAuth!!.currentUser!!.displayName
        userNameHolder = findViewById(R.id.UsernameHolder)
        //checkboxThing = findViewById(R.id.chechbox)
        var strHold: String? = "Welcome $userName"
        userNameHolder?.text = strHold
        if (currentUser == null) {
            val intent = Intent(this@MainActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
        fabb = findViewById(R.id.addThing)
        //stuffs = findViewById(R.id.textView);
        //stuffs.setText("Welcome! Thanks!");
        fabb?.setOnClickListener(View.OnClickListener {
            val input = findViewById<View>(R.id.input) as EditText

            // Read the input field and push a new instance
            // of Checklist to the Firebase database
            FirebaseDatabase.getInstance()
                .reference
                .push()
                .setValue(Checklist(input.text.toString(), userName)
                )

            // Clear the input
            input.setText("")
        })

        //Toast.makeText(getApplicationContext(), currentUser.getDisplayName(), Toast.LENGTH_LONG).show();
        val listOfMessages = findViewById<View>(R.id.list_of_messages) as ListView
        options = FirebaseListOptions.Builder<Checklist>()
            .setQuery(FirebaseDatabase.getInstance().reference, Checklist::class.java).setLayout(R.layout.checklist).build()
        adapter = object : FirebaseListAdapter<Checklist>(options!!) {
            override fun populateView(v: View, model: Checklist, position: Int) {
                // Get references to the views of message.xml
                val messageText = v.findViewById<View>(R.id.message_text) as TextView
                val checkBoxWahh = v.findViewById<CheckBox>(R.id.chechbox) as CheckBox
                checkBoxWahh.setOnCheckedChangeListener { button, b ->


                    //doing remove value here clears the entire view...
                    FirebaseDatabase.getInstance().reference.removeValue()

                    Toast.makeText(this@MainActivity,b.toString(),Toast.LENGTH_SHORT).show()
                }
                if(userName.equals(model.messageUser)){
                    // Set their text
                    messageText.text = model.messageText
                }else{
                    //don't display the checklist for user

                }


            }
        }
        listOfMessages.adapter = adapter
    }

    fun onCheckboxClicked(view: View){
        if(view is CheckBox){
            val checked: Boolean = view.isChecked

        }
    }



    override fun onStart() {
        super.onStart()
        if (currentUser != null) {
            adapter!!.startListening()
        }
    }

    override fun onStop() {
        super.onStop()
        if (currentUser != null) {
            adapter!!.stopListening()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_sign_out) {
            FirebaseAuth.getInstance().signOut()
            finish()
        }
        return true
    }
}
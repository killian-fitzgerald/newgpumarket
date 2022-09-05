package com.example.gpu

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.gpu.MainActivity
import com.example.gpu.signup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    var txt_email: EditText? = null
    var txt_pass: EditText? = null
    var admi: TextView? = null
    var not: TextView? = null
    var forgot: TextView? = null
    var b: TextView? = null
    var firebaseAuth: FirebaseAuth? = null
    var reff: DatabaseReference? = null
    var progressDialog: ProgressDialog? = null
    var signin: Button? = null
    var login: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        firebaseAuth = FirebaseAuth.getInstance()
        b = findViewById(R.id.b2)
        txt_email = findViewById(R.id.id1)
        not = findViewById(R.id.notadmin)
        txt_pass = findViewById(R.id.ed2)
        login = findViewById(R.id.login)
        admi = findViewById(R.id.admin)
        signin = findViewById(R.id.b1)
        login?.setVisibility(View.INVISIBLE)
        not?.setVisibility(View.INVISIBLE)
        forgot = findViewById(R.id.forget)
        forgot?.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    applicationContext, forget_password::class.java
                )
            )
        })
        signin?.setOnClickListener(View.OnClickListener {
            val Email = txt_email?.getText().toString().trim { it <= ' ' }
            val pass = txt_pass?.getText().toString().trim { it <= ' ' }
            if (TextUtils.isEmpty(Email)) {
                Toast.makeText(this@MainActivity, "Enter Email", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (TextUtils.isEmpty(pass)) {
                Toast.makeText(this@MainActivity, "Enter Password", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            val progressDialog = ProgressDialog(this@MainActivity)
            progressDialog.setTitle("Loging In")
            progressDialog.show()
            firebaseAuth?.signInWithEmailAndPassword(Email, pass)
                ?.addOnCompleteListener(this@MainActivity) { task ->
                    if (task.isSuccessful) {
                        startActivity(Intent(applicationContext, Welcome::class.java))
                        progressDialog.dismiss()
                        Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_SHORT).show()
                        finish()
                        txt_email?.setText("")
                        txt_pass?.setText("")
                    } else {
                        progressDialog.dismiss()
                        Toast.makeText(
                            this@MainActivity,
                            "Login Fail or User not Found",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        })
        admi?.setOnClickListener(View.OnClickListener {
            login?.setVisibility(View.VISIBLE)
            signin?.setVisibility(View.INVISIBLE)
            admi?.setVisibility(View.INVISIBLE)
            not?.setVisibility(View.VISIBLE)
        })
        not?.setOnClickListener(View.OnClickListener {
            login?.setVisibility(View.INVISIBLE)
            signin?.setVisibility(View.VISIBLE)
            admi?.setVisibility(View.VISIBLE)
            not?.setVisibility(View.INVISIBLE)
        })
        b?.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    applicationContext,
                    signup::class.java
                )
            )
        })
        login?.setOnClickListener(View.OnClickListener { info() })
    }

    fun info() {
        reff = FirebaseDatabase.getInstance().reference.child("Admins")
        val adEmail = txt_email?.getText().toString().trim { it <= ' ' }
        val adPass = txt_pass?.getText().toString().trim { it <= ' ' }
        reff?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val dbmail = dataSnapshot.child("Email").value.toString()
                val dbpass = dataSnapshot.child("Password").value.toString()
                if (adEmail == dbmail && adPass == dbpass) {
                    if (TextUtils.isEmpty(adEmail)) {
                        Toast.makeText(this@MainActivity, "Enter Email", Toast.LENGTH_SHORT).show()
                        return
                    }
                    if (TextUtils.isEmpty(adPass)) {
                        Toast.makeText(this@MainActivity, "Enter Password", Toast.LENGTH_SHORT)
                            .show()
                        return
                    }
                    val progressDialog = ProgressDialog(this@MainActivity)
                    progressDialog.setTitle("Loging In")
                    progressDialog.show()
                    startActivity(Intent(applicationContext, Admin_dashboard::class.java))
                    txt_email?.setText("")
                    txt_pass?.setText("")
                    finish()
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Invalid Email or Password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
}
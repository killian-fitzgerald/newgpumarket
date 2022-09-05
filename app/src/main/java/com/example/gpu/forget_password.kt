package com.example.gpu

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.gpu.forget_password
import com.google.firebase.auth.FirebaseAuth

class forget_password : AppCompatActivity() {
    var email: EditText? = null
    var imageView1: ImageView? = null
    var resetbutton: Button? = null
    var container: ViewGroup? = null
    var goback: TextView? = null
    var textView: TextView? = null
    var progressBar: ProgressBar? = null
    var firebaseAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)
        email = findViewById(R.id.editText)
        resetbutton = findViewById(R.id.button)
        goback = findViewById(R.id.goback)
        textView = findViewById(R.id.textsuces)
        container = findViewById(R.id.linearLayout)
        progressBar = findViewById(R.id.progressBar)
        firebaseAuth = FirebaseAuth.getInstance()
        imageView1 = findViewById(R.id.icomimage)
        goback?.setOnClickListener(View.OnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        })
        resetbutton?.setOnClickListener(View.OnClickListener {
            val eemail = email?.getText().toString()
            if (TextUtils.isEmpty(eemail)) {
                Toast.makeText(this@forget_password, "Enter Your Email", Toast.LENGTH_SHORT).show()
            } else {
                textView?.setVisibility(View.GONE)
                imageView1?.setVisibility(View.VISIBLE)
                progressBar?.setVisibility(View.VISIBLE)
                firebaseAuth?.sendPasswordResetEmail(eemail)
                    ?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this@forget_password,
                                "Email Sent Successfully !",
                                Toast.LENGTH_SHORT
                            ).show()
                            imageView1?.setImageResource(R.drawable.green_email)
                            textView?.setVisibility(View.VISIBLE)
                            textView?.setTextColor(Color.parseColor("#11A10c"))
                            email?.setText("")
                            resetbutton?.setEnabled(false)
                            resetbutton?.setBackgroundColor(Color.parseColor("#b5261c"))
                            resetbutton?.setTextColor(Color.parseColor("#cccccc"))
                        } else {
                            val error = task.exception?.message
                            Toast.makeText(this@forget_password, error, Toast.LENGTH_SHORT).show()
                            textView?.setText(error)
                            textView?.setTextColor(resources.getColor(R.color.colorPrimary))
                            textView?.setVisibility(View.VISIBLE)
                        }
                        progressBar?.setVisibility(View.GONE)
                    }
            }
        })
    }
}
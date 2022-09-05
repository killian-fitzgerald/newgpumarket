package com.example.gpu

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference

class Admin_dashboard : AppCompatActivity() {
    var b1: Button? = null
    var b2: Button? = null
    private val databaseReference: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)
        b1 = findViewById(R.id.b1)
        b2 = findViewById(R.id.b2)
        b1?.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    applicationContext, Admin::class.java
                )
            )
        })
        b2?.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    applicationContext,
                    Review_Gpu::class.java
                )
            )
        })
    }
}
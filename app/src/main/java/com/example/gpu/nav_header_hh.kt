package com.example.gpu

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class nav_header_hh : AppCompatActivity() {
    var textView: TextView? = null
    var firebaseDatabase1: FirebaseDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_header_hh)
        textView = findViewById(R.id.user)
        textView?.setText("hellooo")
        val navigationView = findViewById<View?>(R.id.navigation) as NavigationView
        val headerView = navigationView.getHeaderView(0)
        val navUsername = headerView.findViewById<View?>(R.id.user) as TextView
        navUsername.text = "Your Text Here"
        firebaseDatabase1 = FirebaseDatabase.getInstance()
        val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
        val userkey = currentFirebaseUser?.getUid()
        firebaseDatabase1?.getReference("make")?.child(userkey.toString())
            ?.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val name = dataSnapshot.child("fullNaame").value.toString()
                    textView?.setText(name)
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
    }
}
package com.example.gpu

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class profile : AppCompatActivity() {
    var names: EditText? = null
    var address: EditText? = null
    var contact: EditText? = null
    var textView: TextView? = null
    var btn: Button? = null
    var firebaseDatabase1: FirebaseDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        names = findViewById(R.id.full)
        btn = findViewById(R.id.regbtn)
        address = findViewById(R.id.ad)
        contact = findViewById(R.id.contact)
        textView = findViewById(R.id.email)
        firebaseDatabase1 = FirebaseDatabase.getInstance()
        supportActionBar?.setTitle("Profile")
        val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
        val userkey = currentFirebaseUser?.getUid()
        firebaseDatabase1?.getReference("make")?.child(userkey ?: "")
            ?.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val addre = dataSnapshot.child("address").value.toString()
                    val cont = dataSnapshot.child("cell").value.toString()
                    val name = dataSnapshot.child("fullNaame").value.toString()
                    val mail = dataSnapshot.child("Email").value.toString()
                    names?.setText(name)
                    textView?.setText(mail)
                    address?.setText(addre)
                    contact?.setText(cont)
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
        btn?.setOnClickListener(View.OnClickListener {
            val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
            val userkey = currentFirebaseUser?.getUid()
            val firebaseDatabase = FirebaseDatabase.getInstance()
            val databaseReference = firebaseDatabase.getReference("make")
            val nam = names?.getText().toString()
            val cont = contact?.getText().toString()
            val a = address?.getText().toString()
            databaseReference.child(userkey ?: "").child("fullNaame").setValue(nam)
            databaseReference.child(userkey ?: "").child("cell").setValue(cont)
            databaseReference.child(userkey ?: "").child("address").setValue(a)
        })
    }
}
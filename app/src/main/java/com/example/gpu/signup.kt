package com.example.gpu

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.regex.Pattern

class signup : AppCompatActivity() {
    var txtEmail: EditText? = null
    var txtpassword: EditText? = null
    var txtcofirmpass: EditText? = null
    var fullname: EditText? = null
    var username: EditText? = null
    var address: EditText? = null
    var cell: EditText? = null
    var btn: Button? = null
    var sign: TextView? = null
    var radioButtonmale: RadioButton? = null
    var radioButtonfemale: RadioButton? = null
    var firebaseAuth: FirebaseAuth? = null
    var databaseReference: DatabaseReference? = null
    var gender: String? = ""
    var nummber = Pattern.compile(".{11,}")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        txtEmail = findViewById(R.id.email)
        txtpassword = findViewById(R.id.pass)
        txtcofirmpass = findViewById(R.id.con)
        cell = findViewById(R.id.mbl)
        address = findViewById(R.id.addresss)
        fullname = findViewById(R.id.full)
        radioButtonfemale = findViewById(R.id.female)
        radioButtonmale = findViewById(R.id.male)
        sign = findViewById(R.id.here)
        btn = findViewById(R.id.regbtn)
        databaseReference = FirebaseDatabase.getInstance().getReference("make")
        firebaseAuth = FirebaseAuth.getInstance()
        btn?.setOnClickListener(View.OnClickListener {
            val fullnamed = fullname?.getText().toString()
            val email = txtEmail?.getText().toString()
            val cellno = cell?.getText().toString()
            val add = address?.getText().toString()
            val pass = txtpassword?.getText().toString()
            val conf = txtcofirmpass?.getText().toString()
            if (radioButtonmale!!.isChecked()) {
                gender = "Male"
            }
            if (radioButtonfemale!!.isChecked()) {
                gender = "Female"
            }
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this@signup, "Enter Email", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (TextUtils.isEmpty(pass)) {
                Toast.makeText(this@signup, "Enter Password", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (TextUtils.isEmpty(conf)) {
                Toast.makeText(this@signup, "Enter Confirm Password", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (!nummber.matcher(cellno).matches()) {
                Toast.makeText(this@signup, "Please provide correct number", Toast.LENGTH_SHORT)
                    .show()
                return@OnClickListener
            }
            if (!PASSWORD_PATTERN.matcher(pass).matches()) {
                Toast.makeText(
                    this@signup,
                    "Required 6 digits combination of Alphabets(one Upper Case) and Numbers ",
                    Toast.LENGTH_SHORT
                ).show()
                return@OnClickListener
            }
            if (pass == conf && PASSWORD_PATTERN.matcher(pass).matches()) {
                val progressDialog = ProgressDialog(this@signup)
                progressDialog.setTitle("Please Wait ")
                progressDialog.show()
                firebaseAuth?.createUserWithEmailAndPassword(email, pass)
                    ?.addOnCompleteListener(this@signup) { task ->
                        if (task.isSuccessful) {
                            // create the user info object
                            val inform = make(
                                fullnamed,
                                cellno,
                                email,
                                gender,
                                add
                            )
                            // save the all user info in the firebase
                            FirebaseDatabase.getInstance().getReference("make")
                                .child(FirebaseAuth.getInstance().currentUser!!.getUid())
                                .setValue(inform).addOnCompleteListener {
                                    Toast.makeText(
                                        this@signup,
                                        "Registration Complete",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    progressDialog.dismiss()
                                    // open the welcom screen after sucessfull signup
                                    startActivity(Intent(applicationContext, Welcome::class.java))
                                    fullname?.setText("")
                                    txtEmail?.setText("")
                                    txtcofirmpass?.setText("")
                                    txtpassword?.setText("")
                                    finish()
                                }
                        } else {
                            Toast.makeText(this@signup, "Authentication Failed", Toast.LENGTH_SHORT)
                                .show()
                        }

                        // ...
                    }
            }
        })
        sign?.setOnClickListener(View.OnClickListener { // open the login screen
            startActivity(Intent(applicationContext, MainActivity::class.java))
        })
    }

    companion object {
        private val PASSWORD_PATTERN = Pattern.compile(
            "^" +
                    "(?=.*[0-9])" +  //at least 1 digit
                    "(?=.*[a-z])" +  //at least 1 lower case letter
                    "(?=.*[A-Z])" +  //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +  //any letter
                    //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +  //no white spaces
                    ".{6,}" +  //at least 6 characters
                    "$"
        )
    }
}
package com.example.gpu

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.gpu.Order_Activity
import com.example.gpu.Order_slip
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class Order_Activity : AppCompatActivity() {
    var name: TextView? = null
    var price: TextView? = null
    var quantity: TextView? = null
    var orderquantity: TextView? = null
    var orderprice: TextView? = null
    var cell: TextView? = null
    var detailpricetotal: TextView? = null
    var orderimage: ImageView? = null
    var firebaseDatabase1: FirebaseDatabase? = null
    var bmp: Bitmap? = null
    var desc: String? = null
    var temp: String? = null
    var te: String? = null
    var address: EditText? = null
    var key: String? = ""
    var placeorder: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_)
        orderimage = findViewById(R.id.orderimage)
        placeorder = findViewById(R.id.placeyourorder)
        name = findViewById(R.id.ordername)
        price = findViewById(R.id.orderprice)
        quantity = findViewById(R.id.orderquantity)
        orderquantity = findViewById(R.id.detailorderquantity)
        orderprice = findViewById(R.id.detailorderprice)
        address = findViewById(R.id.adrressorder)
        detailpricetotal = findViewById(R.id.detailordertotalprice)
        cell = findViewById(R.id.cellno)
        firebaseDatabase1 = FirebaseDatabase.getInstance()
        val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
        val userkey = currentFirebaseUser?.getUid()
        firebaseDatabase1?.getReference("make")?.child(userkey ?: "")
            ?.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val cellnumber = dataSnapshot.child("cell").value.toString()
                    val Adres = dataSnapshot.child("address").value.toString()
                    cell?.setText(cellnumber)
                    address?.setText(Adres)
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
        placeorder?.setOnClickListener(View.OnClickListener {
            val builder = AlertDialog.Builder(this@Order_Activity)
            builder.setMessage("Confirm Your Order  ?").setCancelable(false)
                .setPositiveButton("Yes") { dialog, which ->
                    information()
                    val intent = Intent(this@Order_Activity, Order_slip::class.java)
                    intent.putExtra("name", name?.getText().toString())
                    intent.putExtra("price", price?.getText().toString())
                    intent.putExtra("quantity", quantity?.getText().toString())
                    intent.putExtra("total", detailpricetotal?.getText().toString())
                    startActivity(intent)
                }
                .setNegativeButton("No") { dialog, which -> dialog.cancel() }
            val alertDialog = builder.create()
            alertDialog.show()
        })
    }

    override fun onStart() {
        price?.setText(intent.getStringExtra("price"))
        Picasso.with(this).load(intent.getStringExtra("pic")).into(orderimage)
        name?.setText(intent.getStringExtra("name"))
        desc = intent.getStringExtra("des")
        key = intent.getStringExtra("key")
        te = intent.getStringExtra("imageoforder")
        quantity?.setText(intent.getStringExtra("quantity"))
        orderquantity?.setText(intent.getStringExtra("quantity"))
        val valu = quantity?.getText().toString().toInt()
        val value = price?.getText().toString().toInt()
        val v = valu * value
        detailpricetotal?.setText(Integer.toString(v))
        orderprice?.setText(Integer.toString(v))
        super.onStart()
    }

    fun information() {
        val cartdata = cartdata(
            te,
            name?.getText().toString(),
            quantity?.getText().toString(),
            price?.getText().toString(),
            detailpricetotal?.getText().toString(),
            desc,
            getCurrentDataTime()
        )
        val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
        val userkey = currentFirebaseUser?.getUid()
        val dref =
            FirebaseDatabase.getInstance().getReference("make").child(userkey ?: "")
                .child("myorders")
        val s = dref.push().key
        dref.child(s ?: "").setValue(cartdata)
    }

    fun getCurrentDataTime(): String? {
        val today = Date()
        val format =
            SimpleDateFormat("yyyy-MM-dd hh:mm:ss a")
        return format.format(today)
    }
}
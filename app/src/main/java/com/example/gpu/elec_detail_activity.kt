package com.example.gpu

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.gpu.elec_detail_activity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class elec_detail_activity : AppCompatActivity() {
    var spinner: Spinner? = null
    var imageView: ImageView? = null
    var name: TextView? = null
    var price: TextView? = null
    var description: TextView? = null
    var detail: TextView? = null
    var n: String? = null
    var img: String? = null
    var picss: Picasso? = null
    var imageipload: String? = null
    var key: String? = ""
    var quantity: Array<String?>? = null
    var shop: Button? = null
    var cart: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_elec_detail_activity)
        shop = findViewById(R.id.continueshopping)
        cart = findViewById(R.id.addtocart)
        spinner = findViewById(R.id.spinner)
        imageView = findViewById(R.id.picofelecitem)
        name = findViewById(R.id.nameofelectime)
        detail = findViewById(R.id.priceafterquantity)
        price = findViewById(R.id.priceofelecitems)
        description = findViewById(R.id.descriptionofelecitem)
        quantity = resources.getStringArray(R.array.itemquantity)
        val adapter: ArrayAdapter<*> =
            ArrayAdapter<Any?>(this, android.R.layout.simple_spinner_item, quantity!!)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner?.setAdapter(adapter)
        spinner?.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                n = quantity?.get(position)
                val valu = n?.toDouble()
                val value = price?.getText().toString().toDouble()
                val v = valu?.times(value)
                detail?.setText(v.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
        val bundle = intent.extras
        if (bundle != null) {
            description?.setText(bundle.getString("description"))
            img = bundle.getString("image")
            price?.setText(bundle.getString("price"))
            name?.setText(bundle.getString("name"))
            key = bundle.getString("keyvalue")
            Picasso.with(this@elec_detail_activity).load(img).into(imageView)
            cart?.setOnClickListener(View.OnClickListener {
                val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
                //                    final String userkey=currentFirebaseUser.getUid();
                imageipload = bundle.getString("image")
                if (currentFirebaseUser == null) {
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                } else information()
                Toast.makeText(this@elec_detail_activity, "Item Added to Cart", Toast.LENGTH_SHORT)
                    .show()
            })
            shop?.setOnClickListener(View.OnClickListener {
                val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
                //                    final String userkey=currentFirebaseUser.getUid();
                if (currentFirebaseUser == null) {
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                } else {
                    val intent = Intent(this@elec_detail_activity, Order_Activity::class.java)
                    intent.putExtra("price", price?.getText().toString())
                    intent.putExtra("name", name?.getText().toString())
                    intent.putExtra("key", key)
                    intent.putExtra("imageoforder", img)
                    intent.putExtra("pic", getIntent().getStringExtra("image"))
                    intent.putExtra("des", description?.getText().toString())
                    intent.putExtra("quantity", n)
                    intent.putExtra("total", detail?.getText().toString())
                    startActivity(intent)
                }
            })
        }
    }

    fun information() {
        val progressDialog = ProgressDialog(this@elec_detail_activity)
        progressDialog.setTitle("Uploading Item")
        progressDialog.show()
        val cartdata = cartdata(
            imageipload,
            name?.getText().toString(),
            n,
            price?.getText().toString(),
            detail?.getText().toString(),
            description?.getText().toString(),
            getCurrentDataTime()
        )
        val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
        val userkey = currentFirebaseUser?.getUid()
        val dref =
            FirebaseDatabase.getInstance().getReference("make").child(userkey.toString())
                .child("mycart")
        val s = dref.push().key
        dref.child(s.toString()).setValue(cartdata)
        progressDialog.dismiss()
    }

    fun getCurrentDataTime(): String? {
        val today = Date()
        val format =
            SimpleDateFormat("yyyy-MM-dd hh:mm:ss a")
        return format.format(today)
    }
}
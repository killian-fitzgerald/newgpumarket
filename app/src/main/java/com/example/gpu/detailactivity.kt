package com.example.gpu

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

class detailactivity : AppCompatActivity() {
    var spinner: Spinner? = null
    var imageView: ImageView? = null
    var name: TextView? = null
    var price: TextView? = null
    var description: TextView? = null
    var detail: TextView? = null
    var n: String? = null
    var img: String? = null
    var imageipload: String? = null
    var picss: Picasso? = null
    var shop: Button? = null
    var cart: Button? = null
    var quantity: Array<String?>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailactivity)
        spinner = findViewById(R.id.spinner)
        imageView = findViewById(R.id.picofitem)
        cart = findViewById(R.id.addtocart)
        name = findViewById(R.id.nameoftime)
        shop = findViewById(R.id.continueshopping)
        detail = findViewById(R.id.priceafterquantity)
        price = findViewById(R.id.priceofitems)
        description = findViewById(R.id.descriptionofitem)
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
                detail?.setText(java.lang.Double.toString(v!!))
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
        val bundle = intent.extras
        if (bundle != null) {
            description?.setText(bundle.getString("description"))
            img = bundle.getString("image")
            price?.setText(bundle.getString("price"))
            name?.setText(bundle.getString("name"))
            Picasso.with(this@detailactivity).load(img).into(imageView)
        }
        cart?.setOnClickListener(View.OnClickListener {
            val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
            imageipload = bundle?.getString("image")
            // if user is not login first have to login
            if (currentFirebaseUser == null) {
                startActivity(Intent(applicationContext, MainActivity::class.java))
            } else  // call the function to add item to the cart
                information()
            Toast.makeText(this@detailactivity, "Item Added to Cart", Toast.LENGTH_SHORT).show()
        })
        shop?.setOnClickListener(View.OnClickListener {
            val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
            //                    final String userkey=currentFirebaseUser.getUid();
            if (currentFirebaseUser == null) {
                startActivity(Intent(applicationContext, MainActivity::class.java))
            } else {

// open the order screen and pass the data using intent
                val intent = Intent(this@detailactivity, Order_Activity::class.java)
                intent.putExtra("price", price?.getText())
                intent.putExtra("name", name?.getText())
                val drawable = imageView?.getDrawable()
                val bitmap = (drawable as BitmapDrawable).bitmap
                val baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
                val b = baos.toByteArray()
                intent.putExtra("imageoforder", img)
                intent.putExtra("pic", b)
                intent.putExtra("des", description?.getText())
                intent.putExtra("quantity", n)
                startActivity(intent)
            }
        })
    }

    fun information() {
        val progressDialog = ProgressDialog(this@detailactivity)
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

        // update the cart info
        val dref =
            FirebaseDatabase.getInstance().getReference("make").child(userkey ?: "").child("mycart")
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
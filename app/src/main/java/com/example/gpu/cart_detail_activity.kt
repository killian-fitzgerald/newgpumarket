package com.example.gpu

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.gpu.cart_detail_activity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream

class cart_detail_activity : AppCompatActivity() {
    var spinner: Spinner? = null
    var imageView: ImageView? = null
    var name: TextView? = null
    var price: TextView? = null
    var description: TextView? = null
    var detailss: TextView? = null
    var n: String? = null
    var m: String? = null
    var img: String? = null
    var picss: Picasso? = null
    var imageipload: String? = null
    var key: String? = ""
    var shopping: Button? = null
    var cart: Button? = null
    var button: Button? = null
    var intent = null
    var quantity: Array<String?>? = null
    var context: Context? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_detail_activity)
        cart = findViewById(R.id.addtocart)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        button = findViewById(R.id.delete)
        spinner = findViewById(R.id.spinner)
        imageView = findViewById(R.id.picofclothitem)
        name = findViewById(R.id.nameofclothtime)
        detailss = findViewById(R.id.priceafterquantity)
        price = findViewById(R.id.priceofclothitems)
        description = findViewById(R.id.descriptionofclothitem)
        quantity = resources.getStringArray(R.array.itemquantity)
        shopping = findViewById(R.id.continueshoppingcloth)
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
                val valu = n?.toInt()
                val value = price?.getText().toString().toInt()
                val v = valu?.times(value)
                detailss?.setText(v.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
        val bundle = getIntent().extras
        if (bundle != null) {
            description?.setText(bundle.getString("description"))
            img = bundle.getString("image")
            price?.setText(bundle.getString("price"))
            name?.setText(bundle.getString("name"))
            key = bundle.getString("keyvalue")
            Picasso.with(context).load(img).into(imageView)
            m = bundle.getString("quantity")
            val value = m?.toInt()
            val valuee = value?.minus(1)
            spinner?.setSelection(valuee ?: 0)
            val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
            val userkey = currentFirebaseUser?.getUid()
            button?.setOnClickListener(View.OnClickListener { // delete item to the cart
                val firebaseDatabase1 =
                    FirebaseDatabase.getInstance().getReference("make").child(userkey.toString())
                        .child("mycart")
                firebaseDatabase1.child(key.toString()).removeValue()
                Toast.makeText(context, "Deleted From Cart", Toast.LENGTH_SHORT).show()
                startActivity(Intent(applicationContext, MyCart::class.java))
            })
            shopping?.setOnClickListener(View.OnClickListener {
                val intent = Intent(this@cart_detail_activity, cart_order_activity::class.java)
                intent.putExtra("price", price?.getText())
                intent.putExtra("name", name?.getText())
                intent.putExtra("key", key)
                intent.putExtra("imageoforder", img)
                val drawable = imageView?.getDrawable()
                val bitmap = (drawable as BitmapDrawable).bitmap
                val baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
                val b = baos.toByteArray()
                intent.putExtra("pic", b)
                intent.putExtra("total", detailss?.getText())
                intent.putExtra("des", description?.getText())
                intent.putExtra("quantity", n)
                startActivity(intent)
            })
        }
    }
}
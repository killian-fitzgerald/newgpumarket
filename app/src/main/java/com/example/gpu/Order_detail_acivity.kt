package com.example.gpu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.gpu.Order_detail_acivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class Order_detail_acivity : AppCompatActivity() {
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
        setContentView(R.layout.activity_order_detail_acivity)
        cart = findViewById(R.id.addtocart)
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
            ArrayAdapter<Any>(this, android.R.layout.simple_spinner_item, quantity!!)
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
            button?.setOnClickListener(View.OnClickListener {
                val firebaseDatabase1 =
                    FirebaseDatabase.getInstance().getReference("make").child(userkey.toString())
                        .child("myorders")
                firebaseDatabase1.child(key.toString()).removeValue()
                startActivity(Intent(applicationContext, My_order::class.java))
                Toast.makeText(context, "Order Record Deleted", Toast.LENGTH_SHORT).show()
            })
            shopping?.setOnClickListener(View.OnClickListener {
                val intent = Intent(this@Order_detail_acivity, Order_Activity::class.java)
                intent.putExtra("price", price?.getText())
                intent.putExtra("name", name?.getText())
                intent.putExtra("key", key)
                intent.putExtra("pic", getIntent().getStringExtra("image"))
                intent.putExtra("total", detailss?.getText())
                intent.putExtra("des", description?.getText())
                intent.putExtra("quantity", n)
                startActivity(intent)
            })
        }
    }
}
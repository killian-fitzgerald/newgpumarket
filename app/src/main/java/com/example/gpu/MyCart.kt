package com.example.gpu

import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gpu.MyCart
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*

class MyCart : AppCompatActivity() {
    var recyclerView: RecyclerView? = null
    var cartdataList: MutableList<cartdata?>? = null
    private var databaseReference: DatabaseReference? = null
    private var eventListener: ValueEventListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_cart)
        supportActionBar?.setTitle("My Cart")
        val progressDialog = ProgressDialog(this@MyCart)
        progressDialog.setMessage("Loading Items")
        recyclerView = findViewById(R.id.cartrecycler)
        val gridLayoutManager = GridLayoutManager(this, 1)
        recyclerView?.setLayoutManager(gridLayoutManager)
        val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
        val userkey = currentFirebaseUser?.getUid()
        cartdataList = ArrayList()
        val cart_adapter = Cart_Adapter(cartdataList, this)
        recyclerView?.setAdapter(cart_adapter)
        databaseReference =
            FirebaseDatabase.getInstance().getReference("make").child(userkey ?: "").child("mycart")
        progressDialog.show()
        eventListener = databaseReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                cartdataList?.clear()
                for (dataSnapshot1 in dataSnapshot.children) {
                    val cartdata = dataSnapshot1.getValue(cartdata::class.java)
                    cartdata?.setKey(dataSnapshot1.key)
                    cartdataList?.add(cartdata)
                }
                cart_adapter.notifyDataSetChanged()
                progressDialog.dismiss()
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
}
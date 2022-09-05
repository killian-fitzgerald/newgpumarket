package com.example.gpu

import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gpu.My_order
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*

class My_order : AppCompatActivity() {
    var recyclerView: RecyclerView? = null
    var cartdataList: MutableList<cartdata?>? = null
    private var databaseReference: DatabaseReference? = null
    private var eventListener: ValueEventListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_order)
        supportActionBar?.setTitle("My Orders")
        val progressDialog = ProgressDialog(this@My_order)
        progressDialog.setMessage("Loading Items")
        recyclerView = findViewById(R.id.orderrecycler)
        val gridLayoutManager = GridLayoutManager(this, 1)
        recyclerView?.setLayoutManager(gridLayoutManager)
        val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
        val userkey = currentFirebaseUser?.getUid()
        cartdataList = ArrayList()
        val order_adapter = Order_Adapter(cartdataList, this)
        recyclerView?.setAdapter(order_adapter)
        databaseReference =
            FirebaseDatabase.getInstance().getReference("make").child(userkey ?: "")
                .child("myorders")
        progressDialog.show()
        eventListener = databaseReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                cartdataList?.clear()
                for (dataSnapshot1 in dataSnapshot.children) {
                    val cartdata = dataSnapshot1.getValue(cartdata::class.java)
                    cartdata?.setKey(dataSnapshot1.key)
                    cartdataList?.add(cartdata)
                }
                order_adapter.notifyDataSetChanged()
                progressDialog.dismiss()
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
}
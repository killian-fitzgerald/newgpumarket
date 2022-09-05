package com.example.gpu

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import java.util.*

class Review_Gpu : AppCompatActivity() {
    var recyclerView: RecyclerView? = null
    var list1: MutableList<data?>? = null
    var editText: EditText? = null
    private var databaseReference: DatabaseReference? = null
    private var eventListener: ValueEventListener? = null
    internal var gpu_recycler: Review_Elec_Recycler? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_gpu)
        recyclerView = findViewById(R.id.reviewelectronics)
        editText = findViewById(R.id.search)
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading Items")
        val gridLayoutManager = GridLayoutManager(this, 2)
        recyclerView?.setLayoutManager(gridLayoutManager)
        list1 = ArrayList()
        gpu_recycler = Review_Elec_Recycler(list1, this)
        recyclerView?.setAdapter(gpu_recycler)
        databaseReference = FirebaseDatabase.getInstance().getReference("GPU")
        progressDialog.show()
        eventListener = databaseReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                list1?.clear()
                for (dataSnapshot1 in dataSnapshot.children) {
                    val data = dataSnapshot1.getValue(data::class.java)
                    data?.setKey(dataSnapshot1.key)
                    list1?.add(data)
                }
                gpu_recycler!!.notifyDataSetChanged()
                progressDialog.dismiss()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                progressDialog.dismiss()
            }
        })
    }
}
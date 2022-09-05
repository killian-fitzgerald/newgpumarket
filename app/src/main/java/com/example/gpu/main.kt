package com.example.gpu

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.firebase.database.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class main : Fragment() {
    var toolbar: MaterialToolbar? = null
    var textView: TextView? = null
    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null
    var recyclerView: RecyclerView? = null
    var list: MutableList<data?>? = null
    var list1: MutableList<data?>? = null
    private var databaseReference: DatabaseReference? = null
    private var eventListener: ValueEventListener? = null
    var bottomNavigationView: BottomNavigationView? = null
    var drawerLayout: DrawerLayout? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_main, container, false)
        textView = v.findViewById(R.id.category)
        bottomNavigationView = v.findViewById(R.id.bottomnav)
        toolbar = v.findViewById(R.id.toolbar)
        viewPager = v.findViewById(R.id.viewPager)
        val viewPagerAdapter = Slider_Adapter(context)
        viewPager?.setAdapter(viewPagerAdapter)
        val progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Loading Items")
        (activity as AppCompatActivity?)?.setSupportActionBar(toolbar)
        val timer = Timer()
        timer.scheduleAtFixedRate(mytimertask(), 4000, 4000)
        recyclerView = v.findViewById(R.id.mainrecycler)
        val gridLayoutManager = GridLayoutManager(context, 2)
        recyclerView?.setLayoutManager(
            LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )
        list = ArrayList()
        val main_recycler = Main_Recycler(list, context)
        recyclerView?.setAdapter(main_recycler)
        databaseReference = FirebaseDatabase.getInstance().getReference("GPU")
        progressDialog.show()
        eventListener = databaseReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                list?.clear()
                for (dataSnapshot1 in dataSnapshot.children) {
                    val data = dataSnapshot1.getValue(data::class.java)
                    list?.add(data)
                }
                main_recycler.notifyDataSetChanged()
                progressDialog.dismiss()
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
        return v
    }

    inner class mytimertask : TimerTask() {
        override fun run() {
            val mHandler = Handler(Looper.getMainLooper())
            mHandler.post {
                if (viewPager!!.getCurrentItem() == 0) {
                    viewPager!!.setCurrentItem(1)
                } else if (viewPager!!.getCurrentItem() == 1) {
                    viewPager!!.setCurrentItem(2)
                } else viewPager!!.setCurrentItem(0)
            }
        }
    }
}
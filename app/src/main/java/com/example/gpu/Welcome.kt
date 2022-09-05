package com.example.gpu

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Welcome : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    var toolbar: MaterialToolbar? = null
    var drawerLayout: DrawerLayout? = null
    var firebaseDatabase1: FirebaseDatabase? = null
    var b: BottomNavigationView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        b = findViewById(R.id.bottomnav)
        toolbar = findViewById(R.id.toolbar)
        val navigationView = findViewById<NavigationView?>(R.id.navigation)
        val headerView = navigationView.getHeaderView(0)
        val navUsername = headerView.findViewById<View?>(R.id.user) as TextView
        firebaseDatabase1 = FirebaseDatabase.getInstance()
        val currentFirebaseUser3 = FirebaseAuth.getInstance().currentUser
        if (currentFirebaseUser3 == null) {
            navUsername.text = "Not Signed In"
        } else {
            val userkey = currentFirebaseUser3.uid
            firebaseDatabase1?.getReference("make")?.child(userkey)
                ?.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val name = dataSnapshot.child("fullNaame").value.toString()
                        navUsername.text = name
                    }

                    override fun onCancelled(databaseError: DatabaseError) {}
                })
        }
        navigationView.setNavigationItemSelectedListener(this)
        setSupportActionBar(toolbar)
        drawerLayout = findViewById(R.id.drawe_layout)
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout?.addDrawerListener(toggle)
        toggle.syncState()
        supportFragmentManager.beginTransaction().replace(R.id.fragment, main()).commit()
        b?.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            var selectedFragment: Fragment? = null
            when (menuItem.itemId) {
                R.id.action_home -> selectedFragment = main()
                R.id.action_elec -> selectedFragment = BlankFragment()
                R.id.mycarttttttt -> startActivity(Intent(applicationContext, MyCart::class.java))
            }
            if (selectedFragment != null) {
                supportFragmentManager.beginTransaction().replace(R.id.fragment, selectedFragment)
                    .commit()
            }
            true
        })
    }

    override fun onBackPressed() {
        if (drawerLayout!!.isDrawerOpen(GravityCompat.START)) {
            drawerLayout!!.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.about -> startActivity(Intent(applicationContext, About::class.java))
            R.id.mycarttttttt -> {
                val currentFirebaseUser2 = FirebaseAuth.getInstance().currentUser
                if (currentFirebaseUser2 == null) {
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                } else startActivity(Intent(applicationContext, MyCart::class.java))
            }
            R.id.myorder -> {
                val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
                if (currentFirebaseUser == null) {
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                } else startActivity(Intent(applicationContext, My_order::class.java))
            }
            R.id.lohouttt -> {
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
            }
            R.id.profile -> {
                val currentFirebaseUser1 = FirebaseAuth.getInstance().currentUser
                if (currentFirebaseUser1 == null) {
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                } else startActivity(Intent(applicationContext, profile::class.java))
            }
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.getItemId()
        if (id == R.id.action_shoes) {
            val currentFirebaseUser1 = FirebaseAuth.getInstance().currentUser
            if (currentFirebaseUser1 == null) {
                startActivity(Intent(applicationContext, MainActivity::class.java))
                Toast.makeText(this, "Please Login First", Toast.LENGTH_SHORT).show()
            } else startActivity(Intent(applicationContext, MyCart::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
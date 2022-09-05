package com.example.gpu

import com.google.android.material.appbar.MaterialToolbar
import android.widget.TextView
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.recyclerview.widget.RecyclerView
import com.example.gpu.data
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.drawerlayout.widget.DrawerLayout
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import com.example.gpu.R
import com.example.gpu.Slider_Adapter
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.gpu.main.mytimertask
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gpu.Main_Recycler
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import android.os.Looper
import android.widget.EditText
import android.content.Intent
import android.app.Activity
import android.widget.Toast
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.FirebaseStorage
import com.google.android.gms.tasks.OnSuccessListener
import com.example.gpu.cartdata
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.FirebaseAuth
import com.example.gpu.Cart_Adapter
import android.widget.RadioButton
import android.text.TextUtils
import com.example.gpu.signup
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.example.gpu.make
import com.example.gpu.Welcome
import com.example.gpu.MainActivity
import com.google.android.material.navigation.NavigationView
import com.example.gpu.main
import com.example.gpu.BlankFragment
import com.example.gpu.MyCart
import androidx.core.view.GravityCompat
import com.example.gpu.About
import com.example.gpu.My_order
import com.example.gpu.profile
import com.example.gpu.Order_Adapter
import android.graphics.Bitmap
import android.widget.RelativeLayout
import com.example.gpu.NotificationService.MyNotificationPublisher
import android.app.PendingIntent
import android.app.AlarmManager
import com.example.gpu.Order_slip
import com.example.gpu.Review_Elec_Recycler
import com.example.gpu.cart
import com.squareup.picasso.Picasso
import com.example.gpu.cart_detail_activity
import androidx.cardview.widget.CardView
import com.example.gpu.forget_password
import com.example.gpu.Admin_dashboard
import com.example.gpu.Elec_Recycler
import android.text.TextWatcher
import android.text.Editable
import com.example.gpu.electronics
import com.example.gpu.elec_detail_activity
import android.view.animation.ScaleAnimation
import android.view.animation.Animation
import com.example.gpu.electronicsmain
import com.example.gpu.oder
import com.example.gpu.Order_detail_acivity
import android.widget.Spinner
import android.widget.ArrayAdapter
import android.widget.AdapterView
import com.example.gpu.Order_Activity
import android.graphics.drawable.Drawable
import android.graphics.drawable.BitmapDrawable
import android.content.BroadcastReceiver
import android.app.NotificationManager
import android.app.NotificationChannel
import android.content.DialogInterface
import androidx.viewpager.widget.PagerAdapter
import com.example.gpu.Admin
import com.example.gpu.Review_Gpu
import android.widget.ProgressBar
import android.graphics.BitmapFactory
import com.example.gpu.cart_order_activity
import com.example.gpu.electronicss
import com.example.gpu.Review_electronics_detail
import org.junit.Assert
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        Assert.assertEquals(4, (2 + 2).toLong())
    }
}
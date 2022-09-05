package com.example.gpu

import android.app.*
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.os.Environment
import android.os.SystemClock
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.gpu.NotificationService.MyNotificationPublisher
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class Order_slip : AppCompatActivity() {
    var button: Button? = null
    var imageView: ImageView? = null
    var slipname: TextView? = null
    var orderprice: TextView? = null
    var quantityoforder: TextView? = null
    var totalpriceof: TextView? = null
    var cell: TextView? = null
    var adress: TextView? = null
    var firebaseDatabase1: FirebaseDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_slip)
        button = findViewById(R.id.saveyourslip)
        imageView = findViewById(R.id.happy)
        slipname = findViewById(R.id.slipname)
        orderprice = findViewById(R.id.orderprice)
        cell = findViewById(R.id.cell)
        adress = findViewById(R.id.adress)
        quantityoforder = findViewById(R.id.quantityoforder)
        totalpriceof = findViewById(R.id.totalpriceof)
        supportActionBar?.setTitle("Order Slip")
        val bundle = intent.extras
        val mIntent = intent
        if (bundle != null) {
            slipname?.setText(bundle.getString("name"))
            orderprice?.setText(bundle.getString("price"))
            quantityoforder?.setText(bundle.getString("quantity"))
            totalpriceof?.setText(bundle.getString("total"))
        }
        val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
        val userkey = currentFirebaseUser?.getUid()
        firebaseDatabase1 = FirebaseDatabase.getInstance()
        firebaseDatabase1?.getReference("make")?.child(userkey.toString())
            ?.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val cellnumber = dataSnapshot.child("cell").value.toString()
                    val Adres = dataSnapshot.child("address").value.toString()
                    cell?.setText(cellnumber)
                    adress?.setText(Adres)
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
        button?.setOnClickListener(View.OnClickListener {
            val relativeLayout = findViewById<RelativeLayout?>(R.id.main)
            relativeLayout.post {
                // call the screen shot function
                val b = takeScreenShot(relativeLayout)
                try {
                    if (b != null) {
                        // call the notification function
                        scheduleNotification(
                            getNotification("Thank for using our app your order will be delivered"),
                            5000
                        )
                        // call the save screen function
                        SaveSreenShoot(b)
                        Toast.makeText(
                            this@Order_slip,
                            "Check your \n slip in internal storage",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })
    }

    private fun takeScreenShot(v: View?): Bitmap? {
        var screen: Bitmap? = null
        try {
            val width = v?.getMeasuredWidth()
            val hight = v?.getMeasuredHeight()
            screen = Bitmap.createBitmap(width!!, hight!!, Bitmap.Config.ARGB_8888)
            val c = Canvas(screen)
            v.draw(c)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return screen
    }

    private fun SaveSreenShoot(b: Bitmap?) {
        var bao: ByteArrayOutputStream? = null
        var file: File? = null
        try {
            bao = ByteArrayOutputStream()
            b?.compress(Bitmap.CompressFormat.PNG, 40, bao)
            file = File(
                Environment.getExternalStorageDirectory()
                    .toString() + File.separator + "Patient Slip.png"
            )
            file.createNewFile()
            val f = FileOutputStream(file)
            f.write(bao.toByteArray())
            f.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun scheduleNotification(notification: Notification?, delay: Int) {
        val notificationIntent = Intent(this, MyNotificationPublisher::class.java)
        notificationIntent.putExtra(MyNotificationPublisher.Companion.NOTIFICATION_ID, 1)
        notificationIntent.putExtra(MyNotificationPublisher.Companion.NOTIFICATION, notification)
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val futureInMillis = SystemClock.elapsedRealtime() + delay
        val alarmManager = (getSystemService(ALARM_SERVICE) as AlarmManager)!!
        alarmManager[AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis] = pendingIntent
    }

    private fun getNotification(content: String?): Notification? {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val resultIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
        val builder = NotificationCompat.Builder(this, default_notification_channel_id.toString())
        builder.setContentTitle("Scheduled Notification")
        builder.setContentText(content)
        builder.setSmallIcon(R.drawable.ic_launcher_foreground)
        builder.setAutoCancel(true)
        builder.setChannelId(NOTIFICATION_CHANNEL_ID.toString())
        builder.setContentIntent(resultIntent)
        return builder.build()
    }

    companion object {
        val NOTIFICATION_CHANNEL_ID: String? = "10001"
        private val default_notification_channel_id: String? = "default"
    }
}
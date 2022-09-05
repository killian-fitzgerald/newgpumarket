package com.example.gpu

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.gpu.Review_electronics_detail
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class Review_electronics_detail : AppCompatActivity() {
    var don: ImageView? = null
    var description: EditText? = null
    var price: EditText? = null
    var model: EditText? = null
    var discount: EditText? = null
    var button: Button? = null
    var btn: Button? = null
    var img: String? = null
    var key: String? = null
    var u: String? = "i"
    var uri: Uri? = null
    var n: Int? = 1
    var Imageurl: String? = null
    var picss: Picasso? = null
    var myspinner: Spinner? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_electronics_detail)
        discount = findViewById(R.id.discount)
        don = findViewById(R.id.imagennnn)
        description = findViewById(R.id.description)
        price = findViewById(R.id.price)
        button = findViewById(R.id.updateitem)
        model = findViewById(R.id.model)
        btn = findViewById(R.id.deleteitem)
        val bundle = intent.extras
        if (bundle != null) {
            description?.setText(bundle.getString("description"))
            img = bundle.getString("image")
            price?.setText(bundle.getString("price"))
            model?.setText(bundle.getString("name"))
            discount?.setText(bundle.getString("discount"))
            Picasso.with(this@Review_electronics_detail).load(img).into(don)
            key = bundle.getString("key")
        }
        don?.setOnClickListener(View.OnClickListener {
            val photopicker = Intent(Intent.ACTION_PICK)
            photopicker.type = "image/*"
            startActivityForResult(photopicker, 1)
        })
        btn?.setOnClickListener(View.OnClickListener {
            val databaseReference = FirebaseDatabase.getInstance().reference.child("GPU")
            databaseReference.child(key.toString()).removeValue()
            Toast.makeText(this@Review_electronics_detail, "Item Deleted", Toast.LENGTH_SHORT)
                .show()
            startActivity(Intent(applicationContext, Review_Gpu::class.java))
        })
        button?.setOnClickListener(View.OnClickListener {
            if (Imageurl == null) {
                data()
            } else if (Imageurl != null) {
                image()
            }
        })
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            uri = data?.getData()
            don?.setImageURI(uri)
            Imageurl = u
        } else {
            Toast.makeText(this@Review_electronics_detail, "Image not Picked", Toast.LENGTH_SHORT)
                .show()
        }
    }

    fun image() {
        val storageReference =
            FirebaseStorage.getInstance().reference.child("pics")
                .child(uri?.getLastPathSegment().toString())
        storageReference.putFile(uri!!).addOnSuccessListener { taskSnapshot ->
            val uriTask = taskSnapshot.storage.downloadUrl
            while (!uriTask.isComplete);
            val urlimage = uriTask.result
            Imageurl = urlimage.toString()
            upload()
        }
    }

    fun upload() {
        val progressDialog = ProgressDialog(this@Review_electronics_detail)
        progressDialog.setMessage("Updating")
        progressDialog.show()
        val firebaseDatabase = FirebaseDatabase.getInstance()
        val databaseReference = firebaseDatabase.getReference("GPU")
        val name = model?.getText().toString()
        val des = description?.getText().toString()
        val dis = discount?.getText().toString()
        val pri = price?.getText().toString()
        databaseReference.child(key.toString()).child("description").setValue(des)
        databaseReference.child(key.toString()).child("price").setValue(pri)
        databaseReference.child(key.toString()).child("name").setValue(name)
        databaseReference.child(key.toString()).child("image").setValue(Imageurl)
        databaseReference.child(key.toString()).child("discount").setValue(dis)
        Toast.makeText(this@Review_electronics_detail, "Item Updated", Toast.LENGTH_SHORT).show()
        progressDialog.dismiss()
    }

    fun data() {
        val firebaseDatabase = FirebaseDatabase.getInstance()
        val databaseReference = firebaseDatabase.getReference("GPU")
        val name = model?.getText().toString()
        val des = description?.getText().toString()
        val dis = discount?.getText().toString()
        val pri = price?.getText().toString()
        databaseReference.child(key.toString()).child("description").setValue(des)
        databaseReference.child(key.toString()).child("price").setValue(pri)
        databaseReference.child(key.toString()).child("name").setValue(name)
        databaseReference.child(key.toString()).child("discount").setValue(dis)
        Toast.makeText(this@Review_electronics_detail, "Item Updated", Toast.LENGTH_SHORT).show()
    }
}
package com.example.gpu

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.gpu.Admin
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class Admin : AppCompatActivity() {
    var don: ImageView? = null
    var description: EditText? = null
    var price: EditText? = null
    var model: EditText? = null
    var discount: EditText? = null
    var button: Button? = null
    var btn: Button? = null
    var n: String? = null
    var uri: Uri? = null
    var Imageurl: String? = null
    var name: Array<String?>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        supportActionBar?.setTitle("Admin")
        discount = findViewById(R.id.discount)
        don = findViewById(R.id.imagennnn)
        description = findViewById(R.id.description)
        price = findViewById(R.id.price)
        button = findViewById(R.id.select)
        model = findViewById(R.id.model)
        btn = findViewById(R.id.upload)
        btn?.setOnClickListener(View.OnClickListener { // call the function of upload image
            uploadimage()
        })
        button?.setOnClickListener(View.OnClickListener { // open gallery for image selection
            val photopicker = Intent(Intent.ACTION_PICK)
            photopicker.type = "image/*"
            startActivityForResult(photopicker, 1)
        })
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            uri = data?.getData()
            // set the uri to the image
            don?.setImageURI(uri)
        } else {
            Toast.makeText(this@Admin, "Image not Picked", Toast.LENGTH_SHORT).show()
        }
    }

    fun uploadimage() {
        val progressDialog = ProgressDialog(this@Admin)
        progressDialog.setTitle("Uploading Item")
        progressDialog.show()
        val storageReference =
            FirebaseStorage.getInstance().reference.child("pics")
                .child(uri!!.getLastPathSegment()!!)
        storageReference.putFile(uri!!).addOnSuccessListener { taskSnapshot ->
            val uriTask = taskSnapshot.storage.downloadUrl
            while (!uriTask.isComplete);
            val urlimage = uriTask.result
            Imageurl = urlimage.toString()
            information()
            Toast.makeText(this@Admin, "Item Uploaded", Toast.LENGTH_SHORT).show()
            progressDialog.dismiss()
        }
    }

    fun information() {
        val data = data(
            Imageurl,
            description?.getText().toString(),
            price?.getText().toString(),
            model?.getText().toString(),
            discount?.getText().toString()
        )
        val dref = FirebaseDatabase.getInstance().getReference("GPU")
        val s = dref.push().key
        dref.child(s ?: "").setValue(data)
    }
}
package com.example.gpu

import android.app.ProgressDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class BlankFragment : Fragment() {
    var recyclerView: RecyclerView? = null
    var list1: MutableList<data?>? = null
    var editText: EditText? = null
    private var databaseReference: DatabaseReference? = null
    private var eventListener: ValueEventListener? = null
    var elec_recycler: Elec_Recycler? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_blank, container, false)
        recyclerView = v?.findViewById(R.id.elecrecycler)
        editText = v?.findViewById(R.id.search)
        val progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Loading Items")
        val gridLayoutManager = GridLayoutManager(context, 2)
        recyclerView?.setLayoutManager(gridLayoutManager)
        list1 = ArrayList()
        elec_recycler = Elec_Recycler(list1, context)
        recyclerView?.setAdapter(elec_recycler)
        databaseReference = FirebaseDatabase.getInstance().getReference("GPU")
        progressDialog.show()
        eventListener = databaseReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                list1?.clear()
                for (dataSnapshot1 in dataSnapshot.children) {
                    val data = dataSnapshot1.getValue(data::class.java)
                    list1?.add(data)
                }
                elec_recycler?.notifyDataSetChanged()
                progressDialog.dismiss()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                progressDialog.dismiss()
            }
        })
        editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                filter(s.toString())
            }
        })
        return v
    }

    private fun filter(text: String?) {
        val filterlist = ArrayList<data?>()
        for (item in list1!!) {
            if (item != null && item.getName() != null && text != null) {
                if (item.getName()!!.lowercase(Locale.getDefault())
                        .contains(text.lowercase(Locale.ROOT))
                ) {
                    filterlist.add(item)
                }
            }
        }
        elec_recycler?.filteredlist(filterlist)
    }
}
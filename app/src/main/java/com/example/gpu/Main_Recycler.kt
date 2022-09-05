package com.example.gpu

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class Main_Recycler(var electronicslist: MutableList<data?>?, var context: Context?) :
    RecyclerView.Adapter<electronicsmain?>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): electronicsmain {
        val myview = LayoutInflater.from(parent.context).inflate(R.layout.maindesign, parent, false)
        return electronicsmain(myview)
    }

    override fun onBindViewHolder(holder: electronicsmain, position: Int) {
        holder.name?.setText(electronicslist?.get(position)?.name)
        holder.price?.setText(electronicslist?.get(position)?.price)
        holder.description?.setText(electronicslist?.get(position)?.description)
        Picasso.with(context).load(electronicslist?.get(position)?.getImage())
            .into(holder.imageView)
        holder.cardView?.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, elec_detail_activity::class.java)
            intent.putExtra("image", electronicslist?.get(holder.adapterPosition)?.getImage())
            intent.putExtra("name", electronicslist?.get(holder.adapterPosition)?.getName())
            intent.putExtra("price", electronicslist?.get(holder.adapterPosition)?.getPrice())
            intent.putExtra(
                "description",
                electronicslist?.get(holder.adapterPosition)?.getDescription()
            )
            context?.startActivity(intent)
        })
    }

    override fun getItemCount(): Int {
        return electronicslist!!.size
    }
}

class electronicsmain(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var price: TextView?
    var description: TextView?
    var name: TextView?
    var imageView: ImageView?
    var cardView: CardView?

    init {
        price = itemView.findViewById(R.id.itemprice)
        description = itemView.findViewById(R.id.itemdescription)
        name = itemView.findViewById(R.id.itemname)
        imageView = itemView.findViewById(R.id.shoespic)
        cardView = itemView.findViewById(R.id.mycardview1)
    }
}
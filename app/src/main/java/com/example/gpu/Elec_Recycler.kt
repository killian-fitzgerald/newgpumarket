package com.example.gpu

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.util.*

class Elec_Recycler(var electronicslist: MutableList<data?>?, var context: Context?) :
    RecyclerView.Adapter<electronics?>() {
    private var lastposition = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): electronics {
        val myview = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return electronics(myview)
    }

    override fun onBindViewHolder(holder: electronics, position: Int) {
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
        setAnimations(holder.itemView, position)
    }

    fun setAnimations(viewtoanimate: View?, position: Int) {
        if (position > lastposition) {
            val scaleAnimation = ScaleAnimation(
                0.0f,
                1.0f,
                0.0f,
                1.0f,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.0f
            )
            scaleAnimation.duration = 1500
            viewtoanimate?.startAnimation(scaleAnimation)
            lastposition = position
        }
    }

    override fun getItemCount(): Int {
        return electronicslist!!.size
    }

    fun filteredlist(filterlist: ArrayList<data?>?) {
        electronicslist = filterlist
        notifyDataSetChanged()
    }
}

class electronics(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
        cardView = itemView.findViewById(R.id.mycardview)
    }
}
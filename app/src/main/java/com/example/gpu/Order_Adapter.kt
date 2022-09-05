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

class Order_Adapter(var cartdataList: MutableList<cartdata?>?, var context: Context?) :
    RecyclerView.Adapter<oder?>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): oder {
        val myview = LayoutInflater.from(parent.context).inflate(R.layout.cartdesign, parent, false)
        return oder(myview)
    }

    override fun onBindViewHolder(holder: oder, position: Int) {
        Picasso.with(context).load(cartdataList?.get(position)?.getImagecart())
            .into(holder.imageView)
        holder.cartname?.setText(cartdataList?.get(position)?.namecart)
        holder.cartquantity?.setText(cartdataList?.get(position)?.quantitycart)
        holder.carttotalprice?.setText(cartdataList?.get(position)?.totalpricecart)
        holder.cartdescription?.setText(cartdataList?.get(position)?.description)
        holder.cartprice?.setText(cartdataList?.get(position)?.pricecart)
        holder.datetime?.setText(cartdataList?.get(position)?.datetime)
        holder.cardView1?.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, Order_detail_acivity::class.java)
            intent.putExtra(
                "quantity",
                cartdataList?.get(holder.adapterPosition)?.getQuantitycart()
            )
            intent.putExtra("image", cartdataList?.get(holder.adapterPosition)?.getImagecart())
            intent.putExtra("name", cartdataList?.get(holder.adapterPosition)?.getNamecart())
            intent.putExtra("price", cartdataList?.get(holder.adapterPosition)?.getPricecart())
            intent.putExtra("keyvalue", cartdataList?.get(holder.adapterPosition)?.getKey())
            intent.putExtra(
                "description",
                cartdataList?.get(holder.adapterPosition)?.getDescription()
            )
            context?.startActivity(intent)
        })
    }

    override fun getItemCount(): Int {
        return cartdataList!!.size
    }
}

class oder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var cardView1: CardView?
    var imageView: ImageView?
    var cartname: TextView?
    var cartprice: TextView?
    var cartdescription: TextView?
    var carttotalprice: TextView?
    var cartquantity: TextView?
    var datetime: TextView?

    init {
        cartname = itemView.findViewById(R.id.cartname)
        datetime = itemView.findViewById(R.id.datetime)
        imageView = itemView.findViewById(R.id.cartimage)
        cartprice = itemView.findViewById(R.id.cartprice)
        cartdescription = itemView.findViewById(R.id.cartdescription)
        carttotalprice = itemView.findViewById(R.id.carttotalprice)
        cartquantity = itemView.findViewById(R.id.cartquantity)
        cardView1 = itemView.findViewById(R.id.cartcardview)
    }
}
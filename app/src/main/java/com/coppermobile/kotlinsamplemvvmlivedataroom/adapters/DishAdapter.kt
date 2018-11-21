package com.coppermobile.kotlinsamplemvvmlivedataroom.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.coppermobile.kotlinsamplemvvmlivedataroom.R
import com.coppermobile.kotlinsamplemvvmlivedataroom.data.Dish
import com.coppermobile.kotlinsamplemvvmlivedataroom.utils.IClickListener

class DishAdapter(private val context: Context, private val iClickListener: IClickListener?) : RecyclerView.Adapter<DishAdapter.MyViewHolder>() {

    private var dishList: List<Dish>? = null

    fun addData(dishList: List<Dish>?) {
        this.dishList = dishList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_dish, parent, false))
    }

    override fun getItemCount(): Int {
        return dishList?.size ?: 0
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dish = dishList?.get(position)
        if (dish != null) {
            holder.tvName.text = dish.dishName
            holder.tvDescription.text = dish.dishDescription
            holder.tvPrice.text = String.format("₹%s", dish.dishPrice)
            Glide.with(context).load(dish.dishImage).into(holder.ivDish)

            holder.rlContainer.setOnClickListener {
                holder.itemView.setBackgroundResource(R.drawable.item_clicked)
                iClickListener?.onItemPressed(dish)
            }
        }
    }

    class MyViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        @BindView(R.id.iv_dish)
        lateinit var ivDish: ImageView
        @BindView(R.id.tv_name)
        lateinit var tvName: TextView

        @BindView(R.id.tv_description)
        lateinit var tvDescription: TextView
        @BindView(R.id.tv_price)
        lateinit var tvPrice: TextView

        @BindView(R.id.rl_container)
        lateinit var rlContainer: RelativeLayout

        init {
            ButterKnife.bind(this, v)
        }
    }
}
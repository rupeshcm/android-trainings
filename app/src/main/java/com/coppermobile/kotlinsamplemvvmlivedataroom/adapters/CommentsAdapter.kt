package com.coppermobile.kotlinsamplemvvmlivedataroom.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.coppermobile.kotlinsamplemvvmlivedataroom.R
import com.coppermobile.kotlinsamplemvvmlivedataroom.data.Comments
import com.coppermobile.kotlinsamplemvvmlivedataroom.utils.IShowPopUpListener

class CommentsAdapter(private val iShowPopUpListener: IShowPopUpListener?) : RecyclerView.Adapter<CommentsAdapter.MyViewHolder>() {

    private var commentsList: MutableList<Comments>? = null

    fun addComments(commentsList: MutableList<Comments>) {
        this.commentsList = commentsList
        notifyDataSetChanged()
    }

    fun addSingleComment(comments: Comments) {
        commentsList?.add(comments)
        notifyDataSetChanged()
    }

    fun deleteComment(position: Int) {
        commentsList?.removeAt(position)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_comments, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, p1: Int) {
        val comments = commentsList?.get(p1)
        if (comments != null) {
            holder.tvComments.text = comments.comments
            holder.tvDate.text = comments.date
            holder.ivMenu.setOnClickListener { iShowPopUpListener?.showPopupMenu(comments, holder.adapterPosition, holder.ivMenu) }
        }
    }

    override fun getItemCount(): Int {
        return if (commentsList != null && !commentsList!!.isEmpty()) commentsList!!.size else 0
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.tv_comments)
        lateinit var tvComments: TextView
        @BindView(R.id.tv_date)
        lateinit var tvDate: TextView

        @BindView(R.id.iv_menu)
        lateinit var ivMenu: ImageView

        init {
            ButterKnife.bind(this, itemView)
        }
    }
}
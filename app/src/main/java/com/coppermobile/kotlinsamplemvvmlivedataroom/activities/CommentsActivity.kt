package com.coppermobile.kotlinsamplemvvmlivedataroom.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.PopupMenu
import android.widget.Toast
import butterknife.BindString
import butterknife.ButterKnife
import butterknife.OnClick
import com.bumptech.glide.Glide
import com.coppermobile.kotlinsamplemvvmlivedataroom.R
import com.coppermobile.kotlinsamplemvvmlivedataroom.ViewModelFactory
import com.coppermobile.kotlinsamplemvvmlivedataroom.adapters.CommentsAdapter
import com.coppermobile.kotlinsamplemvvmlivedataroom.data.Comments
import com.coppermobile.kotlinsamplemvvmlivedataroom.data.Dish
import com.coppermobile.kotlinsamplemvvmlivedataroom.utils.Helpers
import com.coppermobile.kotlinsamplemvvmlivedataroom.utils.IShowPopUpListener
import com.coppermobile.kotlinsamplemvvmlivedataroom.viewmodels.CommentsViewModel
import kotlinx.android.synthetic.main.activity_comments.*

class CommentsActivity : AppCompatActivity(), IShowPopUpListener {

    @BindString(R.string.dish)
    lateinit var dishString: String
    @BindString(R.string.enter_comment)
    lateinit var enterComment: String

    @BindString(R.string.comment_added)
    lateinit var commentAdded: String
    @BindString(R.string.comment_deleted)
    lateinit var commentDeleted: String

    private lateinit var adapter: CommentsAdapter
    private lateinit var commentsViewModel: CommentsViewModel

    private var dishId = -1

    private fun setUpRecyclerView() {
        adapter = CommentsAdapter(this)
        rv_comments.setHasFixedSize(true)
        rv_comments.adapter = adapter
    }

    private fun setExtras() {
        val bundle = intent.extras
        if (bundle != null) {
            val dish = bundle.get(dishString) as Dish
            dishId = dish.id
            Glide.with(iv_largeDish).load(dish.dishImage).into(iv_largeDish)
            tv_name.text = dish.dishName
            tv_description.text = dish.dishDescription
            tv_price.text = "₹".plus(dish.dishPrice)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments)

        ButterKnife.bind(this)

        setExtras()
        setUpRecyclerView()

        commentsViewModel = obtainViewModel()
        observeViewModel(commentsViewModel)
    }

    private fun observeViewModel(commentsViewModel: CommentsViewModel) {
        commentsViewModel.getComments(dishId)?.observe(this, Observer<List<Comments>> { if (it != null) adapter.addComments(it as MutableList<Comments>) })
    }

    private fun obtainViewModel(): CommentsViewModel {
        val factory = ViewModelFactory.getInstance(this.application)
        return ViewModelProviders.of(this, factory)[CommentsViewModel::class.java]
    }

    @OnClick(R.id.iv_postComment)
    fun postComments() {
        val commentText = et_comments.text.toString()
        if (!TextUtils.isEmpty(commentText)) {
            val comment = Comments()
            comment.dishId = dishId
            comment.comments = commentText
            comment.date = Helpers.formatDate()
            commentsViewModel.insertComment(comment)
            adapter.addSingleComment(comment)
            Toast.makeText(this, commentAdded, Toast.LENGTH_LONG).show()
            et_comments.setText("")

            val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            if (inputManager != null && this.currentFocus != null)
                inputManager.hideSoftInputFromWindow(this.currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)

        } else Toast.makeText(this, enterComment, Toast.LENGTH_LONG).show()
    }

    override fun showPopupMenu(comments: Comments, position: Int, view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(R.menu.comments_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener {
            commentsViewModel.deleteComment(comments)
            adapter.deleteComment(position)
            Toast.makeText(this, commentDeleted, Toast.LENGTH_LONG).show()
            true
        }
        popupMenu.show()
    }
}
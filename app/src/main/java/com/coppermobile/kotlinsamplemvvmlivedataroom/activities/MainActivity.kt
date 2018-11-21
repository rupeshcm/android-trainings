package com.coppermobile.kotlinsamplemvvmlivedataroom.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import butterknife.BindString
import butterknife.BindView
import butterknife.ButterKnife
import com.coppermobile.kotlinsamplemvvmlivedataroom.R
import com.coppermobile.kotlinsamplemvvmlivedataroom.ViewModelFactory
import com.coppermobile.kotlinsamplemvvmlivedataroom.adapters.DishAdapter
import com.coppermobile.kotlinsamplemvvmlivedataroom.data.Dish
import com.coppermobile.kotlinsamplemvvmlivedataroom.utils.IClickListener
import com.coppermobile.kotlinsamplemvvmlivedataroom.viewmodels.DishViewModel

class MainActivity : AppCompatActivity(), IClickListener {

    @BindView(R.id.rv_dish)
    lateinit var rvDish: RecyclerView

    @BindString(R.string.dish)
    lateinit var dishString: String

    private lateinit var dishAdapter: DishAdapter

    private fun setUpRecyclerView() {
        dishAdapter = DishAdapter(this, this)
        rvDish.setHasFixedSize(true)
        rvDish.adapter = dishAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)

        setUpRecyclerView()

        val dishViewModel = obtainViewModel(this)
        observeViewModel(dishViewModel)
    }

    private fun obtainViewModel(activity: FragmentActivity): DishViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProviders.of(activity, factory)[DishViewModel::class.java]

    }

    private fun observeViewModel(dishViewModel: DishViewModel) {
        dishViewModel.getAllDishes()?.observe(this, Observer<List<Dish>> { if (it != null) dishAdapter.addData(it) })
    }

    override fun onItemPressed(dish: Dish) {
        val i = Intent(this, CommentsActivity::class.java)
        i.putExtra(dishString, dish)
        startActivity(i)
    }
}
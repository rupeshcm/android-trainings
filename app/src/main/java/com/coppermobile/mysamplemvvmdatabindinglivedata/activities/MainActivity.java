package com.coppermobile.mysamplemvvmdatabindinglivedata.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.coppermobile.mysamplemvvmdatabindinglivedata.R;
import com.coppermobile.mysamplemvvmdatabindinglivedata.ViewModelFactory;
import com.coppermobile.mysamplemvvmdatabindinglivedata.adapters.DishAdapter;
import com.coppermobile.mysamplemvvmdatabindinglivedata.data.Dish;
import com.coppermobile.mysamplemvvmdatabindinglivedata.databinding.ActivityMainBinding;
import com.coppermobile.mysamplemvvmdatabindinglivedata.utils.IClickListener;
import com.coppermobile.mysamplemvvmdatabindinglivedata.viewmodels.DishViewModel;

public class MainActivity extends AppCompatActivity implements IClickListener {

    private ActivityMainBinding binding;
    private DishAdapter dishAdapter;

    private void setUpRecyclerView() {
        dishAdapter = new DishAdapter(this);
        RecyclerView rvDish = binding.rvDish;
        rvDish.setHasFixedSize(true);
        rvDish.setAdapter(dishAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setUpRecyclerView();
        DishViewModel dishViewModel = obtainViewModel(this);
        observeViewModel(dishViewModel);
    }

    private void observeViewModel(DishViewModel dishViewModel) {
        dishViewModel.getAllDish().observe(this, dishList -> {
            if (dishList != null) dishAdapter.addData(dishList);
        });
    }

    public DishViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(DishViewModel.class);
    }

    @Override
    public void onItemPressed(Dish dish) {
        if (dish != null) {
            Intent i = new Intent(this, CommentsActivity.class);
            i.putExtra("dish", dish);
            startActivity(i);
        }
    }
}

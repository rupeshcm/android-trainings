package com.coppermobile.mysampleusingmvvmlivedata.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.coppermobile.mysampleusingmvvmlivedata.R;
import com.coppermobile.mysampleusingmvvmlivedata.ViewModelFactory;
import com.coppermobile.mysampleusingmvvmlivedata.adapters.DishAdapter;
import com.coppermobile.mysampleusingmvvmlivedata.data.Dish;
import com.coppermobile.mysampleusingmvvmlivedata.utils.IClickListener;
import com.coppermobile.mysampleusingmvvmlivedata.viewmodels.DishViewModel;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IClickListener {

    @BindView(R.id.rv_dish)
    RecyclerView rvDish;

    @BindString(R.string.dish)
    String dishString;

    private DishAdapter dishAdapter;

    private void setUpRecyclerView() {
        dishAdapter = new DishAdapter(this, this);
        rvDish.setHasFixedSize(true);
        rvDish.setAdapter(dishAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setUpRecyclerView();

        DishViewModel dishViewModel = obtainViewModel(this);
        observeViewModel(dishViewModel);
    }

    private void observeViewModel(DishViewModel dishViewModel) {
        dishViewModel.getAllDish().observe(this, dishList -> {
            if (dishList != null) dishAdapter.addData(dishList);
        });
    }

    public static DishViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(DishViewModel.class);
    }

    @Override
    public void onItemPressed(Dish dish) {
        if (dish != null) {
            Intent i = new Intent(MainActivity.this, CommentsActivity.class);
            i.putExtra(dishString, dish);
            startActivity(i);
        }
    }
}
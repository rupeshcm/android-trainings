package com.coppermobile.mysamplemvvmdatabindinglivedata.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.coppermobile.mysamplemvvmdatabindinglivedata.R;
import com.coppermobile.mysamplemvvmdatabindinglivedata.activities.MainActivity;
import com.coppermobile.mysamplemvvmdatabindinglivedata.data.Dish;
import com.coppermobile.mysamplemvvmdatabindinglivedata.databinding.ListDishBinding;

import java.util.List;

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.MyViewHolder> {

    private LayoutInflater layoutInflater;
    private List<Dish> dishList;
    private Context context;

    public DishAdapter(Context context) {
        this.context = context;
    }

    public void addData(List<Dish> dishList) {
        this.dishList = dishList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        ListDishBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.list_dish, viewGroup, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Dish dish = dishList.get(i);
        if (dish != null) {
            myViewHolder.binding.setDish(dish);
            myViewHolder.binding.setMain((MainActivity) context);
        }
    }

    @Override
    public int getItemCount() {
        return dishList != null && !dishList.isEmpty() ? dishList.size() : 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private ListDishBinding binding;

        MyViewHolder(ListDishBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }
}

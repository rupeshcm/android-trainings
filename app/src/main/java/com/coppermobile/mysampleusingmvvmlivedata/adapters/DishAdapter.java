package com.coppermobile.mysampleusingmvvmlivedata.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.coppermobile.mysampleusingmvvmlivedata.R;
import com.coppermobile.mysampleusingmvvmlivedata.data.Dish;
import com.coppermobile.mysampleusingmvvmlivedata.utils.IClickListener;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.MyViewHolder> {

    private List<Dish> dishList;
    private Context context;
    private IClickListener iClickListener;

    public DishAdapter(Context context, IClickListener iClickListener) {
        this.context = context;
        this.iClickListener = iClickListener;
    }

    public void addData(List<Dish> dishList) {
        if (this.dishList == null) {
            this.dishList = dishList;
            notifyItemRangeInserted(0, dishList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return DishAdapter.this.dishList.size();
                }

                @Override
                public int getNewListSize() {
                    return dishList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return DishAdapter.this.dishList.get(oldItemPosition).getId() == dishList.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Dish project = dishList.get(newItemPosition);
                    Dish old = dishList.get(oldItemPosition);
                    return project.getId() == old.getId() && Objects.equals(project.getDishName(), old.getDishName());
                }

                @Nullable
                @Override
                public Object getChangePayload(int oldItemPosition, int newItemPosition) {
                    return super.getChangePayload(oldItemPosition, newItemPosition);
                }
            });
            this.dishList.clear();
            this.dishList.addAll(dishList);
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_dish, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final Dish dish = dishList.get(position);
        if (dish != null) {
            Glide.with(context).load(dish.getDishImage()).into(holder.ivDish);
            holder.tvName.setText(dish.getDishName());
            holder.tvDescription.setText(dish.getDishDescription());
            holder.tvPrice.setText(String.format("₹%s", String.valueOf(dish.getDishPrice())));
        }

        holder.rlContainer.setOnClickListener(v -> {
            holder.itemView.setBackgroundResource(R.drawable.item_clicked);
            if (iClickListener != null) {
                iClickListener.onItemPressed(dish);
            }
        });
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        return dishList != null && !dishList.isEmpty() ? dishList.size() : 0;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_dish)
        ImageView ivDish;
        @BindView(R.id.tv_name)
        TextView tvName;

        @BindView(R.id.tv_description)
        TextView tvDescription;
        @BindView(R.id.tv_price)
        TextView tvPrice;

        @BindView(R.id.rl_container)
        RelativeLayout rlContainer;

        MyViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
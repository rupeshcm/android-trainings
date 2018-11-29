package com.coppermobile.mysamplemvvmdatabindinglivedata.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.coppermobile.mysamplemvvmdatabindinglivedata.R;
import com.coppermobile.mysamplemvvmdatabindinglivedata.data.Comments;
import com.coppermobile.mysamplemvvmdatabindinglivedata.databinding.ListCommentsBinding;
import com.coppermobile.mysamplemvvmdatabindinglivedata.utils.IShowPopUpListener;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.MyViewHolder> {

    private List<Comments> commentsList;
    private LayoutInflater layoutInflater;
    private IShowPopUpListener iShowPopUpListener;

    public CommentsAdapter(IShowPopUpListener iShowPopUpListener) {
        this.iShowPopUpListener = iShowPopUpListener;
    }

    public void addData(List<Comments> commentsList) {
        this.commentsList = commentsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        ListCommentsBinding binding;
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.list_comments, viewGroup, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Comments comments = commentsList.get(i);
        if (comments != null) {
            myViewHolder.binding.setComments(comments);
            myViewHolder.binding.setCallback(iShowPopUpListener);
        }
    }

    @Override
    public int getItemCount() {
        return commentsList != null && !commentsList.isEmpty() ? commentsList.size() : 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private ListCommentsBinding binding;

        MyViewHolder(ListCommentsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

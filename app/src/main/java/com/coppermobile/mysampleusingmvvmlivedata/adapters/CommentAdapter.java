package com.coppermobile.mysampleusingmvvmlivedata.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coppermobile.mysampleusingmvvmlivedata.R;
import com.coppermobile.mysampleusingmvvmlivedata.data.Comments;
import com.coppermobile.mysampleusingmvvmlivedata.utils.IShowPopUpListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {

    private List<Comments> commentsList;
    private IShowPopUpListener iShowPopUpListener;

    public CommentAdapter(IShowPopUpListener iShowPopUpListener) {
        this.iShowPopUpListener = iShowPopUpListener;
    }

    public void addData(List<Comments> commentsList) {
        this.commentsList = commentsList;
        notifyDataSetChanged();
    }

    public void addSingleComment(Comments note) {
        commentsList.add(note);
        notifyDataSetChanged();
    }

    public void removeComment(int position) {
        commentsList.remove(position);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CommentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_comments, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CommentAdapter.MyViewHolder holder, final int position) {
        final Comments comments = commentsList.get(position);
        holder.tvComments.setText(comments.getComments());
        holder.tvDate.setText(comments.getDate());
        holder.ivMenu.setOnClickListener(v -> {
            if (iShowPopUpListener != null) {
                iShowPopUpListener.showPopupMenu(comments, holder.getAdapterPosition(), holder.ivMenu);
            }
        });
    }

    @Override
    public int getItemCount() {
        return commentsList != null && !commentsList.isEmpty() ? commentsList.size() : 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_comments)
        TextView tvComments;
        @BindView(R.id.tv_date)
        TextView tvDate;

        @BindView(R.id.iv_menu)
        ImageView ivMenu;

        MyViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
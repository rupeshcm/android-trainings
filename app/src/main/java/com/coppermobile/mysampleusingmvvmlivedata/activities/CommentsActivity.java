package com.coppermobile.mysampleusingmvvmlivedata.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MenuInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.coppermobile.mysampleusingmvvmlivedata.R;
import com.coppermobile.mysampleusingmvvmlivedata.ViewModelFactory;
import com.coppermobile.mysampleusingmvvmlivedata.adapters.CommentAdapter;
import com.coppermobile.mysampleusingmvvmlivedata.data.Comments;
import com.coppermobile.mysampleusingmvvmlivedata.data.Dish;
import com.coppermobile.mysampleusingmvvmlivedata.utils.Helper;
import com.coppermobile.mysampleusingmvvmlivedata.utils.IShowPopUpListener;
import com.coppermobile.mysampleusingmvvmlivedata.viewmodels.CommentsViewModel;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommentsActivity extends AppCompatActivity implements IShowPopUpListener {

    //region BindView Data
    @BindView(R.id.iv_largeDish)
    ImageView ivLargeDish;
    @BindView(R.id.tv_name)
    TextView tvName;

    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.tv_price)
    TextView tvPrice;

    @BindView(R.id.rv_comments)
    RecyclerView rvComments;
    @BindView(R.id.et_comments)
    EditText etComments;

    @BindString(R.string.comment_added)
    String commentAdded;
    @BindString(R.string.comment_deleted)
    String commentDeleted;

    @BindString(R.string.dish)
    String dishString;
    //endregion

    private CommentAdapter commentAdapter;
    private int myDishId;
    private CommentsViewModel commentsViewModel;

    public void setUpRecyclerView() {
        commentAdapter = new CommentAdapter(this);
        rvComments.setHasFixedSize(true);
        rvComments.setLayoutManager(new LinearLayoutManager(CommentsActivity.this));
        rvComments.setAdapter(commentAdapter);
    }

    public void setUpExtras() {

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Dish dish = (Dish) bundle.get(dishString);
            if (dish != null) {
                myDishId = dish.getId();
                Glide.with(CommentsActivity.this).load(dish.getDishImage()).into(ivLargeDish);
                tvName.setText(dish.getDishName());
                tvDescription.setText(dish.getDishDescription());
                tvPrice.setText(String.format("₹%s", dish.getDishPrice()));
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        ButterKnife.bind(this);

        setUpRecyclerView();
        setUpExtras();

        commentsViewModel = obtainViewModel(this);
        observeViewModel(commentsViewModel);
    }

    public static CommentsViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(CommentsViewModel.class);
    }

    private void observeViewModel(CommentsViewModel commentsViewModel) {
        commentsViewModel.getAllComments(myDishId).observe(this, new Observer<List<Comments>>() {
            @Override
            public void onChanged(@Nullable List<Comments> commentsList) {
                if (commentsList != null) commentAdapter.addData(commentsList);
            }
        });
    }

    @OnClick(R.id.iv_postComment)
    void postComments() {
        String comments = etComments.getText().toString();
        if (!TextUtils.isEmpty(comments)) {
            final Comments comment = new Comments();
            comment.setComments(comments);
            comment.setDate(Helper.formatDate());
            comment.setDishID(myDishId);
            commentsViewModel.insertComment(comment);
            commentAdapter.addSingleComment(comment);
            Toast.makeText(CommentsActivity.this, commentAdded, Toast.LENGTH_SHORT).show();
            etComments.setText("");

            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputManager != null && this.getCurrentFocus() != null) {
                inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    @Override
    public void showPopupMenu(Comments comments, int position, View view) {
        PopupMenu popup = new PopupMenu(CommentsActivity.this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.comments_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(item -> {
            commentsViewModel.deleteComment(comments);
            commentAdapter.removeComment(position);
            Toast.makeText(CommentsActivity.this, commentDeleted, Toast.LENGTH_SHORT).show();
            return true;
        });
        popup.show();
    }
}
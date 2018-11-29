package com.coppermobile.mysamplemvvmdatabindinglivedata.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
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
import android.widget.Toast;

import com.coppermobile.mysamplemvvmdatabindinglivedata.R;
import com.coppermobile.mysamplemvvmdatabindinglivedata.ViewModelFactory;
import com.coppermobile.mysamplemvvmdatabindinglivedata.adapters.CommentsAdapter;
import com.coppermobile.mysamplemvvmdatabindinglivedata.data.Comments;
import com.coppermobile.mysamplemvvmdatabindinglivedata.data.Dish;
import com.coppermobile.mysamplemvvmdatabindinglivedata.databinding.ActivityCommentsBinding;
import com.coppermobile.mysamplemvvmdatabindinglivedata.utils.Helper;
import com.coppermobile.mysamplemvvmdatabindinglivedata.utils.IShowPopUpListener;
import com.coppermobile.mysamplemvvmdatabindinglivedata.viewmodels.CommentsViewModel;

public class CommentsActivity extends AppCompatActivity implements IShowPopUpListener {

    private ActivityCommentsBinding binding;
    private CommentsAdapter commentAdapter;
    private int myDishId;
    private EditText etComments;
    private CommentsViewModel commentsViewModel;

    public void setUpRecyclerView() {
        commentAdapter = new CommentsAdapter(this);
        RecyclerView rvComments = binding.rvComments;
        rvComments.setHasFixedSize(true);
        rvComments.setLayoutManager(new LinearLayoutManager(this));
        rvComments.setAdapter(commentAdapter);
    }

    public void setUpExtras() {

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Dish dish = (Dish) bundle.get("dish");
            if (dish != null) {
                myDishId = dish.getId();
                binding.setDish(dish);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_comments);

        setUpRecyclerView();
        setUpExtras();

        etComments = binding.etComments;

        commentsViewModel = obtainViewModel(this);
        observeViewModel(commentsViewModel);
    }

    public static CommentsViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(CommentsViewModel.class);
    }

    private void observeViewModel(CommentsViewModel commentsViewModel) {
        commentsViewModel.getAllComments(myDishId).observe(this, commentsList -> {
            if (commentsList != null) commentAdapter.addData(commentsList);
        });
    }

    public void postComments(View view) {
        String comments = etComments.getText().toString();
        if (!TextUtils.isEmpty(comments)) {
            final Comments comment = new Comments();
            comment.setComments(comments);
            comment.setDate(Helper.formatDate());
            comment.setDishID(myDishId);
            commentsViewModel.insertComment(comment);
            Toast.makeText(CommentsActivity.this, "Comment Added", Toast.LENGTH_SHORT).show();
            etComments.setText("");

            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputManager != null && this.getCurrentFocus() != null) {
                inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    @Override
    public void showPopupMenu(View view, Comments comments) {
        PopupMenu popup = new PopupMenu(CommentsActivity.this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.comments_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(item -> {
            commentsViewModel.deleteComment(comments);
            Toast.makeText(CommentsActivity.this, "Comment Deleted", Toast.LENGTH_SHORT).show();
            return true;
        });
        popup.show();
    }
}
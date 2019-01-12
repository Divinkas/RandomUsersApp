package com.example.user.randomusersapp.View;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.user.randomusersapp.Model.Data.UserItem;
import com.example.user.randomusersapp.Presenter.UsersListPresenter;
import com.example.user.randomusersapp.R;
import com.example.user.randomusersapp.View.Adapter.UserAdapter;
import com.example.user.randomusersapp.View.Contract.UserListContract;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class UserListActivity extends AppCompatActivity implements UserListContract {
    private UsersListPresenter presenter;
    private UserAdapter adapter;
    private boolean isLoading = false;

    public RecyclerView rv_list_users;
    public ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        if(savedInstanceState == null){
            rv_list_users = findViewById(R.id.rv_list_users);
            progressBar = findViewById(R.id.pb_load_list);
            presenter = new UsersListPresenter(this, this);
            adapter = new UserAdapter(R.layout.item_load_more, this, new ArrayList<>());
            rv_list_users.setLayoutManager(new LinearLayoutManager(this));
            rv_list_users.setAdapter(adapter);
            set_listeners();
            presenter.load_data();
        }
    }

    @Override
    public void set_list(List<UserItem> list) {
        adapter.stop_load_more();
        adapter.add_list(list);
        isLoading = false;
    }

    private void set_listeners() {
        rv_list_users.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager =((LinearLayoutManager)Objects.requireNonNull(recyclerView.getLayoutManager()));
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItems = layoutManager.findFirstVisibleItemPosition();
                if (!isLoading) {
                    if ( (visibleItemCount+firstVisibleItems) >= totalItemCount) {
                        isLoading = true;
                        presenter.next_load();
                        adapter.start_load_more();
                    }
                }
            }
        });
    }

    @Override
    public void hide_loading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void show_loading() {
        progressBar.setVisibility(View.VISIBLE);
    }
}

package com.example.user.randomusersapp.View;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


public class UserListActivity extends AppCompatActivity implements UserListContract {
    private UsersListPresenter presenter;
    private UserAdapter adapter;
    private boolean isLoading = false;
    private boolean isLinear = false;

    private SwipeRefreshLayout refreshLayout;
    public RecyclerView rv_list_users;
    public ProgressBar progressBar;
    private Menu menu;

    private GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
    private LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        if (savedInstanceState == null) {
            rv_list_users = findViewById(R.id.rv_list_users);
            progressBar = findViewById(R.id.pb_load_list);
            refreshLayout = findViewById(R.id.swipe_layout);
            presenter = new UsersListPresenter(this, this);
            adapter = new UserAdapter(R.layout.item_load_more, this, new ArrayList<>());
            rv_list_users.setLayoutManager(gridLayoutManager);
            isLinear = false;
            rv_list_users.setAdapter(adapter);
            set_listeners();
            presenter.load_data();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_list_activity_menu, menu);
        this.menu = menu;
        check_icons();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.change_list_style_action:
                toggleLayoutManager();
                check_icons();
                break;
        }
        return true;
    }

    @Override
    public void set_list(List<UserItem> list) {
        adapter.stop_load_more();
        adapter.add_list(list);
        isLoading = false;
    }

    @Override
    public void hide_loading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void show_loading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void check_icons() {
        if (!isLinear) {
            menu.findItem(R.id.change_list_style_action).setIcon(R.drawable.view_sequential);
        } else {
            menu.findItem(R.id.change_list_style_action).setIcon(R.drawable.view_grid);
        }
    }

    private boolean getIsGridLayoutManager() {
        return rv_list_users.getLayoutManager() instanceof GridLayoutManager;
    }

    private void toggleLayoutManager() {
        int visible_position = ((LinearLayoutManager) Objects
                .requireNonNull(rv_list_users.getLayoutManager())).findFirstVisibleItemPosition();
        if (getIsGridLayoutManager()) {
            rv_list_users.setLayoutManager(linearLayoutManager);
            isLinear = true;
        } else {
            rv_list_users.setLayoutManager(gridLayoutManager);
            isLinear = false;
        }

        rv_list_users.getLayoutManager().scrollToPosition(visible_position);
    }

    private void set_listeners() {
        rv_list_users.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = ((LinearLayoutManager) Objects
                        .requireNonNull(recyclerView.getLayoutManager()));
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItems = layoutManager.findFirstVisibleItemPosition();
                if (!isLoading) {
                    if ((visibleItemCount + firstVisibleItems) >= totalItemCount) {
                        isLoading = true;
                        presenter.next_load();
                        adapter.start_load_more();
                    }
                }
            }
        });

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return adapter.isBottomProgressPosition(position) ? ((GridLayoutManager) Objects
                        .requireNonNull(rv_list_users.getLayoutManager())).getSpanCount() : 1;
            }
        });

        refreshLayout.setOnRefreshListener(() -> {
            adapter.clearData();
            presenter.load_data();
            refreshLayout.setRefreshing(false);
        });
    }
}

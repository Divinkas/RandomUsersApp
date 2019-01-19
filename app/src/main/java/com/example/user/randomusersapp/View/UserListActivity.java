package com.example.user.randomusersapp.View;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

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

    private SwipeRefreshLayout refreshLayout;
    public RecyclerView recyclerUsers;
    public ProgressBar progressBar;
    private Menu menu;

    private GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
    private LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(isNetworkConnection()) {
            if (savedInstanceState == null) {
                renderContacts();
            }
        } else{
            setContentView(R.layout.no_connection_layout);
            Button buttonRestart = findViewById(R.id.btnNewConnection);
            buttonRestart.setOnClickListener(view -> {
                if(isNetworkConnection()){
                    renderContacts();
                    setCorrectIcons();
                }
            });
        }
    }

    private void renderContacts() {
        setContentView(R.layout.activity_user_list);

        recyclerUsers = findViewById(R.id.rv_list_users);
        progressBar = findViewById(R.id.pb_load_list);
        refreshLayout = findViewById(R.id.swipe_layout);

        presenter = new UsersListPresenter(this);
        adapter = new UserAdapter(R.layout.item_load_more, this, new ArrayList<>());

        recyclerUsers.setLayoutManager(gridLayoutManager);
        recyclerUsers.setAdapter(adapter);
        setListeners();

        showLoading();
        presenter.loadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_list_activity_menu, menu);
        this.menu = menu;
        setCorrectIcons();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.change_list_style_action:
                toggleLayoutManager();
                setCorrectIcons();
                break;
        }
        return true;
    }

    @Override
    public void setList(List<UserItem> list) {
        isLoading = false;
        adapter.stopLoadMore();
        adapter.addList(list);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorLoading() {
        Toast.makeText(this, "Ошибка загрузки данных!", Toast.LENGTH_SHORT).show();
    }

    private void setCorrectIcons() {
        if (!getIsGridLayoutManager()) {
            menu.findItem(R.id.change_list_style_action).setIcon(R.drawable.view_sequential);
        } else {
            menu.findItem(R.id.change_list_style_action).setIcon(R.drawable.view_grid);
        }
    }

    private boolean getIsGridLayoutManager() {
        if(recyclerUsers == null)return true;
        return recyclerUsers.getLayoutManager() instanceof GridLayoutManager;
    }

    private void toggleLayoutManager() {
        if(recyclerUsers != null){
            int visible_position = ((LinearLayoutManager)Objects.requireNonNull(recyclerUsers.getLayoutManager())).findFirstVisibleItemPosition();
            if (getIsGridLayoutManager()) {
                recyclerUsers.setLayoutManager(linearLayoutManager);
            } else {
                recyclerUsers.setLayoutManager(gridLayoutManager);
            }
            recyclerUsers.getLayoutManager().scrollToPosition(visible_position);
        }
    }

    private void setListeners() {
        recyclerUsers.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                        if(isNetworkConnection()) {
                            isLoading = true;
                            presenter.loadData();
                            adapter.startLoadMore();
                        }
                    }
                }
            }
        });

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return adapter.isBottomProgressPosition(position) ? ((GridLayoutManager) Objects
                        .requireNonNull(recyclerUsers.getLayoutManager())).getSpanCount() : 1;
            }
        });

        refreshLayout.setOnRefreshListener(() -> {
            if(isNetworkConnection()) {
                adapter.clearData();
                presenter.loadData();
            }else{
                Toast.makeText(getApplicationContext(), "No network connection!", Toast.LENGTH_SHORT).show();
            }
            refreshLayout.setRefreshing(false);
        });
    }

    private boolean isNetworkConnection(){
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm != null ? cm.getActiveNetworkInfo() : null;
        if (networkInfo != null) {
            if (networkInfo.isConnected()) {
                NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                return (mobile != null && mobile.isConnected()) || (wifi != null && wifi.isConnected());
            } else
                return false;
        }
        return false;
    }

    @Override
    protected void onStop() {
        if(presenter!= null) presenter.unSubscribe();
        super.onStop();
    }
}
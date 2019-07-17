package com.emre1s.playstore.apps_installed_list;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emre1s.playstore.R;
import com.google.android.material.navigation.NavigationView;

public class InstalledAppsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_installed_app_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("My apps & games");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        RecyclerView installedAppsRecyclerView = findViewById(R.id.rv_installed_app_list);
        InstalledAppsAdapter installedAppsAdapter = new InstalledAppsAdapter(this);
        installedAppsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        installedAppsRecyclerView.setAdapter(installedAppsAdapter);

        InstalledAppsViewModel installedAppsViewModel = ViewModelProviders.of(this).get(InstalledAppsViewModel.class);
        installedAppsViewModel.getInstalledApps().observe(this, installedApps -> {
            if (installedApps != null) {
                installedAppsAdapter.setInstalledApps(installedApps);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int menuItemItemId = menuItem.getItemId();
        switch (menuItemItemId) {
            case android.R.id.home: {
                onBackPressed();
                return true;
            }
            default: {
                return super.onOptionsItemSelected(menuItem);
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.nav_my_apps_and_games) {
            Intent intent = new Intent(this, InstalledAppsActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

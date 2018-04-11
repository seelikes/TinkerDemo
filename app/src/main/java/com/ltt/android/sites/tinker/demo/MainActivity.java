package com.ltt.android.sites.tinker.demo;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.os.Process;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.java.lib.oil.file.FileUtils;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.lib.util.TinkerLog;
import com.tencent.tinker.loader.shareutil.ShareTinkerInternals;
import com.yanzhenjie.permission.AndPermission;

import java.io.File;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        AndPermission.with(this)
                .permission(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .send();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "100", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onLoadPatch(View view) {
        File patchFile = FileUtils.getInstance().locateFile(Environment.getExternalStorageDirectory(), "patch", "patch_signed_7zip.apk");
        TinkerLog.i(MainActivity.class.getSimpleName(), "onLoadPatch.UL0112LP.DI1211, patchFile.exists(): " + patchFile.exists());
        if (patchFile.exists()) {
            TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(), patchFile.getAbsolutePath());
        }
        else {
            Toast.makeText(this, R.string.activity_main_load_patch_error, Toast.LENGTH_LONG).show();
        }
    }

    public void onCleanPatch(View view) {
        TinkerLog.i(MainActivity.class.getSimpleName(), "onCleanPatch.UL0112LP.DI1211, enter.");
        Tinker.with(getApplicationContext()).cleanPatch();
        File patchFile = FileUtils.getInstance().locateFile(Environment.getExternalStorageDirectory(), "patch", "patch_signed_7zip.apk");
        if (patchFile.exists()) {
            if (patchFile.delete()) {
                Toast.makeText(this, R.string.activity_main_clean_patch_success, Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this, R.string.activity_main_clean_patch_fail, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onKillSelf(View view) {
        TinkerLog.i(MainActivity.class.getSimpleName(), "onKillSelf.UL0112LP.DI1211, enter.");
        ShareTinkerInternals.killAllOtherProcess(getApplicationContext());
        Process.killProcess(Process.myPid());
    }
}

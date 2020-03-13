package com.roman.batsu;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.roman.batsu.ui.dashboard.DashboardFragment;
import com.roman.batsu.ui.home.ContainerHome;
import com.roman.batsu.ui.note_frags.NotificationsFragment;
import com.roman.batsu.utils.BottomNavigationViewHelper;
import com.roman.batsu.utils.LoaderFragment;
import com.roman.batsu.utils.ToolbarDataSetter;
import com.roman.batsu.utils.application.MyApplication;
import com.roman.batsu.utils.network.NetworkChecker;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    private BottomNavigationView bottomNavigationView;
    private LoaderFragment loaderFragment;
    private Toolbar toolbar;

    private final String[] toolbarTitle = {
            "Расписание",
            "Информация",
            "Звонки"
    };
    private TextView toolbar_title, toolbar_subTitle;

    final Fragment fragment1 = ContainerHome.newInstance();
    final Fragment fragment2 = DashboardFragment.newInstance();
    final Fragment fragment3 = NotificationsFragment.newInstance();
    final FragmentManager fragmentManager = getSupportFragmentManager();
    private Fragment active = fragment1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loaderFragment = MyApplication.getComponent().getLoaderFragment();

        bottomNavigationView = findViewById(R.id.navigation);
        boolean noConnection = !NetworkChecker.isNetworkAvailable(this);
        toolbar = findViewById(R.id.toolbar);
        toolbar_title = toolbar.findViewById(R.id.toolbar_title);
        toolbar_subTitle = toolbar.findViewById(R.id.toolbar_subTitle);

        fragmentManager.beginTransaction().add(R.id.container, fragment3, "3").hide(fragment3).commit();
        fragmentManager.beginTransaction().add(R.id.container, fragment2, "2").hide(fragment2).commit();
        fragmentManager.beginTransaction().add(R.id.container,fragment1, "1").commit();

        toolbar_subTitle.setText(new ToolbarDataSetter().setDataInToolbar());

        toolBarSetUp();


        if (savedInstanceState == null) {
            toolbar_title.setText(toolbarTitle[0]);
        }

        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        if (noConnection) {
            showToast(getString(R.string.no_internet_connection));
        }
    }

    private void toolBarSetUp() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            toolbar.setTitleTextAppearance(this, R.style.RobotoBoldTextAppearance);
        }

    }

    private void showToast(String toastMessage) {
        Toasty.error(this, toastMessage, Toast.LENGTH_LONG, true).show();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if (bottomNavigationView.getSelectedItemId() != menuItem.getItemId()) {
            switch (menuItem.getItemId()) {
                case R.id.navigation_home:
                    toolbar_title.setText(toolbarTitle[0]);
                    setLoaderFragment(active, fragment1, 1);
                    active = fragment1;
                    return true;
                case R.id.navigation_dashboard:
                    toolbar_title.setText(toolbarTitle[1]);
                    setLoaderFragment(active, fragment2, 2);
                    active = fragment2;
                    return true;
                case R.id.navigation_notifications:
                    toolbar_title.setText(toolbarTitle[2]);
                    setLoaderFragment(active, fragment3, 3);
                    active = fragment3;
                    return true;
            }
        }
        return false;
    }


    //устанавливаем текущий фрагмент
    void setLoaderFragment(Fragment active, Fragment currentFragment, int currentPosition){
        loaderFragment.hideOldAndShowNewFragWithBackStackAnimation(
                getSupportFragmentManager(),active, currentFragment,
                currentPosition);
    }

    @Override
    public void onBackPressed() {
        int selectedItemId = bottomNavigationView.getSelectedItemId();
        if (R.id.navigation_home != selectedItemId) {
            setLoaderFragment(active, fragment1, 1);
            active = fragment1;
            bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        } else {
            super.onBackPressed();
        }
    }
}

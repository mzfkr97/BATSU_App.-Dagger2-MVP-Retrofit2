package com.roman.batsu;

import android.content.SharedPreferences;
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
import com.roman.batsu.ui.dialogfragment.OnBoardingDialog;
import com.roman.batsu.ui.home.ViewPagerHome;
import com.roman.batsu.ui.news.PagerNews;
import com.roman.batsu.ui.rings.RingsFragment;
import com.roman.batsu.utils.BottomNavigationViewHelper;
import com.roman.batsu.utils.LoaderFragment;
import com.roman.batsu.utils.ToolbarDataSet;
import com.roman.batsu.utils.ToolbarDataSetter;
import com.roman.batsu.utils.application.MyApplication;
import com.roman.batsu.utils.network.NetworkChecker;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String TAB_1 = "com.roman.batsu.ACTION_HOME";
    private static final String TAB_2 = "com.roman.batsu.ACTION_DASHBOARD";
    private static final String TAB_3 = "com.roman.batsu.ACTION_NOTIFICATION";

    private static final String PREFS_NAME = "firstStartRun";
    private final String[] toolbarTitle = {
            "Расписание",
            "Информация",
            "Звонки"
    };

    private final Fragment fragment1 = ViewPagerHome.newInstance();
    private final Fragment fragment2 = PagerNews.newInstance();
    private final Fragment fragment3 = RingsFragment.newInstance();

    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private BottomNavigationView bottomNavigationView;
    private LoaderFragment loaderFragment;

    private Toolbar toolbar;
    private TextView toolbar_title;
    private Fragment active = fragment1;

    private boolean isOpenShortCutsIntent = false;
    private ToolbarDataSet setToolbarData;

    void setData(ToolbarDataSet setToolbarData) {
        this.setToolbarData = setToolbarData;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loaderFragment = MyApplication.getComponent().getLoaderFragment();
        boolean noConnection = !NetworkChecker.isNetworkAvailable(this);

        bottomNavigationView = findViewById(R.id.navigation);

        toolbar = findViewById(R.id.toolbar);
        toolbar_title = toolbar.findViewById(R.id.toolbar_title);

        TextView toolbar_subTitle = toolbar.findViewById(R.id.toolbar_subTitle);

        fragmentManager.beginTransaction().add(R.id.container, fragment3, "3").hide(fragment3).commit();
        fragmentManager.beginTransaction().add(R.id.container, fragment2, "2").hide(fragment2).commit();
        fragmentManager.beginTransaction().add(R.id.container, fragment1, "1").commit();

        setData(new ToolbarDataSetter());

        toolbar_subTitle.setText(setToolbarData.setData());

        toolBarSetUp();
        if (savedInstanceState == null) {
            toolbar_title.setText(toolbarTitle[0]);
        }
        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        if (noConnection) {
            showToast(getString(R.string.no_internet_connection));
        }
        getShortCutsIntents();
        doFirstRun();
    }


    private void doFirstRun() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        if (settings.getBoolean("isFirstRun", true)) {
            OnBoardingDialog onBoardingDialog = OnBoardingDialog.newInstance();
            onBoardingDialog.setCancelable(false);
            onBoardingDialog.show(getSupportFragmentManager(), "onBoardingDialogFragment");
        }
    }

    private void toolBarSetUp() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            toolbar.setTitleTextAppearance(this, R.style.RobotoBoldTextAppearance);
            toolbar_title.setText(toolbarTitle[0]);
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

    private void getShortCutsIntents() {
        final String action = getIntent().getAction();
        if (action != null) {
            switch (action) {
                case TAB_1:
                    toolbar_title.setText(toolbarTitle[0]);
                    setShortCutsFragment(active, fragment1);
                    active = fragment1;
                    bottomNavigationView.setSelectedItemId(R.id.navigation_home);
                    break;
                case TAB_2:
                    toolbar_title.setText(toolbarTitle[1]);
                    setShortCutsFragment(active, fragment2);
                    bottomNavigationView.setSelectedItemId(R.id.navigation_dashboard);
                    active = fragment2;
                    break;
                case TAB_3:
                    toolbar_title.setText(toolbarTitle[2]);
                    setShortCutsFragment(active, fragment3);
                    active = fragment3;
                    bottomNavigationView.setSelectedItemId(R.id.navigation_notifications);
                    break;
            }
        }
    }

    //устанавливаем текущий фрагмент с анимацией
    void setLoaderFragment(Fragment active, Fragment currentFragment, int currentPosition) {
        loaderFragment.hideOldAndShowNewFragWithBackStackAnimation(
                getSupportFragmentManager(), active, currentFragment,
                currentPosition);
    }

    //устанавливаем текущий фрагмент с анимацией
    void setShortCutsFragment(Fragment active, Fragment currentFragment) {
        isOpenShortCutsIntent = true;
        loaderFragment.hideOldAndShowNewFrag(getSupportFragmentManager(), active, currentFragment);
    }

    @Override
    public void onBackPressed() {
        int selectedItemId = bottomNavigationView.getSelectedItemId();
        if (isOpenShortCutsIntent) {
            MainActivity.this.finish();
        } else if (R.id.navigation_home != selectedItemId) {
            setLoaderFragment(active, fragment1, 1);
            active = fragment1;
            bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        } else {
            super.onBackPressed();
        }
    }
}

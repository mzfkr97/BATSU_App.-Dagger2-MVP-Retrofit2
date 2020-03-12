package com.example.batsu;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.batsu.ui.dashboard.DashboardFragment;
import com.example.batsu.ui.home.ContainerHome;
import com.example.batsu.ui.note_frags.NotificationsFragment;
import com.example.batsu.utils.BottomNavigationViewHelper;
import com.example.batsu.utils.ToolbarDataSetter;
import com.example.batsu.utils.LoaderFragment;
import com.example.batsu.utils.application.MyApplication;
import com.example.batsu.utils.network.NetworkChecker;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    private BottomNavigationView bottomNavigationView;
    private LoaderFragment loaderFragment;
    private boolean noConnection;
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

    // идентификаторы для пунктов меню
    private static final int IDM_OPEN = 101;
    private static final int IDM_SAVE = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loaderFragment = ((MyApplication) getApplication()).getLoaderFragment();
        bottomNavigationView = findViewById(R.id.navigation);
        noConnection = !NetworkChecker.isNetworkAvailable(this);
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


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // добавляем пункты меню
//       // menu.add(Menu.NONE, IDM_OPEN, Menu.NONE, "Открыть");
//        menu.add(Menu.NONE, IDM_SAVE, Menu.NONE, "Сохранить");
//        menu.add(Menu.NONE, IDM_OPEN, Menu.NONE, "Открыть")
//                .setIcon(R.drawable.icon_app_foreground);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle item selection
//        switch (item.getItemId()) {
//            case IDM_OPEN:
//                Toast.makeText(this, "Кнопка Menu есть", Toast.LENGTH_LONG).show();
//                return true;
//            case IDM_SAVE:
//                String error_message = getString(R.string.app_name) +
//                    Build.MODEL + "\n\nВерсия приложения " +
//                        BuildConfig.VERSION_NAME;
//
//                Intent emailIntent = new Intent(Intent.ACTION_SENDTO,
//                        Uri.fromParts("mailto", "slutskapp@gmail.com", null));
//                emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
//                emailIntent.putExtra(Intent.EXTRA_TEXT, error_message);
//                startActivity(Intent.createChooser(emailIntent, getString(R.string.app_name)));
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }



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

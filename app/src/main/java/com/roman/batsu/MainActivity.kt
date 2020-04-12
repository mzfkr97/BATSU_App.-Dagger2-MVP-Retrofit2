package com.roman.batsu

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.roman.batsu.ui.dialogfragment.OnBoardingDialog
import com.roman.batsu.ui.home.ViewPagerHome
import com.roman.batsu.ui.news.PagerNews
import com.roman.batsu.ui.rings.RingsFragment
import com.roman.batsu.utils.BottomNavigationViewHelper
import com.roman.batsu.utils.LoaderFragment
import com.roman.batsu.utils.ToolbarDataSet
import com.roman.batsu.utils.ToolbarDataSetter
import com.roman.batsu.utils.application.MyApplication
import com.roman.batsu.utils.network.NetworkChecker
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private val toolbarTitle = arrayOf(
            "Расписание",
            "Информация",
            "Звонки"
    )
    private val fragment1: Fragment = ViewPagerHome.newInstance()
    private val fragment2: Fragment = PagerNews.newInstance()
    private val fragment3: Fragment = RingsFragment.newInstance()
    private val fragmentManager = supportFragmentManager
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var loaderFragment: LoaderFragment
    private lateinit var toolbar: Toolbar
    private lateinit var toolbar_title: TextView
    private var active = fragment1
    private var isOpenShortCutsIntent = false
    private lateinit var setToolbarData: ToolbarDataSet

    companion object {
        /**
         * @author: Zhurid Roman
         * @version: versionName 1
         * @Environment: Android Studio
         * @Created: 24.03.2020
         */
        private const val TAB_1 = "com.roman.batsu.ACTION_HOME"
        private const val TAB_2 = "com.roman.batsu.ACTION_DASHBOARD"
        private const val TAB_3 = "com.roman.batsu.ACTION_NOTIFICATION"
        private const val PREFS_NAME = "firstStartRun"
    }
    private fun setData(setToolbarData: ToolbarDataSet) {
        this.setToolbarData = setToolbarData
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loaderFragment = (application as MyApplication).loaderFragment
        val noConnection = !NetworkChecker.isNetworkAvailable(this)
        bottomNavigationView = findViewById(R.id.navigation)
        toolbar = findViewById(R.id.toolbar)
        toolbar_title = toolbar.findViewById(R.id.toolbar_title)

        val toolbarSubtitle = toolbar.findViewById<TextView>(R.id.toolbar_subTitle)
        fragmentManager.beginTransaction().add(R.id.container, fragment3, "3").hide(fragment3).commit()
        fragmentManager.beginTransaction().add(R.id.container, fragment2, "2").hide(fragment2).commit()
        fragmentManager.beginTransaction().add(R.id.container, fragment1, "1").commit()
        setData(ToolbarDataSetter())
        toolbarSubtitle.text = setToolbarData.setData()
        toolBarSetUp()
        if (savedInstanceState == null) {
            toolbar_title.text = toolbarTitle[0]
        }
        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
        if (noConnection) {
            toast(getString(R.string.no_internet_connection))
        }

        shortCutsIntents

        GlobalScope.launch(Dispatchers.IO) {
            doFirstRun()
        }


    }

    private fun doFirstRun() {
        val settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        if (settings.getBoolean("isFirstRun", true)) {
            val onBoardingDialog = OnBoardingDialog.newInstance()
            onBoardingDialog.isCancelable = false
            onBoardingDialog.show(supportFragmentManager, "onBoardingDialogFragment")
        }
    }

    private fun toolBarSetUp() {
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayShowTitleEnabled(true)
            toolbar.setTitleTextAppearance(this, R.style.RobotoBoldTextAppearance)
            toolbar_title.text = toolbarTitle[0]
        }
    }

    private fun Context.toast(message: String, duration: Int = Toast.LENGTH_LONG) {
        Toasty.error(this, message, duration).show()
    }


    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        if (bottomNavigationView.selectedItemId != menuItem.itemId) {
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    toolbar_title.text = toolbarTitle[0]
                    setLoaderFragment(active, fragment1, 1)
                    active = fragment1
                    return true
                }
                R.id.navigation_dashboard -> {
                    toolbar_title.text = toolbarTitle[1]
                    setLoaderFragment(active, fragment2, 2)
                    active = fragment2
                    return true
                }
                R.id.navigation_notifications -> {
                    toolbar_title.text = toolbarTitle[2]
                    setLoaderFragment(active, fragment3, 3)
                    active = fragment3
                    return true
                }
            }
        }
        return false
    }

    private val shortCutsIntents: Unit
        get() {
            val action = intent.action
            if (action != null) {
                when (action) {
                    TAB_1 -> {
                        toolbar_title.text = toolbarTitle[0]
                        setShortCutsFragment(active, fragment1)
                        active = fragment1
                        bottomNavigationView.selectedItemId = R.id.navigation_home
                    }
                    TAB_2 -> {
                        toolbar_title.text = toolbarTitle[1]
                        setShortCutsFragment(active, fragment2)
                        bottomNavigationView.selectedItemId = R.id.navigation_dashboard
                        active = fragment2
                    }
                    TAB_3 -> {
                        toolbar_title.text = toolbarTitle[2]
                        setShortCutsFragment(active, fragment3)
                        active = fragment3
                        bottomNavigationView.selectedItemId = R.id.navigation_notifications
                    }
                }
            }
        }

    //устанавливаем текущий фрагмент с анимацией
    private fun setLoaderFragment(active: Fragment?, currentFragment: Fragment?, currentPosition: Int) {
        loaderFragment.hideOldAndShowNewFragWithBackStackAnimation(
                supportFragmentManager, active, currentFragment,
                currentPosition)
    }

    //устанавливаем текущий фрагмент с анимацией
    private fun setShortCutsFragment(active: Fragment?, currentFragment: Fragment?) {
        isOpenShortCutsIntent = true
        loaderFragment.hideOldAndShowNewFrag(supportFragmentManager, active, currentFragment)
    }

    override fun onBackPressed() {
        val selectedItemId = bottomNavigationView.selectedItemId
        when {
            isOpenShortCutsIntent -> {
                finish()
            }
            R.id.navigation_home != selectedItemId -> {
                setLoaderFragment(active, fragment1, 1)
                active = fragment1
                bottomNavigationView.selectedItemId = R.id.navigation_home
            }
            else -> {
                super.onBackPressed()
            }
        }
    }


}
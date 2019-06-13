package com.github.dinder.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.github.dinder.R
import com.github.dinder.model.TabModel
import com.github.dinder.ui.fragment.BaseFragment
import com.github.dinder.ui.fragment.ChatFragment
import com.github.dinder.ui.fragment.FeedFragment
import com.github.dinder.ui.fragment.UserFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var tabs = hashMapOf<String, BaseFragment>()
    private val tabKeys = arrayListOf<String>()

    private val mapOfTab = hashMapOf(
        TAB_USER to TabModel(
            R.id.menu_user,
            R.string.menu_user,
            R.drawable.ic_account_circle,
            UserFragment()
        ),
        TAB_FEED to TabModel(
            R.id.menu_feed,
            R.string.menu_feed,
            R.drawable.ic_feed,
            FeedFragment()
        ),
        TAB_CHAT to TabModel(
            R.id.menu_chat,
            R.string.menu_chat,
            R.drawable.ic_chat,
            ChatFragment()
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        updateNavigation()

        if (savedInstanceState == null) {
            showNavigation(navigation)
        } else {
            tabs = findFragments()
        }

        navigation.setOnNavigationItemSelectedListener { item ->
            showTab(getPositionByItem(item), getSelectedPosition())
            true
        }
    }

    private fun showTab(newItem: Int, oldItem: Int) {
        if (newItem == oldItem) {
            return
        }

        val oldFragment = getFragmentByPosition(oldItem)
        val newFragment = getFragmentByPosition(newItem)

        if (oldFragment.isAdded) {
            oldFragment.onHide()
        }

        supportFragmentManager.beginTransaction()
            .hide(oldFragment)
            .show(newFragment)
            .commitAllowingStateLoss()

        if (newFragment.isAdded) {
            newFragment.onShow()
        }
    }

    private fun getSelectedPosition(): Int {
        val menu = navigation.menu
        for (i in 0 until menu.size()) {
            if (menu.getItem(i).isChecked) {
                return i
            }
        }
        return 0
    }

    private fun getPositionByItem(item: MenuItem): Int {
        val menu = navigation.menu
        for (i in 0 until menu.size()) {
            if (menu.getItem(i) == item) {
                return i
            }
        }
        return 0
    }

    private fun findFragments(): HashMap<String, BaseFragment> = hashMapOf(
        tabKeys[0] to supportFragmentManager.findFragmentByTag(tabKeys[0]) as BaseFragment,
        tabKeys[1] to supportFragmentManager.findFragmentByTag(tabKeys[1]) as BaseFragment,
        tabKeys[2] to supportFragmentManager.findFragmentByTag(tabKeys[2]) as BaseFragment
    )

    private fun showNavigation(navigation: BottomNavigationView) {
        supportFragmentManager.beginTransaction()
            .add(R.id.container, getFragmentByPosition(0), tabKeys[0])
            .add(R.id.container, getFragmentByPosition(1), tabKeys[1])
            .add(R.id.container, getFragmentByPosition(2), tabKeys[2])
            .hide(getFragmentByPosition(1))
            .hide(getFragmentByPosition(2))
            .commit()

        navigation.menu.getItem(0).isChecked = true
    }

    private fun getFragmentByPosition(position: Int) = tabs[tabKeys[position]] as BaseFragment

    private fun updateNavigation() {
        val menu = listOf(TAB_USER, TAB_FEED, TAB_CHAT)
        menu.forEach { items ->
            mapOfTab[items]?.run {
                tabKeys.add(items)
                tabs[items] = fragment

                navigation.menu
                    .add(Menu.NONE, id, Menu.NONE, name)
                    .setIcon(icon)
            }
        }
    }

    companion object {
        const val TAB_USER = "user"
        const val TAB_FEED = "feed"
        const val TAB_CHAT = "chat"
    }
}

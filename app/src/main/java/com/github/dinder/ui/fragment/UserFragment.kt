package com.github.dinder.ui.fragment

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import com.github.dinder.R
import kotlinx.android.synthetic.main.fragment_user.*

class UserFragment : BaseFragment() {
    override val layoutRes: Int
        get() = R.layout.fragment_user

    private var pref: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pref = activity?.getSharedPreferences(APP_SETTINGS, MODE_PRIVATE)
    }

    override fun onResume() {
        super.onResume()

        name.setText(pref?.getString(USER_NAME, "Max").orEmpty())
        surname.setText(pref?.getString(USER_SURNAME, "Bauffal").orEmpty())

        save.setOnClickListener {
            val editor = pref?.edit()
            editor?.putString(USER_NAME, name.text.toString())
            editor?.putString(USER_SURNAME, surname.text.toString())
            editor?.apply()
        }
    }

    companion object {
        const val APP_SETTINGS = "dinder_settings"
        const val USER_NAME = "name"
        const val USER_SURNAME = "surname"
     }
}
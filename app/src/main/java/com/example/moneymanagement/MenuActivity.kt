package com.example.moneymanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.bumptech.glide.Glide
import com.example.moneymanagement.databinding.ActivityMenuBinding
import com.google.android.material.navigation.NavigationView

class MenuActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var b: ActivityMenuBinding
    private var id = 0
    lateinit var user: User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(b.apply {
            StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())
            id = intent.getIntExtra("ID", 0)
            user = GetObject.getUser(id)
            targetList.addAll(GetObject.getTarget())
            categoryList.addAll(GetObject.getCategory())
            priorityList.addAll(GetObject.getPriority())
            itemList.addAll(GetObject.getItem(id))
            supportActionBar?.hide()
            supportFragmentManager.beginTransaction().replace(R.id.frame, HomeFragment(user))
                .commit()
            val toggle = ActionBarDrawerToggle(
                this@MenuActivity,
                drawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close
            )
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()
            navigationView.setNavigationItemSelectedListener(this@MenuActivity)
            refresh()
        }.root)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.homeButton -> supportFragmentManager.beginTransaction()
                .replace(R.id.frame, HomeFragment(user)).commit()

            R.id.listButton -> supportFragmentManager.beginTransaction()
                .replace(R.id.frame, ListFragment(id)).commit()

            R.id.userButton -> supportFragmentManager.beginTransaction()
                .replace(R.id.frame, UserFragment(id, this, user)).commit()

            R.id.settingButton -> supportFragmentManager.beginTransaction()
                .replace(R.id.frame, SettingFragment()).commit()

            R.id.logoutButton -> {
                Save.setID(this, 0)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
        b.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    fun refresh() {
        val header = b.navigationView.getHeaderView(0)
        val iconView = header.findViewById<ImageView>(R.id.iconImageView)
        val nickname = header.findViewById<TextView>(R.id.nicknameTextView)
        Glide.with(this).load(user.getIcon(resources)).into(iconView)
        nickname.text = "ようこそ${user.NickName}さん"
    }
}
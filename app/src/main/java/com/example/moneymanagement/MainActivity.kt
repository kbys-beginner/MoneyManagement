package com.example.moneymanagement

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import com.example.moneymanagement.databinding.ActivityMainBinding
import com.example.moneymanagement.databinding.LayoutCreateUserBinding
import com.example.moneymanagement.databinding.LayoutShowPasswordBinding
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var b: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.apply {
            Save.setID(this@MainActivity,0)
            var id = Save.getID(this@MainActivity)
            if (id != 0) {
                startActivity(Intent(this@MainActivity, MenuActivity::class.java).apply {
                    putExtra("ID", id)
                })
                finish()
            }
            StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())
            createLink.setOnClickListener {
                val dialog = AlertDialog.Builder(this@MainActivity).apply {
                    setCancelable(false)
                }.create()
                dialog.setView(
                    LayoutCreateUserBinding.inflate(LayoutInflater.from(this@MainActivity)).apply {
                        cancelButton.setOnClickListener { dialog.dismiss() }
                        submitButton.setOnClickListener {
                            try {
                                if (passwordEditText.text.isEmpty()) throw Exception("パスワードを入力してください")
                                if (passwordEditText.text.toString() != rePasswordEditText.text.toString()) throw Exception(
                                    "パスワードが一致していません"
                                )
                                val exist = GetObject.userCheck(usernameEditText.text.toString())
                                if (exist) throw Exception("このユーザー名は既に使用されています")
                                val result = GetObject.createUser(
                                    usernameEditText.text.toString(),
                                    passwordEditText.text.toString(),
                                    nicknameEditText.text.toString()
                                )
                                Toast.makeText(this@MainActivity, result, Toast.LENGTH_SHORT)
                                    .show()
                                dialog.dismiss()
                            } catch (e: Exception) {
                                Log.d("HOGE",e.message.toString())
                                Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }.root
                )
                dialog.show()
            }
            showPasswordLink.setOnClickListener {
                val dialog = AlertDialog.Builder(this@MainActivity).apply {
                    setCancelable(false)
                }.create()
                dialog.setView(
                    LayoutShowPasswordBinding.inflate(LayoutInflater.from(this@MainActivity))
                        .apply {
                            cancelButton.setOnClickListener { dialog.dismiss() }
                            confirmButton.setOnClickListener {
                                val result =
                                    GetObject.showPassword(usernameEditText.text.toString())
                                Toast.makeText(this@MainActivity, result, Toast.LENGTH_LONG)
                                    .show()
                            }
                        }.root
                )
                dialog.show()
            }
            loginButton.setOnClickListener {
                try {
                    val result = GetObject.login(
                        usernameEditText.text.toString(),
                        passwordEditText.text.toString()
                    )
                    id = result.toInt()
                    startActivity(
                        Intent(
                            this@MainActivity,
                            MenuActivity::class.java
                        ).apply { putExtra("ID", id) })
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    Save.setID(this@MainActivity, id)
                    finish()
                } catch (e: Exception) {
                    Toast.makeText(this@MainActivity, "ユーザー情報が間違っています", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }.root)
    }
}
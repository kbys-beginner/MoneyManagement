package com.example.moneymanagement

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.Insets
import android.icu.text.ListFormatter.Width
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.text.InputType
import android.text.Layout
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.moneymanagement.databinding.ActivityMainBinding
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.android.material.internal.ViewUtils.RelativePadding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.Exception
import java.time.LocalDate

class MainActivity : AppCompatActivity() {
    private lateinit var b: ActivityMainBinding
    private lateinit var adapter: RecyclerViewAdapter
    private val showList = mutableListOf<Data>()
    private val allList = mutableListOf<Data>()
    private val gson = Gson()
    private var id = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.apply {
            StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())
            try {
                val sp = getSharedPreferences("INFO", MODE_PRIVATE)
                val yearList = mutableListOf<SpinnerData>()
                yearList.add(SpinnerData(0, "2023"))
                yearList.add(SpinnerData(1, "2024"))
                val monthList = mutableListOf<SpinnerData>()
                var count = 0
                for (i in 1..12) {
                    monthList.add(SpinnerData(count, i.toString()))
                    count++
                }
                val targetList = mutableListOf<SpinnerData>()
                targetList.add(SpinnerData(0, "10000"))
                targetList.add(SpinnerData(1, "15000"))
                targetList.add(SpinnerData(2, "20000"))
                targetList.add(SpinnerData(3, "25000"))
                targetList.add(SpinnerData(4, "30000"))
                targetList.add(SpinnerData(5, "35000"))
                targetList.add(SpinnerData(6, "40000"))
                targetList.add(SpinnerData(7, "45000"))
                targetList.add(SpinnerData(8, "50000"))

                yearSpinner.adapter = ArrayAdapter(
                    this@MainActivity,
                    androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,
                    yearList
                )
                monthSpinner.adapter = ArrayAdapter(
                    this@MainActivity,
                    androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,
                    monthList
                )
                targetSpinner.adapter = ArrayAdapter(
                    this@MainActivity,
                    androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,
                    targetList
                )
                adapter = RecyclerViewAdapter(this@MainActivity, R.layout.list_listing, showList)
                recyclerView.adapter = adapter
                val categoryList = mutableListOf<SpinnerData>()
                categoryList.add(SpinnerData(1, "食品"))
                categoryList.add(SpinnerData(2, "生活用品"))
                categoryList.add(SpinnerData(3, "コンビニ"))
                categoryList.add(SpinnerData(4, "外食"))
                categoryList.add(SpinnerData(5, "外出諸費用"))
                categoryList.add(SpinnerData(6, "交通費等"))
                categoryList.add(SpinnerData(7, "趣味"))
                categoryList.add(SpinnerData(8, "その他"))
                val priorityList = mutableListOf<SpinnerData>()
                priorityList.add(SpinnerData(1, "大"))
                priorityList.add(SpinnerData(2, "中"))
                priorityList.add(SpinnerData(3, "小"))
                val targetID = sp.getInt("TARGET", 0)
                yearSpinner.setSelection(yearList.first { it.Name == LocalDate.now().year.toString() }.ID)
                monthSpinner.setSelection(monthList.first { it.Name == LocalDate.now().monthValue.toString() }.ID)
                targetSpinner.setSelection(targetID)
                id = sp.getInt("ID", 0)
                allList.addAll(
                    gson.fromJson(
                        sp.getString("DATA", "[]"),
                        object : TypeToken<List<Data>>() {}.type
                    )
                )

                yearSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        all()
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        return
                    }
                }
                monthSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        all()
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        return
                    }

                }

                targetSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        val select = targetSpinner.selectedItem as SpinnerData
                        sp.edit().apply {
                            putInt("TARGET", select.ID)
                            commit()
                        }
                        all()
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        return
                    }

                }

                addButton.setOnClickListener {
                    var linearLayout1 = LinearLayout(this@MainActivity)
                    linearLayout1.apply {
                        orientation = LinearLayout.VERTICAL
                    }
                    var linearLayout2 = LinearLayout(this@MainActivity)
                    linearLayout2.apply {
                        orientation = LinearLayout.HORIZONTAL
                    }
                    var linearLayout3 = LinearLayout(this@MainActivity)
                    linearLayout3.apply {
                        orientation = LinearLayout.HORIZONTAL
                    }
                    var linearLayout4 = LinearLayout(this@MainActivity)
                    linearLayout4.apply {
                        orientation = LinearLayout.HORIZONTAL
                    }
                    var text = TextView(this@MainActivity)
                    text.apply {
                        setText(" 品名: ")
                    }
                    var titleText = EditText(this@MainActivity)
                    titleText.apply {
                        width = 500
                        titleText.inputType = InputType.TYPE_CLASS_TEXT
                    }

                    var priceText = TextView(this@MainActivity)
                    priceText.apply {
                        setText(" 金額: ")
                    }
                    var priceEditText = EditText(this@MainActivity)
                    priceEditText.apply {
                        width = 250
                        inputType = InputType.TYPE_CLASS_NUMBER
                        gravity = Gravity.CENTER
                    }
                    var categoryText = TextView(this@MainActivity)
                    categoryText.apply {
                        setText(" 分類: ")
                    }
                    var categorySpinner = Spinner(this@MainActivity)
                    categorySpinner.apply {
                        adapter = ArrayAdapter(
                            this@MainActivity,
                            androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,
                            categoryList
                        )
                    }
                    var priorityText = TextView(this@MainActivity)
                    priorityText.apply {
                        setText(" 重要: ")
                    }
                    var prioritySpinner = Spinner(this@MainActivity)
                    prioritySpinner.apply {
                        adapter = ArrayAdapter(
                            this@MainActivity,
                            androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,
                            priorityList
                        )
                    }

                    var dateTextView = TextView(this@MainActivity)
                    dateTextView.apply {
                        setText(" 購入日: ")
                    }
                    var dateEditText = EditText(this@MainActivity)
                    dateEditText.apply {
                        width = 350
                        inputType = InputType.TYPE_CLASS_DATETIME
                        isFocusable = false
                        gravity = Gravity.CENTER
                    }


                    linearLayout2.apply {
                        addView(text)
                        addView(titleText)
                        addView(priceText)
                        addView(priceEditText)
                    }
                    linearLayout3.apply {
                        addView(categoryText)
                        addView(categorySpinner)
                        addView(priorityText)
                        addView(prioritySpinner)
                    }
                    linearLayout4.apply {
                        addView(dateTextView)
                        addView(dateEditText)
                    }
                    linearLayout1.apply {
                        addView(linearLayout2)
                        addView(linearLayout3)
                        addView(linearLayout4)
                    }
                    var now = LocalDate.now()
                    dateEditText.setOnClickListener {
                        DatePickerDialog(
                            this@MainActivity, { _, i, i2, i3 ->
                                var today = String.format("%04d-%02d-%02d", i, i2 + 1, i3)
                                dateEditText.setText(today)
                            },
                            now.year,
                            now.monthValue - 1,
                            now.dayOfMonth
                        ).show()
                    }
                    AlertDialog.Builder(this@MainActivity).apply {
                        setTitle("登録")
                        setMessage("すべての項目を入力してください")
                        setIcon(R.drawable.money)
                        setView(linearLayout1)
                        setPositiveButton("追加") { _, _ ->
                            if (titleText.text.isEmpty() || priceEditText.text.isEmpty() || dateEditText.text.isEmpty()) {
                                Toast.makeText(
                                    this@MainActivity,
                                    "すべての項目を入力してください",
                                    Toast.LENGTH_SHORT
                                ).show()
                                return@setPositiveButton
                            }
                            if (priceEditText.text.toString().toInt() <= 0) {
                                Toast.makeText(
                                    this@MainActivity,
                                    "金額は0円以下は入力できません",
                                    Toast.LENGTH_SHORT
                                ).show()
                                return@setPositiveButton
                            }
                            val priority = prioritySpinner.selectedItem as SpinnerData
                            val category = categorySpinner.selectedItem as SpinnerData
                            val selectDate = LocalDate.parse(dateEditText.text)
                            id = id + 1
                            val item = Data(
                                id,
                                selectDate.year.toString(),
                                selectDate.monthValue.toString(),
                                selectDate.dayOfMonth.toString(),
                                titleText.text.toString(),
                                priceEditText.text.toString().toInt(),
                                priority.ID,
                                priority.Name,
                                category.ID,
                                category.Name
                            )
                            allList.add(item)
                            sp.edit().apply {
                                putString("DATA", gson.toJson(allList))
                                putInt("ID", id)
                                commit()
                            }
                            all()
                            Toast.makeText(
                                this@MainActivity,
                                "${titleText.text}を登録しました",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        create()
                        show()
                    }
                }
                highPriceChip.setOnClickListener {
                    all()
                }
                categoryChip.setOnClickListener {
                    all()
                }
                priorityChip.setOnClickListener {
                    all()
                }
                ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                    override fun onMove(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder
                    ): Boolean {
                        return false
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                        val item = showList[viewHolder.adapterPosition]
                        AlertDialog.Builder(this@MainActivity).apply {
                            setTitle(item.Title)
                            setMessage("本当に削除しますか？")
                            setPositiveButton(
                                "はい",
                                DialogInterface.OnClickListener { dialog, which ->
                                    showList.remove(item)
                                    val removeItem = allList.first { it.ID == item.ID }
                                    allList.remove(removeItem)
                                    sp.edit().apply {
                                        putString("DATA", gson.toJson(allList))
                                        commit()
                                    }
                                    Toast.makeText(
                                        this@MainActivity,
                                        "${item.Title}を削除しました",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    all()
                                    adapter.notifyDataSetChanged()
                                })
                            setNegativeButton(
                                "いいえ",
                                DialogInterface.OnClickListener { dialog, which ->
                                    adapter.notifyDataSetChanged()
                                })
                            create()
                            show()
                        }
                    }
                }).attachToRecyclerView(recyclerView)
                all()
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
            }
        }.root)
    }

    private fun refresh() {
        showList.clear()
        val year = b.yearSpinner.selectedItem as SpinnerData
        val month = b.monthSpinner.selectedItem as SpinnerData
        val tmp = allList.filter { it.Year == year.Name && it.Month == month.Name }.toList()
        if (b.highPriceChip.isChecked) {
            showList.addAll(tmp.sortedByDescending { it.Price })
        } else if (b.categoryChip.isChecked) {
            showList.addAll(tmp.sortedBy { it.CategoryID })
        } else if (b.priorityChip.isChecked) {
            showList.addAll(tmp.sortedBy { it.PriorityID })
        } else {
            showList.addAll(tmp)
        }
        adapter.notifyDataSetChanged()
    }

    private fun calc() {
        val year = b.yearSpinner.selectedItem as SpinnerData
        val month = b.monthSpinner.selectedItem as SpinnerData
        val target = b.targetSpinner.selectedItem as SpinnerData
        val tmp = allList.filter { it.Year == year.Name && it.Month == month.Name }.toList()
        b.sumTextView.text = tmp.sumOf { it.Price }.toString()
        b.calcTextView.text = (target.Name.toInt() - tmp.sumOf { it.Price }).toString()
    }

    private fun chart() {
        b.pieChart.clear()
        val year = b.yearSpinner.selectedItem as SpinnerData
        val month = b.monthSpinner.selectedItem as SpinnerData
        val tmp = allList.filter { it.Year == year.Name && it.Month == month.Name }.toList()
        val input = ArrayList<com.github.mikephil.charting.data.PieEntry>()
        val color = arrayListOf<Int>()
        if (tmp.filter { it.CategoryID == 1 }.any()) {
            input.add(PieEntry(tmp.filter { it.CategoryID == 1 }.sumOf { it.Price }.toFloat(), "食品"))
            color.add(Color.RED)
        }
        if (tmp.filter { it.CategoryID == 2 }.any()) {
            input.add(PieEntry(tmp.filter { it.CategoryID == 2 }.sumOf { it.Price }.toFloat(), "生活用品"))
            color.add(Color.BLUE)
        }
        if (tmp.filter { it.CategoryID == 3 }.any()) {
            input.add(PieEntry(tmp.filter { it.CategoryID == 3 }.sumOf { it.Price }.toFloat(), "コンビニ"))
            color.add(Color.GREEN)
        }
        if (tmp.filter { it.CategoryID == 4 }.any()) {
            input.add(PieEntry(tmp.filter { it.CategoryID == 4 }.sumOf { it.Price }.toFloat(), "外食"))
            color.add(Color.YELLOW)
        }
        if (tmp.filter { it.CategoryID == 5 }.any()) {
            input.add(PieEntry(tmp.filter { it.CategoryID == 5 }.sumOf { it.Price }.toFloat(), "外出諸費用"))
            color.add(Color.MAGENTA)
        }
        if (tmp.filter { it.CategoryID == 6 }.any()) {
            input.add(PieEntry(tmp.filter { it.CategoryID == 6 }.sumOf { it.Price }.toFloat(), "交通費等"))
            color.add(Color.CYAN)
        }
        if (tmp.filter { it.CategoryID == 7 }.any()) {
            input.add(PieEntry(tmp.filter { it.CategoryID == 7 }.sumOf { it.Price }.toFloat(), "趣味"))
            color.add(Color.LTGRAY)
        }
        if (tmp.filter { it.CategoryID == 8 }.any()) {
            input.add(PieEntry(tmp.filter { it.CategoryID == 8 }.sumOf { it.Price }.toFloat(), "その他"))
            color.add(Color.BLACK)
        }
        val dataset = PieDataSet(input, "${year}年${month}月の支出割合")
        dataset.colors = color
        val data = PieData(dataset)
        b.pieChart.data = data
    }

    public fun all() {
        refresh()
        calc()
        chart()
    }
}
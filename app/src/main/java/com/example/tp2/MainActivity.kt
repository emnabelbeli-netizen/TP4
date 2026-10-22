package com.example.tp2

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.listViewProduits)
        val productCategories = resources.getStringArray(R.array.products)

        val icons = intArrayOf(
            R.drawable.ic_phone,
            R.drawable.ic_headphone,
            R.drawable.ic_smartwatch,
            R.drawable.ic_charger
        )

        val categoriesList = ArrayList<HashMap<String, Any>>()
        for (i in productCategories.indices) {
            val item = HashMap<String, Any>()
            item["name"] = productCategories[i]
            item["desc"] = "4 produits disponibles"
            item["icon"] = if (i < icons.size) icons[i] else R.drawable.ic_phone
            categoriesList.add(item)
        }

        val from = arrayOf("name", "desc", "icon")
        val to = intArrayOf(R.id.tvCategoryName, R.id.tvCategoryDesc, R.id.imgCategory)

        val adapter = SimpleAdapter(
            this,
            categoriesList,
            R.layout.item_category,
            from,
            to
        )
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedCategory = productCategories[position]
            val intent = Intent(this, CatalogueActivity::class.java)
            intent.putExtra("CATEGORIE", selectedCategory)
            startActivity(intent)
        }
    }
}

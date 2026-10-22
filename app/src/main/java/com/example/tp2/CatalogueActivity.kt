package com.example.tp2

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class CatalogueActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalogue)

        val tvTitle = findViewById<TextView>(R.id.tvCatalogueTitle)
        val listViewCatalogue = findViewById<ListView>(R.id.listViewCatalogue)

        val category = intent.getStringExtra("CATEGORIE") ?: "Téléphone"
        tvTitle.text = "Catalogue : $category"

        val (arrayResId, defaultIcon, imageUrls) = when (category) {
            "Téléphone" -> Triple(
                R.array.catalogue_telephones,
                R.drawable.ic_phone,
                arrayOf(
                    "https://images.unsplash.com/photo-1610945265064-0e34e5519bbf?w=500",
                    "https://images.unsplash.com/photo-1510557880182-3d4d3cba35a5?w=500",
                    "https://images.unsplash.com/photo-1598327105666-5b89351aff97?w=500",
                    "https://images.unsplash.com/photo-1511707171634-5f897ff02aa9?w=500"
                )
            )
            "Casque Bluetooth" -> Triple(
                R.array.catalogue_casques,
                R.drawable.ic_headphone,
                arrayOf(
                    "https://images.unsplash.com/photo-1600294037681-c80b4cb5b434?w=500",
                    "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=500",
                    "https://images.unsplash.com/photo-1583394838336-acd977736f90?w=500",
                    "https://images.unsplash.com/photo-1546435770-a3e426bf472b?w=500"
                )
            )
            "Montre connectée" -> Triple(
                R.array.catalogue_montres,
                R.drawable.ic_smartwatch,
                arrayOf(
                    "https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=500",
                    "https://images.unsplash.com/photo-1508685096489-7aacd43bd3b1?w=500",
                    "https://images.unsplash.com/photo-1579586337278-3befd40fd17a?w=500",
                    "https://images.unsplash.com/photo-1510017803434-a899398421b3?w=500"
                )
            )
            "Chargeur USB" -> Triple(
                R.array.catalogue_chargeurs,
                R.drawable.ic_charger,
                arrayOf(
                    "https://images.unsplash.com/photo-1583863788434-e58a36330cf0?w=500",
                    "https://images.unsplash.com/photo-1622445268465-843d18c1b2c6?w=500",
                    "https://images.unsplash.com/photo-1585338107529-13afc5f02586?w=500",
                    "https://images.unsplash.com/photo-1609592424083-d56d11f8efb9?w=500"
                )
            )
            else -> Triple(
                R.array.catalogue_telephones,
                R.drawable.ic_phone,
                arrayOf<String>()
            )
        }

        val itemsArray = resources.getStringArray(arrayResId)
        val productsList = ArrayList<HashMap<String, Any>>()

        for (i in itemsArray.indices) {
            val itemStr = itemsArray[i]
            val parts = itemStr.split(" - ")
            val itemMap = HashMap<String, Any>()
            itemMap["name"] = parts.getOrElse(0) { itemStr }
            itemMap["price"] = parts.getOrElse(1) { "" }
            itemMap["imageUrl"] = imageUrls.getOrElse(i) { "" }
            itemMap["defaultIcon"] = defaultIcon
            productsList.add(itemMap)
        }

        val from = arrayOf("name", "price", "imageUrl")
        val to = intArrayOf(R.id.tvName, R.id.tvPrice, R.id.imgProduct)

        val adapter = SimpleAdapter(
            this,
            productsList,
            R.layout.item_product,
            from,
            to
        )

        adapter.viewBinder = SimpleAdapter.ViewBinder { view, data, _ ->
            if (view.id == R.id.imgProduct) {
                val url = data as? String ?: ""
                val imageView = view as ImageView
                if (url.isNotEmpty()) {
                    Glide.with(this@CatalogueActivity)
                        .load(url)
                        .placeholder(defaultIcon)
                        .error(defaultIcon)
                        .into(imageView)
                } else {
                    imageView.setImageResource(defaultIcon)
                }
                true
            } else {
                false
            }
        }

        listViewCatalogue.adapter = adapter

        // Clic sur un produit -> Ouverture de DetailActivity
        listViewCatalogue.setOnItemClickListener { _, _, position, _ ->
            val selectedProduct = productsList[position]
            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra("EXTRA_NAME", selectedProduct["name"].toString())
                putExtra("EXTRA_PRICE", selectedProduct["price"].toString())
                putExtra("EXTRA_CATEGORY", category)
                putExtra("EXTRA_IMAGE_URL", selectedProduct["imageUrl"].toString())
            }
            startActivity(intent)
        }
    }
}

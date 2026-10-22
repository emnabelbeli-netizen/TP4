package com.example.tp2

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val imgProduct = findViewById<ImageView>(R.id.imgDetailProduct)
        val tvCategory = findViewById<TextView>(R.id.tvDetailCategory)
        val tvName = findViewById<TextView>(R.id.tvDetailName)
        val tvPrice = findViewById<TextView>(R.id.tvDetailPrice)
        val tvDescription = findViewById<TextView>(R.id.tvDetailDescription)
        val btnCommander = findViewById<Button>(R.id.btnCommander)
        val btnRetour = findViewById<Button>(R.id.btnRetourDetail)

        val name = intent.getStringExtra("EXTRA_NAME") ?: "Produit"
        val price = intent.getStringExtra("EXTRA_PRICE") ?: ""
        val category = intent.getStringExtra("EXTRA_CATEGORY") ?: "Catalogue"
        val imageUrl = intent.getStringExtra("EXTRA_IMAGE_URL") ?: ""

        tvName.text = name
        tvPrice.text = price
        tvCategory.text = category
        tvDescription.text = "Découvrez le $name. Ce produit d'exception offre des performances remarquables, idéal pour vos besoins quotidiens."

        if (imageUrl.isNotEmpty()) {
            Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.ic_phone)
                .error(R.drawable.ic_phone)
                .into(imgProduct)
        }

        btnCommander.setOnClickListener {
            Toast.makeText(this, "Commande passée pour : $name ($price)", Toast.LENGTH_LONG).show()
        }

        btnRetour.setOnClickListener {
            finish()
        }
    }
}

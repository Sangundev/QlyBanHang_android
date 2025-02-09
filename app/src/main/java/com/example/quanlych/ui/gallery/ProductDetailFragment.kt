// ProductDetailFragment.kt
package com.example.quanlych.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.quanlych.R
import com.example.quanlych.model.Product
import com.example.quanlych.utils.CartManager

class ProductDetailFragment : Fragment() {

    private var quantity: Int = 1
    private lateinit var product: Product

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_productdetails, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        product = arguments?.getParcelable("product") ?: return

        val productName: TextView = view.findViewById(R.id.product_name)
        val productPrice: TextView = view.findViewById(R.id.product_price)
        val productImage: ImageView = view.findViewById(R.id.product_image)
        val productDescription: TextView = view.findViewById(R.id.txtmotachitiet)
        val addToCartButton: Button = view.findViewById(R.id.btnthemvaogiohang)
        val btnIncrement: Button = view.findViewById(R.id.btn_increment)
        val btnDecrement: Button = view.findViewById(R.id.btn_decrement)
        val txtQuantity: TextView = view.findViewById(R.id.txt_quantity)
        val txtTotalPrice: TextView = view.findViewById(R.id.txt_total_price)

        productName.text = product.name
        productPrice.text = "${product.price}đ"
        // Use Glide or another library to load images from resources or URLs
        Glide.with(this)
            .load(product.imageResource) // Replace with URL if using online images
            .into(productImage) // Replace with actual image loading logic
        productDescription.text = product.description

        txtTotalPrice.text = "Total: ${product.price * quantity}đ"

        btnIncrement.setOnClickListener {
            quantity++
            txtQuantity.text = quantity.toString()
            txtTotalPrice.text = "Total: ${String.format("%.2f", product.price * quantity)}đ"
        }

        btnDecrement.setOnClickListener {
            if (quantity > 1) {
                quantity--
                txtQuantity.text = quantity.toString()
                txtTotalPrice.text = "Total: ${String.format("%.2f", product.price * quantity)}đ"
            }
        }

        addToCartButton.setOnClickListener {
            val cartProduct = product.copy(quantity = quantity) // Update the product with the selected quantity
            CartManager.cartItems.add(cartProduct) // Add the product to the cart
            Toast.makeText(context, "Thêm vào giỏ hàng với số lượng $quantity", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_productdetails_to_cart) // Navigate to CartFragment
        }
    }
}

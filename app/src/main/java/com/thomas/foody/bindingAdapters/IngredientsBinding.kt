package com.thomas.foody.bindingAdapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import com.thomas.foody.R
import com.thomas.foody.util.Constants.Companion.BASE_IMAGE_URL

class IngredientsBinding {

    companion object {
        @BindingAdapter("setNumberOfAmounts")
        @JvmStatic
        fun setNumberOfAmounts(textView: TextView, minutes: Double) {
            textView.text = minutes.toString()
        }

        @BindingAdapter("loadIngredientImage")
        @JvmStatic
        fun loadIngredientImage(imageView: ImageView, url: String) {
            imageView.load(BASE_IMAGE_URL + url) {
                crossfade(600)
                error(R.drawable.ic_error_placeholder)
            }
        }
    }

}
package com.getz.setthegoal.presentationpart.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.getz.setthegoal.presentationpart.core.GlideApp

fun View.setSingleClickListener(onClick: (View) -> Unit) {
    val singleClicker = OnSingleClickListener { onClick(it) }
    setOnClickListener(singleClicker)
}

fun EditText.addOnTextChangedListener(onTextChange: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) = Unit

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChange.invoke(text.toString())
        }
    })
}

fun Fragment.say(msg: String) {
    Toast.makeText(this.context, msg, Toast.LENGTH_LONG).show()
}

fun ViewPager.addOnPageSelectedListener(onPageSelected: (position: Int) -> Unit) {
    this.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) = Unit
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) = Unit

        override fun onPageSelected(position: Int) {
            onPageSelected.invoke(position)
        }
    })
}

fun View.addKeyboardListener(result: (isOpened: Boolean) -> Unit): () -> Unit {
    val globalSubscriber = {
        val rect = Rect()
        this.getWindowVisibleDisplayFrame(rect)
        val keyboardHeight = rootView.height - rect.bottom
        /**
         * (rootView.height * fifteenPercent) equals around 300-400px on different devices.
         * */
        val fifteenPercent = 0.15f
        val opened = keyboardHeight > rootView.height * fifteenPercent
        result.invoke(opened)
    }
    this.viewTreeObserver.addOnGlobalLayoutListener(globalSubscriber)
    return globalSubscriber
}

fun View.removeKeyboardListener(globalSubscriber: () -> Unit) {
    this.viewTreeObserver.removeOnGlobalLayoutListener(globalSubscriber)
}

fun hideKeyboard(view: TextView) {
    val inputMethodManager = inputMethodManager(view.context)
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.SHOW_FORCED)
}

private fun inputMethodManager(context: Context): InputMethodManager =
    context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

fun ViewPager.swipeRight(position: Int) {
    if (position < this.size) {
        this.setCurrentItem(position + 1, true)
    }
}

fun ViewPager.swipeLeft(position: Int) {
    if (position > 0) {
        this.setCurrentItem(position - 1, true)
    }
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible(isVisible: Boolean) {
    if (isVisible) this.visibility = View.VISIBLE else this.visibility = View.GONE
}

fun loadPicture(
    view: ImageView,
    url: String,
    isCenterCrop: Boolean = false,
    isCenterInside: Boolean = false,
    isRoundedCorner: Boolean = false,
    listener: RequestListener<Drawable>? = null
) {
    val options = arrayListOf<Transformation<Bitmap>>()
    if (isCenterCrop) options.add(CenterCrop())
    if (isCenterInside) options.add(CenterInside())
    if (isRoundedCorner) options.add(RoundedCorners(10))
    if (options.isEmpty()) options.add(FitCenter())

    val transforms = RequestOptions().transform(*options.toTypedArray())

    GlideApp.with(view)
        .load(url)
        .apply(transforms)
//        .error(GlideApp.with(view).load(url).apply(transforms))
//        .diskCacheStrategy(if (BuildConfig.DEBUG) DiskCacheStrategy.NONE else DiskCacheStrategy.AUTOMATIC)
//        .skipMemoryCache(BuildConfig.DEBUG)
        .listener(listener)
        .into(view)
}
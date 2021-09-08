package com.core.wanandroid.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.core.wanandroid.R

class TitleBar(private val mContext: Context, private val attrs: AttributeSet? = null) :
    FrameLayout(mContext, attrs) {

    private lateinit var titleRoot: View
    private lateinit var titleText: TextView
    private lateinit var titleBack: ImageView

    init {
        LayoutInflater.from(context).inflate(R.layout.title_bar, this)
        titleRoot = findViewById(R.id.title_root)
        titleText = findViewById(R.id.title)
        titleBack = findViewById(R.id.back)

        val a = context.obtainStyledAttributes(attrs, R.styleable.TitleBar)
        val title = a.getString(R.styleable.TitleBar_title)
        val backVisible = a.getBoolean(R.styleable.TitleBar_backVisible, true)

        a.recycle()

        setTitle(title)
        setBackVisible(backVisible)

        titleBack.setOnClickListener {
            if (context is AppCompatActivity) {
                (context as AppCompatActivity).finish()
            }
        }
    }

    private fun setBackVisible(backVisible: Boolean) {
        titleBack.visibility = if (backVisible) View.VISIBLE else View.GONE
    }

    fun setTitle(title: String?) {
        title.let {
            titleText.text = title
        }
    }

}
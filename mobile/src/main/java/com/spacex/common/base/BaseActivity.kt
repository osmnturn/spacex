package com.spacex.common.base

import androidx.lifecycle.ViewModelProvider
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import javax.inject.Inject

import dagger.android.support.DaggerAppCompatActivity
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper


abstract class BaseActivity() : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }


    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (!keyboardAutoCloseEnabled()) {
            return super.dispatchTouchEvent(event)
        }
        val view = currentFocus
        val ret = super.dispatchTouchEvent(event)

        if (view is EditText) {
            val w = currentFocus ?: return ret
            val scrcoords = IntArray(2)
            w.getLocationOnScreen(scrcoords)
            val x = event.rawX + w.left - scrcoords[0]
            val y = event.rawY + w.top - scrcoords[1]

            if (event.action == MotionEvent.ACTION_UP && (x < w.left || x >= w.right
                            || y < w.top || y > w.bottom)) {
                view.clearFocus()
                val outRect = Rect()
                view.getGlobalVisibleRect(outRect)
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                val currentFocusedView = window.currentFocus
                if (currentFocusedView != null) {
                    imm.hideSoftInputFromWindow(currentFocusedView.windowToken, 0)
                }
            }
        }
        return ret
    }

    protected fun keyboardAutoCloseEnabled(): Boolean {
        return false
    }
}

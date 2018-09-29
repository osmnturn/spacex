package com.spacex.common.databinding;

import androidx.databinding.BindingAdapter;

import com.spacex.common.view.ContentView;

public class ContentViewBA {

    @BindingAdapter("displayType")
    public static void setDisplayType(ContentView view, ContentView.Type type) {
        if (type != null)
            view.setDisplay(type);
    }

}

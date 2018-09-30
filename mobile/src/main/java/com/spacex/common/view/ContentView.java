package com.spacex.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.spacex.R;

import androidx.annotation.LayoutRes;
import androidx.appcompat.widget.AppCompatImageView;


public class ContentView extends ViewAnimator {



    private AppCompatImageView errorImage;
    private TextView errorTitle;
    private TextView errorText;



    private int errorLayoutId = R.layout.layout_content_error_default;
    private int loadingLayoutId = R.layout.layout_content_loading_default;

    private View viewLoading;

    private View viewError;

    protected Type currentType = Type.CONTENT;



    public enum Type {
        CONTENT, LOADING, ERROR
    }

    public ContentView(Context context) {
        super(context);
        init(context, null);
    }

    public ContentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        initAttrs(context, attrs);

        viewLoading = LayoutInflater.from(context).inflate(loadingLayoutId, null);

        viewError = LayoutInflater.from(context).inflate(errorLayoutId, null);

        addView(viewLoading);

        addView(viewError);

        errorImage = viewError.findViewById(R.id.error_image);
        errorTitle = viewError.findViewById(R.id.error_title);
        errorText = viewError.findViewById(R.id.error_description);


        if (errorImage == null || errorTitle == null || errorText == null) {
            throw new RuntimeException("required views not found!");
        }

        setInAnimation(context, android.R.anim.fade_in);
        setOutAnimation(context, android.R.anim.fade_out);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setDisplay(currentType);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray attributeArray = context.obtainStyledAttributes(attrs, R.styleable.ContentList);

            errorLayoutId = attributeArray.getResourceId(R.styleable.ContentList_contentErrorLayout, errorLayoutId);
            loadingLayoutId = attributeArray.getResourceId(R.styleable.ContentList_contentLoadingLayout, loadingLayoutId);
            attributeArray.recycle();
        }
    }


    public void setDisplayedChildId(int id) {
        if (id == 0 || getDisplayedChildId() == id)
            return;

        for (int i = 0; i < getChildCount(); i++) {
            if (getChildAt(i).getId() == id) {
                setDisplayedChild(i);
                return;
            }
        }

        String name = getResources().getResourceEntryName(id);
        throw new IllegalStateException("No view with ID " + name);
    }

    public int getDisplayedChildId() {
        return getChildAt(getDisplayedChild()).getId();
    }

    public void setDisplay(Type type) {
        currentType = type;
        switch (type) {
            case CONTENT:
                setDisplayedChildId(R.id.contentView);
                break;
            case LOADING:
                setDisplayedChildId(R.id.loadingView);
                break;
            case ERROR:
                setDisplayedChildId(R.id.errorView);
                break;

        }
    }



    public void setLoadingView(@LayoutRes int layoutId) {
        removeView(viewLoading);
        viewLoading = LayoutInflater.from(getContext()).inflate(layoutId, null);
        addView(viewLoading);
    }



    public void setErrorView(@LayoutRes int layoutId) {
        removeView(viewError);
        viewError = LayoutInflater.from(getContext()).inflate(layoutId, null);
        errorImage = viewError.findViewById(R.id.error_image);
        errorTitle = viewError.findViewById(R.id.error_title);
        errorText = viewError.findViewById(R.id.error_description);
        addView(viewError);
    }

    public View getLoadingView() {
        return viewLoading;
    }


    public View getErrorView() {
        return viewError;
    }

    public AppCompatImageView getErrorImage() {
        return errorImage;
    }

    public TextView getErrorTitle() {
        return errorTitle;
    }

    public TextView getErrorText() {
        return errorText;
    }


}

package com.spacex.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.google.gson.Gson;
import com.spacex.R;
import com.spacex.common.util.Utils;
import com.spacex.repository.remote.BaseResponse;

import androidx.annotation.LayoutRes;
import androidx.appcompat.widget.AppCompatImageView;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import timber.log.Timber;


public class ContentView extends ViewAnimator {



    private AppCompatImageView errorImage;
    private TextView errorTitle;
    private TextView errorText;
    private TextView errorRetryButton;

    private AppCompatImageView emptyImage;
    private TextView emptyTitle;
    private TextView emptyText;

    private int emptyLayoutId = R.layout.layout_content_empty_default;
    private int errorLayoutId = R.layout.layout_content_error_default;
    private int loadingLayoutId = R.layout.layout_content_loading_default;

    private View viewLoading;
    private View viewEmpty;
    private View viewError;

    protected Type currentType = Type.CONTENT;

    public void setRetryErrorOnClickListener(View.OnClickListener listener) {
        if (errorRetryButton != null) {
            errorRetryButton.setOnClickListener(listener);
        }
    }


    public enum Type {
        CONTENT, LOADING, ERROR, EMPTY
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
        viewEmpty = LayoutInflater.from(context).inflate(emptyLayoutId, null);
        viewError = LayoutInflater.from(context).inflate(errorLayoutId, null);

        addView(viewLoading);
        addView(viewEmpty);
        addView(viewError);

        errorImage = viewError.findViewById(R.id.error_image);
        errorTitle = viewError.findViewById(R.id.error_title);
        errorText = viewError.findViewById(R.id.error_description);
        errorRetryButton = viewError.findViewById(R.id.error_retry_button);

        emptyImage = viewError.findViewById(R.id.empty_image);
        emptyTitle = viewError.findViewById(R.id.empty_title);
        emptyText = viewError.findViewById(R.id.empty_description);

        if (errorImage == null || errorTitle == null || errorText == null || errorRetryButton == null) {
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
            emptyLayoutId = attributeArray.getResourceId(R.styleable.ContentList_contentEmptyLayout, emptyLayoutId);
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
            case EMPTY:
                currentType = Type.EMPTY;
                setDisplayedChildId(R.id.emptyView);
                break;
        }
    }

    public void setError(Throwable t) {
        Timber.w(t);
        setDisplay(Type.ERROR);

        String strErrorTitle = getContext().getString(R.string.an_error_occurred);
        String strErrorDescription = getContext().getString(R.string.unexpected_error_our_services);
        int imageResource = R.drawable.ic_error;

        if (!Utils.network.isConnectingToInternet(getContext())) {
            strErrorTitle = getContext().getString(R.string.no_connection);
            strErrorDescription = getContext().getString(R.string.no_internet_connection);
            imageResource = R.drawable.image_no_connection;
        } else if (t instanceof HttpException) {
            try {
                ResponseBody body = ((HttpException) t).response().errorBody();
                if (body != null) {
                    String bodyString = body.string();
                    BaseResponse errorParser = new Gson().fromJson(bodyString, BaseResponse.class);
                    if (errorParser != null && !TextUtils.isEmpty(errorParser.getErrorMessage())) {
                        strErrorDescription = errorParser.getErrorMessage();
                    } else {

                    }
                }
            } catch (Exception e) {
                Timber.w(e);
            }
        }

        if (errorTitle != null) {
            errorTitle.setText(strErrorTitle);
        }
        if (errorText != null) {
            errorText.setText(strErrorDescription);
        }
        if (errorImage != null) {
            errorImage.setImageResource(imageResource);
        }
    }

    public void setLoadingView(@LayoutRes int layoutId) {
        removeView(viewLoading);
        viewLoading = LayoutInflater.from(getContext()).inflate(layoutId, null);
        addView(viewLoading);
    }

    public void setEmptyView(@LayoutRes int layoutId) {
        removeView(viewEmpty);
        viewEmpty = LayoutInflater.from(getContext()).inflate(layoutId, null);
        addView(viewEmpty);
    }

    public void setErrorView(@LayoutRes int layoutId) {
        removeView(viewError);
        viewError = LayoutInflater.from(getContext()).inflate(layoutId, null);
        errorImage = viewError.findViewById(R.id.error_image);
        errorTitle = viewError.findViewById(R.id.error_title);
        errorText = viewError.findViewById(R.id.error_description);
        errorRetryButton = viewError.findViewById(R.id.error_retry_button);
        addView(viewError);
    }

    public View getLoadingView() {
        return viewLoading;
    }

    public View getEmptyView() {
        return viewEmpty;
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

    public TextView getErrorRetryButton() {
        return errorRetryButton;
    }

    public AppCompatImageView getEmptyImage() {
        return emptyImage;
    }

    public TextView getEmptyTitle() {
        return emptyTitle;
    }

    public TextView getEmptyText() {
        return emptyText;
    }

}

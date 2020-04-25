package com.creations.mvvm.viewmodel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.creations.condition.Preconditions;
import com.creations.mvvm.live.LiveEvent;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Interface to support the basic shared operations between MVVMViewModel's
 * as well as define needed {@link BindingAdapter}'s. Supports {@link ContextCallback}'s
 * so the MVVMViewModel can use Context's when needed.
 */
@SuppressWarnings("unused")
public interface IMVVMViewModel {

    interface ContextCallback {
        void call(@NonNull final Context context);
    }

    @BindingAdapter("error")
    static void setErrorMessage(@NonNull final TextInputEditText view, @Nullable String errorMessage) {
        view.setError(errorMessage);
    }

    @BindingAdapter("src")
    static void setImageSource(@NonNull final ImageView view, @Nullable Bitmap bitmap) {
        view.setImageBitmap(bitmap);
    }

    @BindingAdapter("visibility")
    static void setVisibility(@NonNull final View view, int visibility) {
        view.setVisibility(visibility);
    }

    @BindingAdapter("marginTop")
    static void setMarginTop(@NonNull final View view, final int dimension) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) layoutParams;
            params.topMargin = dimension;
            view.setLayoutParams(params);
        }
    }

    @BindingAdapter("marginStart")
    static void setMarginStart(@NonNull final View view, final int dimension) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) layoutParams;
            params.setMarginStart(dimension);
            view.setLayoutParams(params);
        }
    }

    @BindingAdapter("marginEnd")
    static void setMarginEnd(@NonNull final View view, final int dimension) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) layoutParams;
            params.setMarginEnd(dimension);
            view.setLayoutParams(params);
        }
    }

    @BindingAdapter("adapter")
    static void bindRecyclerViewAdapter(@NonNull final RecyclerView recyclerView, @NonNull final RecyclerView.Adapter<?> adapter) {
        recyclerView.setPadding(0, 0, 0, 0);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter("navigationAdapter")
    static void bindRecyclerAdapter(@NonNull final RecyclerView recyclerView, @NonNull final RecyclerView.Adapter<?> adapter) {
        recyclerView.setPadding(0, 0, 0, 0);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter("onFocusChange")
    static void onFocusChange(@NonNull final TextInputEditText view, @NonNull final View.OnFocusChangeListener listener) {
        view.setOnFocusChangeListener(listener);
    }

    @BindingAdapter("selectedItemPosition")
    static void selectedItemPosition(@NonNull final Spinner spinner, @NonNull final Integer position) {
        spinner.setSelection(position);
    }
    @BindingAdapter("onItemSelected")
    static void setItemSelectedListener(@NonNull final Spinner spinner, @NonNull final AdapterView.OnItemSelectedListener itemSelectedListener) {
        spinner.setOnItemSelectedListener(itemSelectedListener);
    }

    @BindingAdapter("entries")
    static void setEntries(@NonNull final Spinner spinner, final CharSequence[] entries) {
        if (entries != null && entries.length > 0) {
            SpinnerAdapter oldAdapter = spinner.getAdapter();
            boolean changed = true;
            if (oldAdapter != null && oldAdapter.getCount() == entries.length) {
                changed = false;
                for (int i = 0; i < entries.length; i++) {
                    if (!entries[i].equals(oldAdapter.getItem(i))) {
                        changed = true;
                        break;
                    }
                }
            }
            if (changed) {
                ArrayAdapter<CharSequence> adapter =
                        new ArrayAdapter<>(spinner.getContext(),
                                android.R.layout.simple_spinner_item, entries);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
            }
        } else {
            spinner.setAdapter(null);
        }
    }

    @BindingAdapter("endIconClicked")
    static void setEndIconClicked(@NonNull final TextInputLayout layout, @NonNull final Runnable listener) {
        Preconditions.requiresNonNull(layout, "TextInputLayout");

        layout.setEndIconOnClickListener(v -> listener.run());
    }

    @BindingAdapter("endIconVisibility")
    static void setEndIconVisibility(@NonNull final TextInputLayout layout, @Nullable final Boolean visibility) {
        Preconditions.requiresNonNull(layout, "TextInputLayout");

        layout.setEndIconVisible(visibility != null && visibility);
    }

    @BindingAdapter("setClickableText")
    static void setText(@NonNull final TextView textView, @NonNull final SpannableString text) {
        textView.setText(text);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @BindingAdapter("bold")
    static void setBold(TextView view, boolean isBold) {
        if (isBold) {
            view.setTypeface(null, Typeface.BOLD);
        } else {
            view.setTypeface(null, Typeface.NORMAL);
        }
    }

    @BindingAdapter("leftIcon")
    static void setLeftIcon(TextView view, Drawable drawable) {
        view.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
    }

    @BindingAdapter("checkBoxChangeListener")
    static void setCheckedEvent(CheckBox checkBox, CompoundButton.OnCheckedChangeListener checkedChangeListener) {
        checkBox.setOnCheckedChangeListener(checkedChangeListener);
    }

    @BindingAdapter("checkedChangeListener")
    static void setCheckedEvent(Switch switchButton, CompoundButton.OnCheckedChangeListener checkedChangeListener) {
        switchButton.setOnCheckedChangeListener(checkedChangeListener);
    }

    @BindingAdapter("radioChangeListener")
    static void setCheckedEvent(RadioButton radioButton, CompoundButton.OnCheckedChangeListener checkedChangeListener) {
        radioButton.setOnCheckedChangeListener(checkedChangeListener);
    }

    @BindingAdapter("buttonClickListener")
    static void setCheckedEvent(Button button, Button.OnClickListener clickListener) {
        button.setOnClickListener(clickListener);
    }

    @NonNull
    LiveEvent<ContextCallback> getContextCallback();

    void startIntentWithPhoneNumber(@NonNull final String phoneNumber);

}

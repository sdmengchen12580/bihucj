package com.aganyun.acode.callback;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by 孟晨 on 2018/5/22.
 */

public abstract  class EdittextWatcherCallback implements TextWatcher {

    protected abstract void textChanged();

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        textChanged();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }


}

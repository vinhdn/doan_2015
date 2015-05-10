/*
 * Copyright (C) 2013 Manuel Peinado
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.manuelpeinado.fadingactionbar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

public class FadingActionBarHelper extends FadingActionBarHelperBase {

    private ActionBar mActionBar;
    private android.app.ActionBar mActionBarA;

    @SuppressLint("NewApi")
    @Override
    public void initActionBar(ActionBarActivity activity) {
        mActionBar = activity.getSupportActionBar();
        super.initActionBar(activity);
    }

    @Override
    public void initActionBar(Activity activity) {
        mActionBarA = activity.getActionBar();
        super.initActionBar(activity);
    }

    @SuppressLint("NewApi")
    @Override
    protected int getActionBarHeight() {
        if (mActionBar != null)
            return mActionBar.getHeight();
        if (mActionBarA != null)
            return mActionBarA.getHeight();
        return 0;
    }

    @Override
    protected boolean isActionBarNull() {
        return mActionBar == null && mActionBarA == null;
    }

    @SuppressLint("NewApi")
    @Override
    protected void setActionBarBackgroundDrawable(Drawable drawable) {
        if (mActionBar != null)
            mActionBar.setBackgroundDrawable(drawable);
        if (mActionBarA != null)
            mActionBarA.setBackgroundDrawable(drawable);
    }
}

/*
 * Copyright 2012 Roman Nurik
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.klinker.android.messaging_donate.wizardpager.wizard.model;

import android.support.v4.app.Fragment;
import android.text.TextUtils;
import com.klinker.android.messaging_donate.wizardpager.wizard.ui.LinkFragment;

import java.util.ArrayList;

/**
 * A page asking for a name and an email.
 */
public class LinkPage extends Page {
    public static final String NAME_DATA_KEY = "name";
    public static final String EMAIL_DATA_KEY = "email";

    public LinkPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return LinkFragment.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        dest.add(new ReviewItem("Your name", mData.getString(NAME_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Your email", mData.getString(EMAIL_DATA_KEY), getKey(), -1));
    }

    @Override
    public boolean isCompleted() {
        return !TextUtils.isEmpty(mData.getString(NAME_DATA_KEY));
    }

    public Page setExtra(String message, String link) {
        mMessage = message;
        mLink = link;
        return this;
    }

    public String getMessage() {
        return mMessage;
    }

    public String getLink() {
        return mLink;
    }
}

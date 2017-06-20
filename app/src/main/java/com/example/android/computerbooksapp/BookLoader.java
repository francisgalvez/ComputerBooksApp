/*
 * Copyright (c) 2017 by Francis Gálvez
 */
package com.example.android.computerbooksapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Loads a list of books by using an AsyncTask to perform the network request to the given URL.
 */
class BookLoader extends AsyncTaskLoader<List<Book>> {

    /** Query URL */
    private String url;

    /**
     * Constructs a new {@link BookLoader}.
     *
     * @param context of the activity
     * @param url to load data from
     */
    BookLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<Book> loadInBackground() {
        if (url == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of books.
        return QueryUtils.fetchBookData(url);
    }
}

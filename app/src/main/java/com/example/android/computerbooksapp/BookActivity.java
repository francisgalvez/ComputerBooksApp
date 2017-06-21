/*
 * Copyright (c) 2017 by Francis Gálvez
 */
package com.example.android.computerbooksapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {
    /**
     * Constant value for the book loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int BOOK_LOADER_ID = 1;

    /** Adapter for the list of books */
    private BookAdapter adapter;

    /** SearchView displayed on the screen" */
    private SearchView searchView;

    /** Variable for the query searched by the user */
    private String query;

    /**
     * TextView that is displayed when there are no books to show
     */
    private TextView noBooksTextView;

    /**
     * TextView that is displayed when there is no Internet connection
     */
    private TextView noConnectionTextView;

    /**
     * TextView that is displayed when the user has no Internet connection
     */
    private ImageView noWifiImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_activity);

        // Find a reference to the {@link ListView} in the layout
        ListView bookListView = (ListView) findViewById(R.id.list);

        noBooksTextView = (TextView) findViewById(R.id.no_books);
        noConnectionTextView = (TextView) findViewById(R.id.no_connection);
        noWifiImageView = (ImageView) findViewById(R.id.no_wifi_image);

        noConnectionTextView.setText(R.string.no_internet_connection);
        noBooksTextView.setText(R.string.no_books);

        //Get the SearchView and enable the Submit Button on it
        searchView = (SearchView) findViewById(R.id.search);
        searchView.setSubmitButtonEnabled(true);

        //bookListView.setEmptyView(noBooksTextView);

        // Create a new adapter that takes an empty list of books as input
        adapter = new BookAdapter(this, new ArrayList<Book>());

        // Set the adapter on the {@link ListView} so the list can be populated in the user interface
        bookListView.setAdapter(adapter);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected book.
        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current book that was clicked on
                Book currentBook = adapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri bookUri = Uri.parse(currentBook.getUrl());

                // Create a new intent to view the book URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        final boolean connected = networkInfo != null && networkInfo.isConnected();

        //Set an OnQueryTextListener to the search button
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                if (connected) {
                    // Get a reference to the LoaderManager, in order to interact with loaders.
                    LoaderManager loaderManager = getLoaderManager();

                    noConnectionTextView.setVisibility(View.GONE);
                    noWifiImageView.setVisibility(View.GONE);
                    noBooksTextView.setVisibility(View.GONE);

                    // Restart the loader.
                    loaderManager.restartLoader(BOOK_LOADER_ID, null, BookActivity.this);
                    return true;
                } else {
                    // Otherwise, display error
                    // First, hide loading indicator so error message will be visible
                    View loadingIndicator = findViewById(R.id.loading_indicator);
                    loadingIndicator.setVisibility(View.GONE);

                    noConnectionTextView.setVisibility(View.VISIBLE);
                    noWifiImageView.setVisibility(View.VISIBLE);
                    noBooksTextView.setVisibility(View.GONE);
                    return false;
                }
            }
        });

        // If there is a network connection, fetch data
        if (connected) {
            noConnectionTextView.setVisibility(View.GONE);
            noWifiImageView.setVisibility(View.GONE);
            noBooksTextView.setVisibility(View.GONE);

            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(BOOK_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Also hide the search bar
            searchView.setVisibility(View.GONE);

            // Update empty state with no connection error message
            noConnectionTextView.setVisibility(View.VISIBLE);
            noWifiImageView.setVisibility(View.VISIBLE);
            noBooksTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        //Get the query given by the user
        query = searchView.getQuery().toString();

        // Create a new loader for the given URL
        return new BookLoader(this, query);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Clear the adapter of previous book data
        adapter.clear();

        // If there is a valid list of {@link book}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (books != null && !books.isEmpty()) {
            adapter.addAll(books);
        }

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        final boolean connected = networkInfo != null && networkInfo.isConnected();

        if (connected) {
            noBooksTextView.setText(R.string.no_books);
        } else {
            noConnectionTextView.setText(R.string.no_internet_connection);
            noWifiImageView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        // Loader reset, so we can clear out our existing data.
        adapter.clear();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //Get the query given by the user
        query = searchView.getQuery().toString();
        outState.putString("query", query);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        query = savedInstanceState.getString("query");
        //Initialize the Loader (execute the search)
        super.onRestoreInstanceState(savedInstanceState);
    }
}

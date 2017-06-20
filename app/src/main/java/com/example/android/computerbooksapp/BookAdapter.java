/*
 * Copyright (c) 2017 by Francis Gálvez
 */
package com.example.android.computerbooksapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * An {@link BookAdapter} knows how to create a list item layout for each book
 * in the data source (a list of {@link Book} objects).
 *
 * These list item layouts will be provided to an adapter view like ListView
 * to be displayed to the user.
 */
class BookAdapter extends ArrayAdapter<Book> {

    /**
     * Constructs a new {@link BookAdapter}.
     *
     * @param context of the app
     * @param books is the list of books, which is the data source of the adapter
     */
    BookAdapter(Context context, List<Book> books) {
        super(context, 0, books);
    }

    /**
     * Returns a list item view that displays information about the book at the given position
     * in the list of books.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.book_list_item, parent, false);
        }

        // Find the book at the given position in the list of books
        Book currentBook = getItem(position);

        // Get the title of the book
        String title = currentBook.getTitle();
        // Find the TextView with view ID title
        TextView titleView = (TextView) listItemView.findViewById(R.id.title);
        // Display the location of the current book in that TextView
        titleView.setText(title);

        // Get the author of the book
        String author = currentBook.getAuthor();
        // Find the TextView with view ID author
        TextView authorView = (TextView) listItemView.findViewById(R.id.author);
        // Display the location offset of the current book in that TextView
        authorView.setText(author);

        // Get the publisher of the book
        String publisher = currentBook.getPublisher();
        // Find the TextView with view ID author
        TextView publisherView = (TextView) listItemView.findViewById(R.id.publisher);
        // Display the location offset of the current book in that TextView
        publisherView.setText(publisher);

        // Find the TextView with view ID date
        TextView dateView = (TextView) listItemView.findViewById(R.id.published_date);
        // Format the date string (i.e. "Mar 3, 1984")
        String formattedDate = currentBook.getPublishedDate();
        // Display the date of the current earthquake in that TextView
        dateView.setText(formattedDate);

        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }
}

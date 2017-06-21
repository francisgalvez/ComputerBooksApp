/*
 * Copyright (c) 2017 by Francis Gálvez
 */
package com.example.android.computerbooksapp;

import android.content.Context;
import android.support.annotation.NonNull;
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
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;

        ViewHolder holder;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.book_list_item, parent, false);

            holder = new ViewHolder();

            // Find the TextView with view ID title
            holder.titleView = (TextView) listItemView.findViewById(R.id.title);

            // Find the TextView with view ID author
            holder.authorView = (TextView) listItemView.findViewById(R.id.author);

            // Find the TextView with view ID author
            holder.publisherView = (TextView) listItemView.findViewById(R.id.publisher);

            listItemView.setTag(holder);
        } else {
            // view already exists, get the holder instance from the view
            holder = (ViewHolder) listItemView.getTag();
        }

        // Find the book at the given position in the list of books
        Book currentBook = getItem(position);

        // Display the title of the current book in that TextView
        holder.titleView.setText(currentBook.getTitle());

        // Display the author of the current book in that TextView
        holder.authorView.setText(currentBook.getAuthor());

        // Display the publisher of the current book in that TextView
        holder.publisherView.setText(currentBook.getPublisher());

        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }

    private static class ViewHolder {
        TextView titleView;
        TextView authorView;
        TextView publisherView;
    }
}

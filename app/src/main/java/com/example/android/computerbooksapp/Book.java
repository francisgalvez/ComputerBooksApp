/*
 * Copyright (c) 2017 by Francis Gálvez
 */
package com.example.android.computerbooksapp;

/**
 * An {@link Book} object contains information related to a single book.
 */
class Book {

    /** Title of the book */
    private String title;

    /** Author of the book */
    private String author;

    /** Publisher of the book */
    private String publisher;

    /** Date of publishing of the book */
    private String publishedDate;

    /** Website URL of the book */
    private String url;

    /**
     * Constructs a new {@link Book} object.
     *
     * @param title is the title of the book
     * @param author is the author of the book
     * @param publisher is the publisher of the book
     * @param publishedDate is the date of publishing of the book
     * @param url is the website URL to find more details about the book
     */
    Book(String title, String author, String publisher, String publishedDate, String url) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.url = url;
    }

    // Getters
    String getTitle() { return title; }

    String getAuthor() {
        return author;
    }

    String getPublisher() {
        return publisher;
    }

    String getPublishedDate() {
        return publishedDate;
    }

    String getUrl() { return url; }
}

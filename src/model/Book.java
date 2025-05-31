package model;

import java.util.ArrayList;
import java.util.List;

public class Book {
    private int id;
    private String title;
    private String author;
    private String isbn;
    private int publicationYear;
    private int availableCopies;
    private List<Reader> currentReaders;

    public Book(int id, String title, String author, String isbn, int publicationYear, int availableCopies) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.availableCopies = availableCopies;
        this.currentReaders = new ArrayList<>();
    }

    // Getters and Setters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }
    public int getPublicationYear() { return publicationYear; }
    public int getAvailableCopies() { return availableCopies; }
    public List<Reader> getCurrentReaders() { return currentReaders; }

    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public void setPublicationYear(int publicationYear) { this.publicationYear = publicationYear; }
    public void setAvailableCopies(int availableCopies) { this.availableCopies = availableCopies; }
    public void setCurrentReaders(List<Reader> currentReaders) { this.currentReaders = currentReaders; }

    public void addCurrentReader(Reader reader) {
        this.currentReaders.add(reader);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", publicationYear=" + publicationYear +
                ", availableCopies=" + availableCopies +
                '}';
    }
}
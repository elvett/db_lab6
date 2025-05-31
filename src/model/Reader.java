package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Reader {
    private int id;
    private String name;
    private String email;
    private String phone;
    private LocalDate registrationDate;
    private List<Book> borrowedBooks;

    public Reader(int id, String name, String email, String phone, LocalDate registrationDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.registrationDate = registrationDate;
        this.borrowedBooks = new ArrayList<>();
    }

    // Getters and Setters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public LocalDate getRegistrationDate() { return registrationDate; }
    public List<Book> getBorrowedBooks() { return borrowedBooks; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setRegistrationDate(LocalDate registrationDate) { this.registrationDate = registrationDate; }
    public void setBorrowedBooks(List<Book> borrowedBooks) { this.borrowedBooks = borrowedBooks; }

    public void addBorrowedBook(Book book) {
        this.borrowedBooks.add(book);
    }

    @Override
    public String toString() {
        return "Reader{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", registrationDate=" + registrationDate +
                '}';
    }
}
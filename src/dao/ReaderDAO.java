package dao;

import model.Reader;
import model.Book;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReaderDAO {
    private Connection conn;

    public ReaderDAO(Connection conn) {
        this.conn = conn;
    }

    public void insert(Reader reader) throws SQLException {
        String sql = "INSERT INTO readers (name, email, phone, registration_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, reader.getName());
            stmt.setString(2, reader.getEmail());
            stmt.setString(3, reader.getPhone());
            stmt.setDate(4, Date.valueOf(reader.getRegistrationDate()));
            stmt.executeUpdate();
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    reader.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public void update(Reader reader) throws SQLException {
        String sql = "UPDATE readers SET name = ?, email = ?, phone = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, reader.getName());
            stmt.setString(2, reader.getEmail());
            stmt.setString(3, reader.getPhone());
            stmt.setInt(4, reader.getId());
            stmt.executeUpdate();
        }
    }

    public List<Reader> findAll() throws SQLException {
        List<Reader> readers = new ArrayList<>();
        String sql = "SELECT * FROM readers";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                readers.add(new Reader(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getDate("registration_date").toLocalDate()
                ));
            }
        }
        return readers;
    }

    public Reader findById(int id) throws SQLException {
        String sql = "SELECT * FROM readers WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Reader(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getDate("registration_date").toLocalDate()
                    );
                }
            }
        }
        return null;
    }

    public void borrowBook(int readerId, int bookId) throws SQLException {
        String sql = "INSERT INTO reader_books (reader_id, book_id, borrow_date) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, readerId);
            stmt.setInt(2, bookId);
            stmt.setDate(3, Date.valueOf(LocalDate.now()));
            stmt.executeUpdate();
        }
    }

    public void returnBook(int readerId, int bookId) throws SQLException {
        String sql = "UPDATE reader_books SET return_date = ? WHERE reader_id = ? AND book_id = ? AND return_date IS NULL";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(LocalDate.now()));
            stmt.setInt(2, readerId);
            stmt.setInt(3, bookId);
            stmt.executeUpdate();
        }
    }

    public List<Book> findBorrowedBooks(int readerId) throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT b.* FROM books b JOIN reader_books rb ON b.id = rb.book_id WHERE rb.reader_id = ? AND rb.return_date IS NULL";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, readerId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    books.add(new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("isbn"),
                        rs.getInt("publication_year"),
                        rs.getInt("available_copies")
                    ));
                }
            }
        }
        return books;
    }
}
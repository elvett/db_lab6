package dao;

import model.Book;
import model.Reader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    private Connection conn;

    public BookDAO(Connection conn) {
        this.conn = conn;
    }

    public void insert(Book book) throws SQLException {
        String sql = "INSERT INTO books (title, author, isbn, publication_year, available_copies) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getIsbn());
            stmt.setInt(4, book.getPublicationYear());
            stmt.setInt(5, book.getAvailableCopies());
            stmt.executeUpdate();
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    book.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public void update(Book book) throws SQLException {
        String sql = "UPDATE books SET title = ?, author = ?, isbn = ?, publication_year = ?, available_copies = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getIsbn());
            stmt.setInt(4, book.getPublicationYear());
            stmt.setInt(5, book.getAvailableCopies());
            stmt.setInt(6, book.getId());
            stmt.executeUpdate();
        }
    }

    public List<Book> findAll() throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
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
        return books;
    }

    public Book findById(int id) throws SQLException {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("isbn"),
                        rs.getInt("publication_year"),
                        rs.getInt("available_copies")
                    );
                }
            }
        }
        return null;
    }

    public List<Reader> findCurrentReaders(int bookId) throws SQLException {
        List<Reader> readers = new ArrayList<>();
        String sql = "SELECT r.* FROM readers r JOIN reader_books rb ON r.id = rb.reader_id WHERE rb.book_id = ? AND rb.return_date IS NULL";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookId);
            try (ResultSet rs = stmt.executeQuery()) {
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
        }
        return readers;
    }
}
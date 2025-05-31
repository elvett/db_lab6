# Реалізація об'єктно-реляційного відображення для бібліотечної системи

## 1. Створення бази даних MySQL

Було створено базу даних `library_db` у MySQL для зберігання даних.

```
CREATE DATABASE library_db;
USE library_db;

CREATE TABLE readers (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL,
  phone VARCHAR(20) NOT NULL,
  registration_date DATE NOT NULL
);

CREATE TABLE books (
  id INT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(200) NOT NULL,
  author VARCHAR(100) NOT NULL,
  isbn VARCHAR(13) NOT NULL,
  publication_year INT NOT NULL,
  available_copies INT NOT NULL
);

CREATE TABLE reader_books (
  reader_id INT,
  book_id INT,
  borrow_date DATE NOT NULL,
  return_date DATE,
  PRIMARY KEY (reader_id, book_id, borrow_date),
  FOREIGN KEY (reader_id) REFERENCES readers(id) ON DELETE CASCADE,
  FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE
);
```

## 2. Створення Java-проєкту

Для реалізації завдання створено консольний Java-проєкт без використання IDE. Усі дії виконувались через термінал Linux.

```
mkdir -p src/model src/dao src/test
```

**Структура проєкту:**

```
src/
├── model/      — bean-класи (модель таблиць)
├── dao/        — реалізація DAO
└── test/       — тестовий клас
```

## 3. Створення таблиці `readers`

У базі даних `library_db` створено таблицю `readers` зі структурою:

- `id` — унікальний ідентифікатор
- `name` — ім'я читача
- `email` — електронна пошта
- `phone` — номер телефону
- `registration_date` — дата реєстрації

```
CREATE TABLE readers (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL,
  phone VARCHAR(20) NOT NULL,
  registration_date DATE NOT NULL
);
```

## 4. Створення таблиці `books`

У базі даних `library_db` створено таблицю `books` зі структурою:

- `id` — унікальний ідентифікатор
- `title` — назва книги
- `author` — автор
- `isbn` — ISBN книги
- `publication_year` — рік публікації
- `available_copies` — кількість доступних примірників

```
CREATE TABLE books (
  id INT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(200) NOT NULL,
  author VARCHAR(100) NOT NULL,
  isbn VARCHAR(13) NOT NULL,
  publication_year INT NOT NULL,
  available_copies INT NOT NULL
);
```

## 5. Створення таблиці `reader_books`

У базі даних `library_db` створено таблицю `reader_books` зі структурою:

- `reader_id` — ідентифікатор читача
- `book_id` — ідентифікатор книги
- `borrow_date` — дата позичення
- `return_date` — дата повернення

```
CREATE TABLE reader_books (
  reader_id INT,
  book_id INT,
  borrow_date DATE NOT NULL,
  return_date DATE,
  PRIMARY KEY (reader_id, book_id, borrow_date),
  FOREIGN KEY (reader_id) REFERENCES readers(id) ON DELETE CASCADE,
  FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE
);
```

## 6. Створення bean-класу

Створено клас `Reader` (у `src/model/Reader.java`), який описує один запис таблиці `readers`.

```java
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
```

## 7. Створення bean-класу

Створено клас `Book` (у `src/model/Book.java`), який описує один запис таблиці `books`.

```java
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
```

## 8. Розробка DAO-інфраструктури

У пакеті `src/dao/` реалізовано клас `ReaderDAO.java`, який містить методи для:

* вставки нового читача
* оновлення читача
* пошуку читача
* позичення книги читачем
* пошуку книг читача

```java
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
```

## 9. Розробка DAO-інфраструктури

У пакеті `src/dao/` реалізовано клас `BookDAO.java`, який містить методи для:

* вставки нової книги
* оновлення книги
* пошуку книги
* пошуку читачів книги

```java
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
```

## 10. Тестування DAO

У `src/test/TestDAO.java` створено тестовий клас `TestDAO`, який:

* підключається до бази `library_db`
* використовує `ReaderDAO` та `BookDAO` для роботи з базою

```java
package test;

import dao.BookDAO;
import dao.ReaderDAO;
import model.Book;
import model.Reader;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class TestDAO {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/library_db";
        String user = "user";
        String password = "user";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            // Initialize DAOs
            ReaderDAO readerDAO = new ReaderDAO(conn);
            BookDAO bookDAO = new BookDAO(conn);

            // Create some readers
            Reader reader1 = new Reader(0, "John Smith", "john@email.com", "+1234567890", LocalDate.now());
            Reader reader2 = new Reader(1, "Jane Doe", "jane@email.com", "+0987654321", LocalDate.now());
            readerDAO.insert(reader1);
            readerDAO.insert(reader2);

            // Create some books
            Book book1 = new Book(0, "The Great Gatsby", "F. Scott Fitzgerald", "9780743273565", 1925, 5);
            Book book2 = new Book(1, "1984", "George Orwell", "9780451524935", 1949, 3);
            bookDAO.insert(book1);
            bookDAO.insert(book2);

            // Borrow books
            readerDAO.borrowBook(reader1.getId(), book1.getId());
            readerDAO.borrowBook(reader1.getId(), book2.getId());
            readerDAO.borrowBook(reader2.getId(), book1.getId());

            // Test retrieving data
            System.out.println("All Readers:");
            List<Reader> readers = readerDAO.findAll();
            readers.forEach(System.out::println);

            System.out.println("\nAll Books:");
            List<Book> books = bookDAO.findAll();
            books.forEach(System.out::println);

            // Test finding borrowed books for a reader
            System.out.println("\nBooks borrowed by John Smith:");
            List<Book> johnsBooks = readerDAO.findBorrowedBooks(reader1.getId());
            johnsBooks.forEach(System.out::println);

            // Test finding current readers for a book
            System.out.println("\nCurrent readers of The Great Gatsby:");
            List<Reader> gatsbyReaders = bookDAO.findCurrentReaders(book1.getId());
            gatsbyReaders.forEach(System.out::println);

            // Return a book
            readerDAO.returnBook(reader1.getId(), book1.getId());
            System.out.println("\nAfter returning The Great Gatsby:");
            johnsBooks = readerDAO.findBorrowedBooks(reader1.getId());
            johnsBooks.forEach(System.out::println);

            // Update a reader
            reader1.setEmail("john.smith@newemail.com");
            readerDAO.update(reader1);
            System.out.println("\nUpdated reader:");
            System.out.println(readerDAO.findById(reader1.getId()));

            // Update a book
            book2.setAvailableCopies(2);
            bookDAO.update(book2);
            System.out.println("\nUpdated book:");
            System.out.println(bookDAO.findById(book2.getId()));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```

## 11. Запуск програми

**Компіляція:**

```bash
javac -cp "lib/mysql-connector-j-<version>.jar" src/**/*.java
```

**Запуск:**

```bash
java -cp "lib/mysql-connector-j-<version>.jar:src" test.TestDAO
```

**Перевірка результатів:**

```
All Readers:
Reader{id=1, name='John Smith', email='john@email.com', phone='+1234567890', registrationDate=2024-03-20}
Reader{id=2, name='Jane Doe', email='jane@email.com', phone='+0987654321', registrationDate=2024-03-20}

All Books:
Book{id=1, title='The Great Gatsby', author='F. Scott Fitzgerald', isbn='9780743273565', publicationYear=1925, availableCopies=5}
Book{id=2, title='1984', author='George Orwell', isbn='9780451524935', publicationYear=1949, availableCopies=3}

Books borrowed by John Smith:
Book{id=1, title='The Great Gatsby', author='F. Scott Fitzgerald', isbn='9780743273565', publicationYear=1925, availableCopies=5}
Book{id=2, title='1984', author='George Orwell', isbn='9780451524935', publicationYear=1949, availableCopies=3}

Current readers of The Great Gatsby:
Reader{id=1, name='John Smith', email='john@email.com', phone='+1234567890', registrationDate=2024-03-20}
Reader{id=2, name='Jane Doe', email='jane@email.com', phone='+0987654321', registrationDate=2024-03-20}

After returning The Great Gatsby:
Book{id=2, title='1984', author='George Orwell', isbn='9780451524935', publicationYear=1949, availableCopies=3}

Updated reader:
Reader{id=1, name='John Smith', email='john.smith@newemail.com', phone='+1234567890', registrationDate=2024-03-20}

Updated book:
Book{id=2, title='1984', author='George Orwell', isbn='9780451524935', publicationYear=1949, availableCopies=2}
```

## 12. Висновок

Успішно реалізовано DAO шаблон для системи управління бібліотекою з використанням Java та MySQL.
 
Створено базу даних library_db з трьома взаємопов'язаними таблицями:
* readers - для зберігання інформації про читачів
* books - для зберігання інформації про книги
* reader_books - для реалізації зв'язків між читачами та книгами

Розроблено об'єктну модель:
* Клас Reader з усіма необхідними полями та методами
* Клас Book з підтримкою інформації про книгу та доступність

Реалізовано повноцінні DAO класи:
* ReaderDAO з методами CRUD та управління позиченням книг
* BookDAO з методами CRUD та управління читачами

Забезпечено двосторонній зв'язок між об'єктами:
* Читач знає свої позичені книги
* Книга знає своїх поточних читачів

Протестовано всі основні операції:
* Додавання/редагування читачів та книг
* Позичення та повернення книг
* Отримання пов'язаних даних

Підтверджено коректну роботу програми з реальною базою даних MySQL.
Дана реалізація демонструє ефективне використання патерну DAO для створення масштабованої системи управління бібліотекою з чітким розділенням логіки доступу до даних та бізнес-логіки.
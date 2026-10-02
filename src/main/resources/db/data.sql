INSERT INTO users (username, password, name, email) VALUES
 ('jmanrique', 'pass123', 'Julian Manrique', 'j@example.com'),
 ('gizaguirre', 'pass123', 'Gabriel Izaguirre', 'g@example.com'),
 ('amaradiaga', 'pass123', 'Anthony Maradiaga-Diaz', 'a@example.com');

INSERT INTO authors (first_name, last_name, biography, publisher) VALUES
 ('Robert', 'Martin', 'Software craftsman and author.', 'Prentice Hall'),
 ('Andrew', 'Tanenbaum', 'Computer science professor.', 'Pearson');

INSERT INTO books (isbn, name, description, price, author_id, genre, publisher, year_published, copies_sold) VALUES
 ('9780132350884', 'Clean Code', 'A handbook of agile software craftsmanship.', 39.99, 1, 'Programming', 'Prentice Hall', 2008, 50000),
 ('9780134494166', 'Clean Architecture', 'A guide to software structure and design.', 34.99, 1, 'Programming', 'Prentice Hall', 2017, 30000),
 ('9780133591620', 'Modern Operating Systems', 'Operating system concepts and design.', 89.99, 2, 'Systems', 'Pearson', 2014, 20000);

INSERT INTO ratings (user_id, book_id, rating) VALUES (1,1,5),(2,1,4),(3,2,5),(1,3,3);
INSERT INTO comments (user_id, book_id, comment) VALUES
 (1,1,'Great read for any developer.'),
 (2,1,'A bit opinionated but useful.'),
 (3,3,'Dense but thorough.');
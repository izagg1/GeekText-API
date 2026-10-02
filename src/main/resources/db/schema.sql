CREATE TABLE users (
  id BIGSERIAL PRIMARY KEY,
  username VARCHAR(50) UNIQUE NOT NULL,
  password VARCHAR(100) NOT NULL,
  name VARCHAR(100),
  email VARCHAR(100),
  home_address VARCHAR(200)
);

CREATE TABLE credit_cards (
  id BIGSERIAL PRIMARY KEY,
  user_id BIGINT NOT NULL REFERENCES users(id),
  card_number VARCHAR(19) NOT NULL,
  expiry DATE NOT NULL
);

CREATE TABLE authors (
  id BIGSERIAL PRIMARY KEY,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  biography TEXT,
  publisher VARCHAR(100)
);

CREATE TABLE books (
  id BIGSERIAL PRIMARY KEY,
  isbn VARCHAR(20) UNIQUE NOT NULL,
  name VARCHAR(200) NOT NULL,
  description TEXT,
  price NUMERIC(8,2) NOT NULL,
  author_id BIGINT NOT NULL REFERENCES authors(id),
  genre VARCHAR(50),
  publisher VARCHAR(100),
  year_published INT,
  copies_sold INT NOT NULL DEFAULT 0
);

CREATE TABLE ratings (
  id BIGSERIAL PRIMARY KEY,
  user_id BIGINT NOT NULL REFERENCES users(id),
  book_id BIGINT NOT NULL REFERENCES books(id),
  rating INT NOT NULL CHECK (rating BETWEEN 1 AND 5),
  created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE comments (
  id BIGSERIAL PRIMARY KEY,
  user_id BIGINT NOT NULL REFERENCES users(id),
  book_id BIGINT NOT NULL REFERENCES books(id),
  comment TEXT NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE cart_items (
  user_id BIGINT REFERENCES users(id),
  book_id BIGINT REFERENCES books(id),
  PRIMARY KEY (user_id, book_id)
);

CREATE TABLE wishlists (
  id BIGSERIAL PRIMARY KEY,
  user_id BIGINT NOT NULL REFERENCES users(id),
  name VARCHAR(100) NOT NULL,
  UNIQUE (user_id, name)
);

CREATE TABLE wishlist_books (
  wishlist_id BIGINT REFERENCES wishlists(id),
  book_id BIGINT REFERENCES books(id),
  PRIMARY KEY (wishlist_id, book_id)
);
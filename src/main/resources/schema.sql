CREATE TABLE IF NOT EXISTS items (
    barcode TEXT PRIMARY KEY,
    name TEXT NOT NULL,
    category TEXT NOT NULL,
    price TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS inventory (
    barcode TEXT PRIMARY KEY,
    quantity INTEGER NOT NULL,
    FOREIGN KEY (barcode) REFERENCES items(barcode)
);

CREATE TABLE IF NOT EXISTS orders (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    subtotal TEXT NOT NULL,
    discount TEXT NOT NULL,
    total TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS order_items (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    order_id INTEGER NOT NULL,
    barcode TEXT NOT NULL,
    name TEXT NOT NULL,
    quantity INTEGER NOT NULL,
    price TEXT NOT NULL,
    subtotal TEXT NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id)
);

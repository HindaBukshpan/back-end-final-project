CREATE TABLE users (
id INT AUTO_INCREMENT,
first_name VARCHAR(255) NOT NULL,
last_name VARCHAR(255) NOT NULL,
email VARCHAR(255) UNIQUE NOT NULL,
phone VARCHAR(20),
address VARCHAR(255),
username VARCHAR(255) NOT NULL,
password VARCHAR(255) NOT NULL,
role VARCHAR(50) DEFAULT 'USER',
PRIMARY KEY (id)
);

CREATE TABLE items(
id INT AUTO_INCREMENT,
name VARCHAR(255) NOT NULL,
description TEXT DEFAULT NULL,
price DECIMAL(10,2) NOT NULL,
sale BOOLEAN NOT NULL DEFAULT FALSE,
stock INT NOT NULL,
image_url TEXT NOT NULL,
PRIMARY KEY(id)
);

CREATE TABLE orders(
id BIGINT AUTO_INCREMENT,
user_id INT NOT NULL,
order_date DATE NOT NULL DEFAULT CURRENT_DATE,
total_price DECIMAL(10,2) NOT NULL,
shipping_address VARCHAR(225) NOT NULL,
status VARCHAR(50) DEFAULT 'PENDING',
PRIMARY KEY(id),
FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE ordered_items(
order_id INT NOT NULL,
item_id INT NOT NULL,
quantity INT NOT NULL DEFAULT 1,
total_price_per_item DECIMAL(10,2) NOT NULL,
PRIMARY KEY(order_id, item_id),
FOREIGN KEY(order_id) REFERENCES orders(id) ON DELETE CASCADE,
FOREIGN KEY(item_id) REFERENCES items(id) ON DELETE CASCADE
);

CREATE TABLE wish_list (
id INT AUTO_INCREMENT PRIMARY KEY,
user_id INT NOT NULL,
item_id INT NOT NULL,
UNIQUE (user_id, item_id),
FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
FOREIGN KEY (item_id) REFERENCES items(id) ON DELETE CASCADE
);

INSERT INTO users (first_name, last_name, email, phone, address, username, password, role) VALUES
('Miki', 'Gabay', E'mikigabay@gmail.com', '0585236376', '45 Ben Eliezer St', 'mikigabay', '$2a$10$24P9JHWZJm8yRsJRpP4a.e11OvU9ynMvz6XAKJOrxl8Nhph7mojJ2', 'USER'),
('Amitay', 'Gabay', E'amitaygabay1@gmail.com', '0504380333', '38 Erez St', 'amitaygabay', '$2a$10$K78Qy75RrDNQcAolPojuM.sI.otXpP23xhZYJ7p2fXrIMoI.k2ehO', 'ADMIN'),
('Hinda', 'Bukshpan', E'hynda4@gmail.com', '0533178921', 'harav sah 64', 'hinda', '$2a$10$TjUr0ZD6jDQHMleX7PSBZu82Rv35y.XHV6peFR0JSN8TPi/yHuuXm', 'USER');


INSERT INTO items (name, description, price, stock, image_url) VALUES
('Nursing Chair', 'Comfortable chair designed for nursing mothers, offering back and arm support.', 200, 10, 'https://d3m9l0v76dty0.cloudfront.net/system/photos/14252322/large/3fe935d54645274c4691f422ff5a535b.jpg'),
('Sofa', 'A sturdy wooden table for dining, suitable for 6-8 people.', 350, 8, 'https://vdivani.co.il/wp-content/uploads/2023/12/WhatsApp-Image-2023-12-07-at-10.46.07.jpeg'),
('Dining Table', 'tall', 250, 19, 'data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxIQEA8PEBEPEA8NDw8NEA8PEBAQDw0QFREWFhYSFRUYHSggGBolGxUVITEhZ'),
('Living Room Table', 'A stylish table for living rooms, perfect for coffee and snacks.', 250, 12, 'data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxIQEA8PEBEPEA8NDw8NEA8PEBAQDw0QFREWFh8QGS0fAABEAAAAP/Z'),
('Patio Table Glass', 'Durable glass table for outdoor patios.', 180, 20, 'https://ae-pic-a1.aliexpress-media.com/kf/S88f00c2c20bc47c1965c7d06dfa2c4f9E.jpg_960x960q75.jpg_.avif'),
('Living Room Chairs', 'Comfortable chairs designed for the living room.', 120, 30, 'https://img.freepik.com/free-photo/closeup-shot-dark-brown-leather-armchair_181624-22622.jpg?t=st=1741235764~exp=1741239364~hmac=61270c7873e1d36c9033e9fe14aaf8f3229c2494592a31a175599cb3cd320820&w=740'),
('Kitchen Chairs', 'Set of sturdy chairs designed for the kitchen or dining area.', 90, 25, 'https://img.freepik.com/free-vector/chair_1308-82630.jpg?t=st=1741235714~exp=1741239314~hmac=d7139e0fde3c435f74417f53ff7d7c5c2a6090dbdbffb1332f597a2dcfcd072b&w=740'),
('Adult Bed', 'A full-size bed for adults, with a comfortable mattress.', 500, 5, 'https://img.freepik.com/free-psd/teal-velvet-upholstered-bed-luxurious-bedroom-design_632498-24118.jpg?t=st=1741235672~exp=1741239272~hmac=887335df446c38ef8cb979dfe9cbcc68ec1ee3a1c028042167ee07efd1c9b232&w=900'),
('Childrens Bed', 'A smaller bed designed for children, with a safe and cozy mattress.', 350, 10, 'https://img.freepik.com/premium-psd/wooden-bed-with-canopy_1074774-36152.jpg?w=900'),
('Crib', 'A safe and comfortable crib for babies, with adjustable mattress height.', 250, 10, 'https://img.freepik.com/free-psd/3d-rendering-furniture-icon_23-2151445372.jpg?t=st=1741235561~exp=1741239161~hmac=f5f885795b066cda9eeeab6e947288b64ab787288a16c740c7da920d7dba0d1b&w=900');




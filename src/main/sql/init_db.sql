DROP TABLE IF EXISTS supplier;
DROP TABLE IF EXISTS product_category;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS cart;
DROP TABLE IF EXISTS line_item;
DROP TABLE IF EXISTS users;


CREATE TABLE supplier
(
    id          SERIAL PRIMARY KEY  NOT NULL,
    name        VARCHAR(255) UNIQUE NOT NULL,
    description VARCHAR(255)        NOT NULL
);

CREATE TABLE product_category
(
    id          SERIAL PRIMARY KEY  NOT NULL,
    name        VARCHAR(255) UNIQUE NOT NULL,
    description VARCHAR(255)        NOT NULL,
    department  VARCHAR(255)        NOT NULL
);

CREATE TABLE product
(
    id               SERIAL PRIMARY KEY  NOT NULL,
    name             VARCHAR(255) UNIQUE NOT NULL,
    description      VARCHAR(255)        NOT NULL,
    default_price    DOUBLE PRECISION    NOT NULL,
    currency_string  VARCHAR(255)        NOT NULL,
    supplier         INTEGER             NOT NULL,
    product_category INTEGER             NOT NULL
);

CREATE TABLE cart
(
    id           SERIAL PRIMARY KEY NOT NULL,
    line_item_id INTEGER            NOT NULL
);

CREATE TABLE line_item
(
    line_item_id SERIAL PRIMARY KEY NOT NULL,
    order_id     INTEGER            NOT NULL,
    quantity     INTEGER            NOT NULL,
    item_id      INTEGER            NOT NULL,
    currency     VARCHAR(255)       NOT NULL,
    unit_price   DOUBLE PRECISION   NOT NULL,
    product      DOUBLE PRECISION   NOT NULL,
    total        VARCHAR(255)       NOT NULL
);

CREATE TABLE users
(
    id          SERIAL PRIMARY KEY  NOT NULL,
    name        VARCHAR(255) UNIQUE NOT NULL,
    password    varchar(255)        NOT NULL
);




-- ALTER TABLE product
--     ADD CONSTRAINT fk_product_supplier_id FOREIGN KEY (supplier_id) REFERENCES supplier(id);
--
-- ALTER TABLE product
--     ADD CONSTRAINT fk_product_product_category_id FOREIGN KEY (product_category_id) REFERENCES product_category(id);


INSERT INTO supplier (name, description)
VALUES ('PenguinBooks',
        'Committed to publishing great books, connecting readers and authors globally, and spreading the love of reading.');
INSERT INTO supplier (name, description)
VALUES ('HarperCollins', 'Publishing great authors since 1817.');
INSERT INTO supplier (name, description)
VALUES ('Hachette',
        'Publishing authors who have a purpose, a story to tell, and an unusual talent for making readers care about it.');

INSERT INTO product_category (name, department, description)
VALUES ('Kids books', 'Books', 'Spark the love for reading in the new generations!');
INSERT INTO product_category (name, department, description)
VALUES ('Parenting books', 'Books', 'No one is born knowing this and everyone can learn!');

INSERT INTO product (name, description, default_price, currency_string, supplier, product_category)
VALUES ('The Very Hungry Caterpillar',
        'he all-time classic picture book, from generation to generation, sold somewhere in the world every 30 seconds!',
        5.5, 'USD', 1, 1);
INSERT INTO product (name, description, default_price, currency_string, supplier, product_category)
VALUES ('Goodnight Moon',
        'The quiet poetry of the words and the gentle, lulling illustrations combine to make a perfect book for the end of the day.',
        5.5, 'USD', 2, 1);
INSERT INTO product (name, description, default_price, currency_string, supplier, product_category)
VALUES ('Dr. Seuss Beginner Book Collection',
        'Perfect for inspiring a love of reading, and with five books in one super giftable set, it will complete any beginning reader shelf!',
        25, 'USD', 1, 1);
INSERT INTO product (name, description, default_price, currency_string, supplier, product_category)
VALUES ('I Love You to the Moon and Back', 'Show a child just how strong your love is every minute of the day!', 3.5,
        'USD', 3, 1);
INSERT INTO product (name, description, default_price, currency_string, supplier, product_category)
VALUES ('Go the F**k to Sleep', 'A Reader''s Digest \"25 Funniest Books of All Time\"', 6.5, 'USD', 3,
        1);
INSERT INTO product (name, description, default_price, currency_string, supplier, product_category)
VALUES ('The Montessori Baby: A Parents Guide to Nurturing Your Baby with Love',
        'The Montessori Baby shows how to raise your baby from birth to age one with love, respect, insight, and a surprising sense of calm.',
        10.5, 'USD', 2, 2);
INSERT INTO product (name, description, default_price, currency_string, supplier, product_category)
VALUES ('The Montessori Toddler: A Parents Guide to Raising a Curious and Responsible Human Being',
        'Turn your home into a Montessori home—and become a more mindful, attentive, and easygoing parent.', 11.89,
        'USD', 2, 2);
INSERT INTO product (name, description, default_price, currency_string, supplier, product_category)
VALUES ('No-Drama Discipline',
        'The pioneering experts behind The Whole-Brain Child and The Yes Brain tackle the ultimate parenting challenge: discipline.',
        11.5, 'USD', 3, 2);
INSERT INTO product (name, description, default_price, currency_string, supplier, product_category)
VALUES ('Positive Behavior Activities for Kids', 'Fun activities that encourage positive behavior in kids ages 4 to 8',
        12.5, 'USD', 1, 2);
INSERT INTO product (name, description, default_price, currency_string, supplier, product_category)
VALUES ('Raising Good Humans', 'A wise and fresh approach to mindful parenting.', 6.5, 'USD', 2,
        2);
INSERT INTO product (name, description, default_price, currency_string, supplier, product_category)
VALUES ('How to Be a Happier Parent',
        'An encouraging guide to helping parents find more happiness in their day-to-day family life.', 18, 'USD',
        3, 2);




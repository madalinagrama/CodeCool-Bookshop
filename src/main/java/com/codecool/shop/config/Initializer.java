package com.codecool.shop.config;

import com.codecool.shop.dao.CategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.CategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Initializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ProductDao productDataStore = ProductDaoMem.getInstance();
        CategoryDao productCategoryDataStore = CategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();


        // setting up a new supplier
        Supplier penguin = new Supplier("PenguinBooks",
                "Committed to publishing great books, connecting readers and authors globally, and spreading the love of reading.");
        supplierDataStore.add(penguin);
        Supplier harper = new Supplier("HarperCollins", "Publishing great authors since 1817.");
        supplierDataStore.add(harper);
        Supplier hachette = new Supplier("Hachette",
                "Publishing authors who have a purpose, a story to tell, and an unusual talent for making readers care about it.");
        supplierDataStore.add(hachette);

        // setting up a new product category
        ProductCategory kids = new ProductCategory("Kids' books", "Books",
                "Spark the love for reading in the new generations!");
        productCategoryDataStore.add(kids);
        ProductCategory parents = new ProductCategory("Parenting books", "Books",
                "No one's born knowing this and everyone can learn.");
        productCategoryDataStore.add(parents);

        // setting up products and printing it

        productDataStore.add(new Product("The Very Hungry Caterpillar", 5.5f, "USD",
                "The all-time classic picture book, from generation to generation, sold somewhere in the world every 30 seconds!",
                kids, hachette));
        productDataStore.add(new Product("Goodnight Moon", 5.5f, "USD",
                "The quiet poetry of the words and the gentle, lulling illustrations combine to make a perfect book for the end of the day.",
                kids, penguin));
        productDataStore.add(new Product("Dr. Seuss's Beginner Book Collection", 25, "USD",
                "Perfect for inspiring a love of reading, and with five books in one super giftable set, it will complete any beginning reader's shelf!",
                kids, penguin));
        productDataStore.add(new Product("I Love You to the Moon and Back", 3.5f, "USD",
                "Show a child just how strong your love is every minute of the day! ", kids, harper));
        productDataStore.add(new Product("Go the F**k to Sleep ", 6.5f, "USD",
                "A Reader's Digest \"25 Funniest Books of All Time\"", kids, harper));
        productDataStore.add(new Product(
                "The Montessori Baby: A Parent's Guide to Nurturing Your Baby with Love", 10.5f, "USD",
                "The Montessori Baby shows how to raise your baby from birth to age one with love, respect, insight, and a surprising sense of calm.",
                parents, hachette));
        productDataStore.add(new Product(
                "The Montessori Toddler: A Parent's Guide to Raising a Curious and Responsible Human Being",
                11.89f, "USD",
                "Turn your home into a Montessori home—and become a more mindful, attentive, and easygoing parent.",
                parents, penguin));
        productDataStore.add(new Product("No-Drama Discipline", 11.5f, "USD",
                "The pioneering experts behind The Whole-Brain Child and The Yes Brain tackle the ultimate parenting challenge: discipline.",
                parents, harper));
        productDataStore.add(new Product("Positive Behavior Activities for Kids", 12.5f, "USD",
                "Fun activities that encourage positive behavior in kids ages 4 to 8", parents, hachette));
        productDataStore.add(new Product("Raising Good Humans", 6.5f, "USD",
                "A wise and fresh approach to mindful parenting.", parents, harper));
        productDataStore.add(new Product("How to Be a Happier Parent", 18, "USD",
                "An encouraging guide to helping parents find more happiness in their day-to-day family life.",
                parents, hachette));
    }
}
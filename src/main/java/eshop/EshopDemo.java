package eshop;

import eshop.model.Category;
import eshop.model.Product;
import eshop.service.CategoryService;
import eshop.service.ProductService;
import eshop.service.impl.CategoryServiceImpl;
import eshop.service.impl.ProductServiceImpl;

import java.util.List;
import java.util.Scanner;

public class EshopDemo implements Commands {

    private static final Scanner scanner = new Scanner(System.in);
    private static final CategoryService categoryService = new CategoryServiceImpl();
    private static final ProductService productService = new ProductServiceImpl();

    public static void main(String[] args) {
        process();
    }

    private static void process() {
        boolean flag = true;
        while (flag) {
            commands();
            String command = scanner.nextLine();
            switch (command) {
                case EXIT -> flag = false;
                case ADD_CATEGORY -> addCategory();
                case REMOVE_CATEGORY -> removeCategory();
                case ADD_PRODUCT -> addProduct();
                case EDIT_PRODUCT_BY_ID -> editProductById();
                case DELETE_PRODUCT_BY_ID -> removeProductById();
                case PRINT_PRODUCT_COUNT -> printProductCount();
                case PRINT_PRODUCT_WITH_MIN_PRICE -> printProductWithMinPrice();
                case PRINT_PRODUCT_WITH_MAX_PRICE -> printProductWithMaxPrice();
                case AVERAGE_PRICE -> printAveragePriceWithProduct();
                case PRINT_ALL_PRODUCTS -> printAllProducts();
            }
        }
    }

    private static void commands() {
        System.out.println("Please input " + EXIT + " to exit");
        System.out.println("PLease input " + ADD_CATEGORY + " to add category");
        System.out.println("PLease input " + REMOVE_CATEGORY + " to remove category");
        System.out.println("Please input " + ADD_PRODUCT + " to add product");
        System.out.println("Please input " + EDIT_PRODUCT_BY_ID + " to edit product by ID");
        System.out.println("PLease input " + DELETE_PRODUCT_BY_ID + " to delete product by ID");
        System.out.println("PLease input " + PRINT_PRODUCT_COUNT + " to print product count");
        System.out.println("PLease input " + PRINT_PRODUCT_WITH_MIN_PRICE + " to print product with min price");
        System.out.println("PLease input " + PRINT_PRODUCT_WITH_MAX_PRICE + " to print product with max price");
        System.out.println("PLease input " + AVERAGE_PRICE + " to print average price");
        System.out.println("PLease input " + PRINT_ALL_PRODUCTS + " to print all products");
    }

    private static void addCategory() {
        System.out.println("Please input category name");
        String categoryName = scanner.nextLine();
        if (categoryName != null) {
            Category category = new Category();
            category.setName(categoryName);
            categoryService.add(category);
        }
    }

    private static void removeCategory() {
        System.out.println("Please input category id");
        printAllCategories();
        try {
            String categoryId = scanner.nextLine();
            if (categoryId != null) {
                Category category = categoryService.findById(Integer.parseInt(categoryId));
                if (category != null) {
                    categoryService.removeCategoryById(category.getId());
                } else {
                    System.out.println("Category not found");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void addProduct() {
        System.out.println("Please input category id for product:");
        printAllCategories();
        String categoryId = scanner.nextLine();

        try {
            if (categoryId != null && !categoryId.isEmpty()) {
                Category category = categoryService.findById(Integer.parseInt(categoryId));

                if (category != null) {
                    System.out.println("Please input product name, description, price, quantity (separated by commas):");
                    String[] productStr = scanner.nextLine().split(",");
                    if (productStr.length == 4) {
                        try {
                            Product product = Product.builder()
                                    .name(productStr[0].trim())
                                    .description(productStr[1].trim())
                                    .price(Double.parseDouble(productStr[2].trim()))
                                    .quantity(Integer.parseInt(productStr[3].trim()))
                                    .category(category)
                                    .build();

                            productService.addProduct(product);
                            System.out.println("Product added successfully.");
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input for ID, price, or quantity. Please input valid numbers.");
                        }
                    } else {
                        System.out.println("Invalid input format. Please provide exactly 4 values separated by commas.");
                    }
                } else {
                    System.out.println("Category not found.");
                }
            } else {
                System.out.println("Category ID cannot be empty.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid symbol! Please input only numbers for category ID.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
    private static void printAllCategories() {
        List<Category> categories = categoryService.findAll();
        for (Category category : categories) {
            System.out.println(category.getId() + " " + category.getName());
        }
    }

    private static void removeProductById() {
        List<Product> products = productService.getProducts();

        if (products != null) {
            for (Product product : products) {
                System.out.println(product.getId() + " " + product.getName());
            }
            try {
                System.out.println("Please input product id");
                String productId = scanner.nextLine();
                if (!productId.isEmpty()) {
                    productService.removeProduct(Integer.parseInt(productId));
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid symbol please input only number");
            }
        }else {
            System.out.println("Product list is empty.");
        }

    }

    private static void editProductById() {
        try {
            System.out.println("Please input product id:");
            String productId = scanner.nextLine();

            if (!productId.isEmpty()) {
                Product product = productService.getProductById(Integer.parseInt(productId));

                if (product != null) {
                    System.out.println("Update category? (yes or no)");
                    String command = scanner.nextLine();

                    if (command.equalsIgnoreCase("yes")) { // Игнорируем регистр
                        printAllCategories();
                        System.out.println("Please input new category id:");
                        String categoryId = scanner.nextLine();

                        if (!categoryId.isEmpty()) {
                            Category category = categoryService.findById(Integer.parseInt(categoryId));
                            if (category != null) {
                                product.setCategory(category);
                                System.out.println("Category updated successfully.");
                            } else {
                                System.out.println("Category not found.");
                            }
                        }
                    }
                    System.out.println("Please input product name, description, price, quantity (separated by space):");
                    String[] productStr = scanner.nextLine().split(",");

                    if (productStr.length == 4) {
                        String newName = productStr[0];
                        String newDescription = productStr[1];
                        double newPrice;
                        int newQuantity;
                        try {
                            newPrice = Double.parseDouble(productStr[2]);
                            newQuantity = Integer.parseInt(productStr[3]);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input for price or quantity. Please input valid numbers.");
                            return;
                        }
                        product.setName(newName);
                        product.setDescription(newDescription);
                        product.setPrice(newPrice);
                        product.setQuantity(newQuantity);

                        System.out.println("Product updated successfully.");
                    } else {
                        System.out.println("Invalid input format. Please input exactly 4 values.");
                    }
                } else {
                    System.out.println("Product not found.");
                }
            } else {
                System.out.println("Product ID cannot be empty.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid symbol. Please input only numbers.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private static void printProductCount() {
        System.out.println("Product count: " + productService.productCount());
    }

    private static void printAllProducts() {
        List<Product> products = productService.getProducts();
        for (Product product : products) {
            System.out.println(product);
        }
    }

    private static void printProductWithMaxPrice() {
        Product product = productService.getProductWithMinPrice();
        if (product != null) {
            System.out.println("Product name: " + product.getName() + " price: " + product.getPrice());
        }else {
            System.out.println("Product not found.");
        }

    }

    private static void printProductWithMinPrice() {
        Product product = productService.getProductWithMaxPrice();
        System.out.println("Product name: " + product.getName() + " price: " + product.getPrice());
    }

    private static void printAveragePriceWithProduct() {
        System.out.println("This is a average price " + productService.averagePrice());
    }

}

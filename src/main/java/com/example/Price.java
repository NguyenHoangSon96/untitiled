package com.example;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Price {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<Product> products = convertProducts();
        CompletableFuture<Void> marginFuture = setMarginFuture(products);
        marginFuture.get();
        List<CompletableFuture<Map<String, BigDecimal>>> completableFutures = new ArrayList<>();
        for (String group : getUniqueGroups(products)) {
            CompletableFuture<Map<String, BigDecimal>> completableFuture = CompletableFuture.supplyAsync(() -> calculateAvegragePriceGroup(group, products));
            completableFutures.add(completableFuture);
        }

        List<Map<String, BigDecimal>> results = completableFutures.stream().map(CompletableFuture::join).collect(Collectors.toList());
        System.out.println(results); // [{G1=37.5}, {G2=124.5}, {G3=116.1}]
    }

    public static Set<String> getUniqueGroups(List<Product> products) {
        Set<String> set = new HashSet<>();
        for (Product product : products) {
            set.add(product.group);
        }
        return set;
    }

    private static Map<String, Double> convertMargins() {
        Map<String, String> m = new HashMap<>();
        m.put("C1", "20%");
        m.put("C2", "30%");
        m.put("C3", "0.4");
        m.put("C4", "50%");
        m.put("C5", "0.6");

        Map<String, Double> margins = new HashMap<>();
        m.forEach((k, v) -> {
            var marginStr = v;
            if (v.contains("%")) {
                marginStr = v.replace("%", "");
                margins.put(k, Double.parseDouble(marginStr) / 100);
            } else {
                margins.put(k, Double.parseDouble(v));
            }
        });

        return margins;
    }

    public static List<Product> convertProducts() {
        List<List<Object>> p = List.of(
                List.of("A", "G1", 20.1),
                List.of("B", "G2", 98.4),
                List.of("C", "G1", 49.7),
                List.of("D", "G3", 35.8),
                List.of("E", "G3", 105.5),
                List.of("F", "G1", 55.2),
                List.of("G", "G1", 12.7),
                List.of("H", "G3", 88.6),
                List.of("I", "G1", 5.2),
                List.of("J", "G2", 72.4)
        );

        List<Product> products = new ArrayList<>();
        for (List<Object> o : p) {
            products.add(new Product((String) o.get(0), (String) o.get(1), (Double) o.get(2)));
        }
        return products;
    }

    public static List<Category> convertCategories() {
        List<Object> cWithNull = new ArrayList<>();
        cWithNull.add("C5");
        cWithNull.add(100);
        cWithNull.add(null);
        List<List<Object>> c = new ArrayList<>(List.of(
                List.of("C3", 50, 75),
                List.of("C4", 75, 100),
                List.of("C2", 25, 50),
                List.of("C1", 0, 25),
                cWithNull
        ));


        List<Category> categories = new ArrayList<>();
        for (List<Object> o : c) {
            categories.add(new Category((String) o.get(0), (Integer) o.get(1), o.get(2) != null ? (Integer) o.get(2) : null));
        }

        return categories;
    }

    public static Map<String, BigDecimal> calculateAvegragePriceGroup(String groupName, List<Product> products) {
        var price = Double.valueOf(0);
        var countGroup = 0;
        for (Product product : products) {
            if (Objects.equals(product.group, groupName)) {
                price += product.cost * (1 + product.margin);
                countGroup++;
            }
        }
        Map<String, BigDecimal> results = new HashMap<>();
        results.put(groupName, BigDecimal.valueOf(price / countGroup).setScale(1, RoundingMode.HALF_UP));
        return results;
    }

    public static CompletableFuture<Void> setMarginFuture(List<Product> products) {
        List<Category> categories = convertCategories();

        List<CompletableFuture<Void>> completableFutures = new ArrayList<>();
        for (Category category : categories) {
            CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
                setMarginByCategory(products, category);
            });
            completableFutures.add(completableFuture);
        }

        return CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0]));
    }

    public static void setMarginByCategory(List<Product> products, Category category) {
        Map<String, Double> margins = convertMargins();

        for (Product product : products) {
            if (category.exclusive != null) {
                if (product.cost > category.inclusive && product.cost < category.exclusive) {
                    product.margin = margins.get(category.category);
                }
            } else {
                if (product.cost > category.inclusive) {
                    product.margin = margins.get(category.category);
                }
            }
        }
    }

}

class Product {
    public String product;
    public String group;
    public Double cost;
    public Double margin;
    public Product(String product, String group, Double cost) {
        this.product = product;
        this.group = group;
        this.cost = cost;
    }
}

class Category {
    public String category;
    public Integer inclusive;
    public Integer exclusive;

    public Category(String category, Integer inclusive, Integer exclusive) {
        this.category = category;
        this.inclusive = inclusive;
        this.exclusive = exclusive;
    }
}

package solid;

import java.util.List;
import java.util.stream.Stream;

/** 개방 폐쇄 원칙(Ocp: Open-close principle)
 *
 * 웹 쇼핑몰을 만들었다고 가정하자.
 */

enum Color {
    RED, GREEN, BLUE
}

enum Size {
    SMALL, MEDIUM, LARGE
}

class Product {
    public String name;
    public Color color;
    public Size size;

    public Product(String name, Color color, Size size) {
        this.name = name;
        this.color = color;
        this.size = size;
    }
}

/** 필터기능
 * 요구사항1: 상품을 색상 별로 필터해서 보는 기능이 있으면 좋겠어요.
 * 요구사항2: 상품을 사이즈 별로 필터해서 보는 기능이 있으면 좋겠어요.
 * 요구사항3: 상품을 색상, 사이즈 별로 필터해서 보는 기능이 있으면 좋겠어요.
 *
 * OCP 를 생각해보자 확장에는 열려 있어야하고 수정에는 닫혀 있어야한다.
 *
 * 색상, 사이즈 말고도 다른 요구사항들이 많다면?
 */
//class ProductFilter {
//
//    public Stream<Product> filterByColor(List<Product> products, Color color) {
//        return products.stream().filter(p -> p.color == color);
//    }
//
//    public Stream<Product> filterBySize(List<Product> products, Size size)  {
//        return products.stream().filter(p -> p.size == size);
//    }
//
//    public Stream<Product> filterBySizeAndColor(List<Product> products, Size size, Color color)  {
//        return products.stream().filter(p -> p.size == size && p.color == color);
//    }
//
//}

interface Specification<T> {
    boolean isSatisfied(T item);
}

interface Filter<T> {
    Stream<T> filter(List<T> items, Specification<T> spec);
}

class BetterFilter implements Filter<Product> {

    @Override
    public Stream<Product> filter(List<Product> items, Specification<Product> spec) {
        return items.stream().filter(p -> spec.isSatisfied(p));
    }
}

class ColorSpecification implements Specification<Product> {
    private Color color;

    public ColorSpecification(Color color) {
        this.color = color;
    }

    @Override
    public boolean isSatisfied(Product item) {
        return item.color == color;
    }
}

class SizeSpecification implements Specification<Product> {
    private Size size;

    public SizeSpecification(Size size) {
        this.size = size;
    }

    @Override
    public boolean isSatisfied(Product item) {
        return item.size == size;
    }
}

class AndSpecification<T> implements Specification<T> {

    private Specification<T> first, second;

    public AndSpecification(Specification<T> first, Specification<T> second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean isSatisfied(T item) {
        return first.isSatisfied(item) && second.isSatisfied(item);
    }
}

public class Ocp {

    public static void main(String[] args) {
        Product apple = new Product("Apple", Color.RED, Size.SMALL);
        Product tree = new Product("Tree", Color.GREEN, Size.MEDIUM);
        Product house = new Product("House", Color.BLUE, Size.LARGE);

        List<Product> products = List.of(apple, tree, house);

        // OCP 위반
//        ProductFilter pf = new ProductFilter();
//        System.out.println("Green products (old): ");
//
//        pf.filterByColor(products, Color.GREEN)
//                .forEach(p -> System.out.println(" - " + p.name + " is Green"));


        // 개선 이후
        BetterFilter bf = new BetterFilter();
        bf.filter(products, new ColorSpecification(Color.GREEN))
                .forEach(p -> System.out.println(" - " + p.name + " is Green"));

        // Large Blue Item
        bf.filter(products,
                new AndSpecification<>(
                        new ColorSpecification(Color.BLUE),
                        new SizeSpecification(Size.LARGE)
                ))
                .forEach(p -> System.out.println(" - " + p.name + " is Large Blue"));
    }
}

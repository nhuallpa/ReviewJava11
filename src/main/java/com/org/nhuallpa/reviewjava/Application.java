package com.org.nhuallpa.reviewjava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class Application {

    private static List<Dish> menu = Arrays.asList(
            new Dish("pork", false, 800, Dish.Type.MEAT),
            new Dish("beef", false, 700, Dish.Type.MEAT),
            new Dish("chicken", false, 400, Dish.Type.MEAT),
            new Dish("french fries", true, 530, Dish.Type.OTHER), new Dish("rice", true, 350, Dish.Type.OTHER),
            new Dish("season fruit", true, 120, Dish.Type.OTHER), new Dish("pizza", true, 550, Dish.Type.OTHER),
            new Dish("prawns", false, 300, Dish.Type.FISH),
            new Dish("salmon", false, 450, Dish.Type.FISH));

    private static List<Dish> specialMenu = Arrays.asList(
            new Dish("seasonal fruit", true, 120, Dish.Type.OTHER),
            new Dish("prawns", false, 300, Dish.Type.FISH),
            new Dish("rice", true, 350, Dish.Type.OTHER),
            new Dish("chicken", false, 400, Dish.Type.MEAT),
            new Dish("french fries", true, 530, Dish.Type.OTHER));

    private static List<String> words = Arrays.asList("Modern", "Java", "In", "Action");


    public static void main(String[] args) {
        // Streaming
        internalIterationDishes();
        filterUniqueElement();
        takeElementsInAOrderedList();
        limiteAndSkipAreComplementary();
        flattenWords();
        mapAndGeneratePair();
        mapAndGeneratePairDividedByThree();
        findingAndMatching();
        summingElements();
        minAndMaxElements();
        generateStream();

        //Collect
        findDishWithMaxCalority();
        summingAveragingAndSumarizing();
        joinMenu();
        reducingMenu();
        listFiltering();
        solution("Forget  CVs..Save time . x x");
    }

    private static void listFiltering() {
        var list = Arrays.asList(new Object[]{1,2,"a","b"});
        var result = list.stream()
                .filter(item -> item instanceof Integer)
                .collect(Collectors.toList());

        result.stream().forEach(System.out::println);
    }

    private static void reducingMenu() {
        var totalCalories = menu.stream().collect(reducing(0, Dish::getCalories, (i,j) -> i + j));
        var mostCaloriesDish = menu.stream().collect(reducing(
                (d1, d2) -> d1.getCalories() > d1.getCalories()? d1 : d2
        ));
    }

    public static int solution(String S) {
        String delimiters = "\\.|\\,|\\?";
        var tokens = Arrays.asList(S.split(delimiters));

        var counters = tokens.stream()
                .map( sentence -> sentence.trim())
                .map( sentence -> {
                    var words = Arrays.asList(sentence.split("\\s+"));
                    return words.size();
                })
                .collect(Collectors.toList());

        return counters.stream().reduce(Math::max).orElse(0);
    }

    private static void joinMenu() {
        var shotMenu = menu.stream().map(Dish::getName).collect(joining(", "));
        System.out.println(shotMenu);
    }

    private static void summingAveragingAndSumarizing() {

        var totalCalories = menu.stream().collect(summingInt(Dish::getCalories));
        var averageCalories = menu.stream().collect(averagingInt(Dish::getCalories));
        var summaryStatistic = menu.stream().collect(summarizingInt(Dish::getCalories));

        System.out.println(summaryStatistic);

    }

    private static void findDishWithMaxCalority() {

        Comparator<Dish> comparatorDishes = Comparator.comparing(Dish::getCalories);
        menu.stream().collect(maxBy(comparatorDishes)).ifPresent(System.out::println);

    }

    private static void generateStream() {
        var stream = Stream.of("Modern ", "Java", "In", "Action");
        stream.map(String::toUpperCase).forEach(System.out::println);

        var numbers = new int[]{2, 3, 5, 7, 11, 13};
        var sum = Arrays.stream(numbers).sum();
        System.out.println("Suma " + sum);

        Stream.iterate(0, n -> n + 2)
                .limit(10)
                .forEach(System.out::println);

        Stream.iterate( new int[]{0, 1}, pair -> new int[]{ pair[0] + pair[1], pair[0] + pair[1] + pair[1] })
                .limit(10)
                .flatMapToInt(Arrays::stream)
                .forEach(fiboNum -> System.out.print( fiboNum + ", "));

        Stream.generate(Math::random)
                .limit(20)
                .forEach(System.out::println);
    }

    private static void summingElements() {
        var numbers = Arrays.asList(1, 2, 3, 4, 5);

        int sum = numbers.stream().reduce(0, (a, b) -> a + b);

        int sumParallel = numbers.parallelStream().reduce(0, Integer::sum);

        System.out.println("Summing elements " + sum);
        System.out.println("Summing elements in parallel " + sumParallel);

    }
    private static void minAndMaxElements() {
        var numbers = Arrays.asList(1, 2, 3, 4, 5);

        var max = numbers.stream().reduce(Math::max);
        var min = numbers.stream().reduce(Math::min);

        System.out.println("Min and Max elements " + min.get() +  " , " + max.get());
    }

    private static void findingAndMatching() {
        menu.stream()
                .filter(Dish::isVegetarian)
                .findAny()
                .ifPresent(dish1 -> System.out.println(dish1.getName()));

    }

    private static void mapAndGeneratePairDividedByThree() {

        var numbers1 = Arrays.asList(1, 2, 3);
        var numbers2 = Arrays.asList(3, 4);
        var pairs = numbers1.stream()
                .flatMap(i -> numbers2.stream()
                        .filter(j -> (i + j) % 3 == 0)
                        .map(j -> new int[]{i, j}))
                .collect(toList());

        pairs.forEach(pair -> System.out.println("Special Pair  : [" + pair[0] + " , " + pair[1] + "]"));

    }

    private static void mapAndGeneratePair() {

        var numbers1 = Arrays.asList(1, 2, 3);
        var numbers2 = Arrays.asList(3, 4);
        var pairs = numbers1.stream()
                .flatMap(i -> numbers2.stream()
                        .map(j -> new int[]{i, j}))
                .collect(toList());

        pairs.forEach(pair -> System.out.println("Pair : [" + pair[0] + " , " + pair[1] + "]"));

    }

    private static void flattenWords() {

        var uniqueCharacter = words.stream()
                .map(word -> word.split(""))
                .flatMap(Arrays::stream)
                .collect(toList());

        System.out.println(uniqueCharacter);
    }

    private static void limiteAndSkipAreComplementary() {

        var slicedMenu1 = specialMenu.stream()
                .filter(dish -> dish.getCalories() > 300)
                .limit(3)
                .collect(Collectors.toList());

        System.out.println(slicedMenu1);

        var slicedMenu2 = specialMenu.stream()
                .filter(dish -> dish.getCalories() > 300)
                .skip(2)
                .collect(Collectors.toList());

        System.out.println(slicedMenu2);

    }

    private static void takeElementsInAOrderedList() {

        var slicedMenu1 = specialMenu.stream()
                .takeWhile(dish -> dish.getCalories() < 320)
                .collect(Collectors.toList());

        System.out.println(slicedMenu1);

        var slicedMenu2 = specialMenu.stream()
                .dropWhile(dish -> dish.getCalories() < 320)
                .collect(Collectors.toList());

        System.out.println(slicedMenu2);


    }

    private static void internalIterationDishes() {
        // Internal interation
        var threeHighCaloricDishNames = menu.stream()
                .filter(dish -> dish.getCalories() > 300)  // intermidiate operator
                .map(Dish::getName)// intermidiate operator
                .limit(3) // intermidiate operator
                .collect(toList()); // terminal operator. ex count(), forEach()

        System.out.println(threeHighCaloricDishNames);

        //Keep in mind that you can consume a stream only once!

        // External iteration vs Internal iteration
    }

    private static void filterUniqueElement() {
        var numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
        numbers.stream()
                .filter(i -> i % 2 == 0)
                .distinct()
                .forEach(System.out::println);
    }


}

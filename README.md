# Java 8 notes

Table of Contents

* [Java 8 notes](#java-8-notes)
  * [Lambda expressions](#lambda-expressions)
     * [Functional interface](#functional-interface)
        * [Common functional interfaces in Java 8](#common-functional-interfaces-in-java-8)
        * [Predicate](#predicate)
        * [Consumer](#consumer)
        * [Function](#function)
     * [Type checking, type interface and restrictions](#type-checking-type-interface-and-restrictions)
        * [Type interface](#type-interface)
        * [Using local variables](#using-local-variables)
     * [Method references](#method-references)
        * [Constructor references](#constructor-references)
     * [Useful methods to compose lambda expressions](#useful-methods-to-compose-lambda-expressions)
        * [Reversed order](#reversed-order)
        * [Chaining comparators](#chaining-comparators)
        * [Composing predicates](#composing-predicates)
        * [Composing functions](#composing-functions)

## Lambda expressions

A _lambda expression_ can be understood as a concise representation of an anonymous function that can be passed around: it doesn’t have a name, but it has a list of parameters, a body, a return type, and also possibly a list of exceptions that can be thrown.

A lambda expression is composed of parameters, an arrow, and a body.
![alt tag](readmeImgs/lambda.png)

```
(parameters) -> expression
(parameters) -> { statements; }
```

### Functional interface

In a nutshell, a _functional interface_ is an interface that specifies exactly one abstract method.
Lambda expressions let you provide the implementation of the abstract method of a functional interface directly inline and treat the whole expression as an instance of a functional interface.
```
Runnable r1 = () -> System.out.println("123");
```

#### Common functional interfaces in Java 8

| Functional interface    | Function descriptor | Primitive specializations                                                                                                                                                   |
|-------------------------|---------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `Predicate<T>`            | T -> boolean        | IntPredicate, LongPredicate, DoublePredicate                                                                                                                                |
| `Consumer<T>`             | T -> void           | IntConsumer, LongConsumer, DoubleConsumer                                                                                                                                   |
| `Function<T, R>`          | T -> R              | IntFunction, IntToDoubleFunction, IntToLongFunction, LongFunction, LongToDoubleFunction, LongToIntFunction, DoubleFunction, ToIntFunction, ToDoubleFunction, ToLongFunction |
| `Supplier<T>`             | () -> T             | BooleanSupplier, IntSupplier, LongSupplier, DoubleSupplier                                                                                                                  |
| `UnaryOperator<T>`        | T -> T              | IntUnaryOperator, LongUnaryOperator, DoubleUnaryOperator                                                                                                                    |
| `BinaryOperator<T>`       | (T, T) -> T         | IntBinaryOperator, LongBinaryOperator, DoubleBinaryOperator                                                                                                                 |
| `BiPredicate<L, R>`       | (L, R) -> boolean   |                                                                                                                                                                             |
| `BiConsumer<T, U>`        | (T, U) -> void      | ObjIntConsumer, ObjLongConsumer, ObjDoubleConsumer                                                                                                                          |
| `BiFunction<T, U, R>`     | (T, U) -> R         | ToIntBiFunction, ToLongBiFunction, ToDoubleBiFunction                                                                                                                       |

If you explore the new Java API, you’ll notice that functional interfaces are annotated with @FunctionalInterface. This annotation is used to indicate that the interface is intended to be a functional interface. The compiler will return a meaningful error if you define an interface using the @FunctionalInterface annotation and it isn’t a functional interface.

![alt tag](readmeImgs/fourSteps.png)

1. Common solution
2. Creating functional interface
3. Using a functional interface
4. Passing lambda expression as a parameter

#### Predicate

The java.util.function.Predicate<T> interface defines an abstract method named test that accepts an object of generic type T and returns a boolean.
```
@FunctionalInterface
public interface Predicate<T>{
    boolean test(T t);
}
```

Example:
```
Predicate<String> nonEmptyStringPredicate = (String s) -> !s.isEmpty();
List<String> nonEmpty = filter(listOfStrings, nonEmptyStringPredicate);
```

#### Consumer

The java.util.function.Consumer<T> interface defines an abstract method named accept that takes an object of generic type T and returns no result (void).

```
@FunctionalInterface
public interface Consumer<T> {
    void accept(T t);
}
```

Example:
```
forEach(Arrays.asList(1,2,3,4,5), (Integer i) -> System.out.println(i));
```

#### Function

The java.util.function.Function<T, R> interface defines an abstract method named apply that takes an object of generic type T as input and returns an object of generic type R.

```
@FunctionalInterface
public interface Function<T,R> {
    R apply(T t);
}
```

```
List<Integer> l = map(Arrays.asList("1", "2", "3"), (String s) -> s.length());
```

### Type checking, type interface and restrictions

![alt tag](readmeImgs/typeChecking.png)

**Special void-compatibility rule**

If a lambda has a statement expression as its body, it’s compatible with a function descriptor that returns void (provided the parameter list is compatible too). For example, both of the ollowing lines are legal even though the method add of a List returns a boolean and not void as expected in the Consumer context (T -> void):
```
// Predicate has a boolean return
Predicate<String> p = s -> list.add(s);
// Consumer has a void return
Consumer<String> b = s -> list.add(s);
```

#### Type interface

The Java compiler deduces what functional interface to associate with a lambda expression from its surrounding context (the target type), meaning it can also deduce an appropriate signature for the lambda because the function descriptor is available through the target type.

**No explicit type on the parameter:**
```
List<Apple> greenApples = filter(inventory, a -> "green".equals(a.getColor()));
```

#### Using local variables

Lambda expressions are also allowed to use free variables (variables that aren’t the parameters and defined in an outer scope) just like anonymous classes can. They’re called capturing lambdas. For example, the following lambda captures the variable portNumber:
```
int portNumber = 1337;
Runnable r = () -> System.out.println(portNumber);
```

**Closure**

A closure is an instance of a function that can reference nonlocal variables of that function with no restrictions. For example, a closure could be passed as argument to another function. It could also access and modify variables defined outside its scope. Now Java 8 lambdas and anonymous classes do something similar to closures: they can be passed as argument to methods and can access variables outside their scope. But they have a restriction: they can’t modify the content of local variables of a method in which the lambda is defined. Those variables have to be implicitly final. It helps to think that lambdas close over values rather than variables.

### Method references

**Recipe for constructing method references**

1. A method reference to a _static method_ (for example, the method `parseInt` of `Integer`, written `Integer::parseInt`)
2. A method reference to an _instance method_ of an arbitrary type (for example, the method `length` of a `String`, written `String::length`)
3. A method reference to an _instance method of an existing object_ (for example, suppose you have a local variable `expensiveTransaction` that holds an object of type `Transaction`, which supports an instance method `getValue`; you can write `expensiveTransaction::getValue`)

#### Constructor references

You can create a reference to an existing constructor using its name and the keyword new as follows: `ClassName::new`.
```
Supplier<Apple> c1 = Apple::new;
Apple a1 = c1.get();
```

### Useful methods to compose lambda expressions

#### Reversed order

```
apples.sort(comparing(Apple::getWeight).reversed());
```

#### Chaining comparators

```
apples.sort(comparing(Apple::getWeight).reversed().thenComparing(Apple::getColor));
```

#### Composing predicates

```
Predicate<Apple> readApple = (Apple a) -> a.getColor().equals("red");
Predicate<Apple> notRedApple = readApple.negate()
```

```
Predicate<Apple> redHeavvyApple = readApple.and((a) -> a.getWeight() > 150);
```

#### Composing functions

```
Function<Integer, Integer> f = x -> x + 1;
Function<Integer, Integer> g = x -> x * 2;
Function<Integer, Integer> h = f.andThen(g);
int result = h.apply(2);
```

```
Function<String, String> addHeader = Letter::addHeader;
Function<String, String> transformationPipeline = addHeader.andThen(Letter::checkSpelling)
    .andThen(Letter::addFooter);
```

## Streams

Collections in Java 8 support a new stream method that returns a stream (the interface definition is available in java.util.stream.Stream).

Example:

```
List<String> threeHighCaloricDishes = DishRepository.dishesList()
                .stream()
                .filter(dish -> dish.getCalories() > 300)
                .limit(3)
                .map(Dish::getName)
                .collect(Collectors.toList());
```

![alt tag](readmeImgs/menuStream.png)

* filter — Takes a lambda to exclude certain elements from the stream. In this case, you select dishes
that have more than 300 calories by passing the lambda `d -> d.getCalories() > 300`. 
* map — Takes a lambda to transform an element into another one or to extract information. In this case, you extract the name for each dish by passing the method reference Dish::getName, which is equivalent to the lambda `d -> d.getName()`.
* limit — Truncates a stream to contain no more than a given number of elements.
* collect — Converts a stream into another form. In this case you convert the stream into a list.

### Stream properties

#### Traversable only once

```
Stream<String> menuStream = DishRepository.dishesList().stream().map(Dish::getName);
menuStream.forEach(System.out::println);
menuStream.forEach(System.out::println);
```

`java.lang.IllegalStateException` - stream has already been operated upon or closed

#### Internal iteration

![alt tag](readmeImgs/internalIteration.png)

### Stream operations

Stream operations that can be connected are called _intermediate operations_, and operations that close a stream are called _terminal operations_.

| Operation | Type         | ReturnType | Argument      | Function descriptor |
|-----------|--------------|------------|---------------|---------------------|
| filter    | Intermediate | Stream<T>  | Predicate<T>  | T -> boolean        |
| map       | Intermediate | Stream<R>  | Function<T,R> | T -> R              |
| limit     | Intermediate | Stream<T>  |               |                     |
| sorted    | Intermediate | Stream<T>  | Comparator<T> | (T, T) -> int       |
| distinct  | Intermediate | Stream<T>  |               |                     |

| Operation | Type     | Purpose                                                                                |
|-----------|----------|----------------------------------------------------------------------------------------|
| forEach   | Terminal | Consumes each element from a stream and applies a lambda to each of them. Returns void |
| count     | Terminal | Returns the number of elements in a stream. The operation returns a long               |
| collect   | Terminal | Reduces the stream to create a collection such as a List, a Map, or Integer            |

#### Filtering and slicing

##### Filtering with a predicate

```
DishRepository.dishesList().stream()
                .filter(Dish::isVegetarian)
                .collect(Collectors.toList());
```

##### Filtering unique elements

```
List<Integer> list = Arrays.asList(1,2,2,3,4,4,5,6,7);
list.stream().filter(i -> i % 2 == 0).distinct();
```

##### Truncating a stream

```
List<Integer> list = Arrays.asList(1,2,2,3,4,4,5,6,7);
list.stream().filter(i -> i % 2 == 0).limit(3);
```

##### Skipping elements

```
List<Integer> list = Arrays.asList(1,2,2,3,4,4,5,6,7);
list.stream().filter(i -> i % 2 == 0).skip(3);
```

#### Mapping

```
List<String> threeHighCaloricDishes = DishRepository.dishesList()
    .stream()
    .map(Dish::getName)
    .collect(Collectors.toList());
```

##### Using flatMap

```
List<String> words = Arrays.asList("Java8", "Lambdas", "In");
words.stream()
        .map(word -> word.split(""))
        .flatMap(Arrays::stream)
        .distinct()
        .collect(toList());
```

```
List evens = Arrays.asList(2, 4, 6);
List odds = Arrays.asList(3, 5, 7);
List primes = Arrays.asList(2, 3, 5, 7, 11);
List numbers = Stream.of(evens, odds, primes)
    .flatMap(List::stream)
    .collect(Collectors.toList());

Read more: http://javarevisited.blogspot.com/2016/03/difference-between-map-and-flatmap-in-java8.html#ixzz4Os8FiEyX
```

#### Finding and matching

##### If a predicate matches at least one element

```
menu.stream().anyMatch(Dish::isVegetarian)
```

##### If a predicate matches all elements

```
menu.stream().allMatch(d -> d.getCalories() < 1000);
```

##### noneMatch

```
menu.stream().noneMatch(d -> d.getCalories() >= 1000);
```

#### Finding an element

```
Optional<Dish> dish = menu.stream()
                        .filter(Dish::isVegetarian)
                        .findAny();
```

#### Finding the first element

```
List<Integer> someNumbers = Arrays.asList(1, 2, 3, 4, 5);
Optional<Integer> firstSquareDivisibleByThree =
someNumbers.stream()
.map(x -> x * x)
.filter(x -> x % 3 == 0)
.findFirst(); // 9
```

#### Reducing

![alt tag](readmeImgs/reducing.png)

##### No initial value

There’s also an overloaded variant of reduce that doesn't take an initial value, but it returns an `Optional` object:

```
Optional<Integer> sum = numbers.stream().reduce((a, b) -> (a + b));
```

| Operation | Type         | Return type | Type/Functional interface used |
|-----------|--------------|-------------|--------------------------------|
| filter    | Intermediate | Stream<T>   | Predicate<T>                   |
| distinct  | Intermediate | Stream<T>   |                                |
| skip      | Intermediate | Stream<T>   | long                           |
| limit     | Intermediate | Stream<T>   | long                           |
| map       | Intermediate | Stream<R>   | Function<T,R>                  |
| flatMap   | Intermediate | Stream<R>   | Function<T,Stream<R>>          |
| sorted    | Intermediate | Stream<T>   | Comparator<T>                  |
| anyMatch  | Terminal     | boolean     | Predicate<T>                   |
| noneMatch | Terminal     | boolean     | Predicate<T>                   |
| allMatch  | Terminal     | boolean     | Predicate<T>                   |
| findAny   | Terminal     | Optional<T> |                                |
| findFirst | Terminal     | Optional<T> |                                |
| forEach   | Terminal     | void        | Consumer<T>                    |
| collect   | Terminal     | R           | Collector<T,A,R>               |
| reduce    | Terminal     | Optional<T> | BinaryOperator<T>              |
| count     | Terminal     | long        |                                |

#### Numeric streams

```
int calories = menu.stream()
                    .map(Dish::getCalories)
                    .sum();
```

##### Numeric ranges

```
IntStream.rangeClosed(1, 100);
```

```
IntStream.rangeClosed(1, 100)
    .filter(b -> Math.sqrt(a*a + b*b) % 1 == 0)
    .boxed()
    .map(b -> new int[]{a, b, (int) Math.sqrt(a * a + b * b)});
```

#### Building streams

```
Stream<String> stream = Stream.of("Java 8 ", "Lambdas ", "In ", "Action");
```

```
int[] numbers = {1,2,3,4};
int sum = Arrays.stream(numbers).sum();
```

```
Stream.iterate(0, n -> n + 2)
        .limit(10)
        .forEach(System.out::println);
```

```
Stream.iterate(new int[]{0, 1},
    t -> new int[]{t[1], t[0]+t[1]})
    .limit(20)
    .forEach(t -> System.out.println("(" + t[0] + "," + t[1] +")"));
```

```
Stream.generate(Math::random)
    .limit(5)
    .forEach(System.out::println);
```
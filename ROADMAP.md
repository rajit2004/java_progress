# ☕ Java Programming Roadmap for Complete Beginners
### From Zero to Job-Ready — A Step-by-Step Guide

> **Accompanying code repository:** [github.com/rajit2004/java_progress](https://github.com/rajit2004/java_progress) – over 173 solved problems, categorized by topic.

---

## 📌 How to Use This Roadmap

- Follow the phases **in order** — each builds on the last.
- Don't rush. Understanding > speed.
- Code **every single day**, even if only for 20 minutes.
- When stuck, Google it, read docs, ask on Stack Overflow — that *is* programming.
- Use the code in my [java_progress](https://github.com/rajit2004/java_progress) repo to see working examples.

---

## 🗺️ Overview at a Glance

| Phase | Topic | Estimated Time |
|-------|-------|----------------|
| 0 | Setup & First Program | 1–2 days |
| 1 | Core Java Basics | 3–4 weeks |
| 2 | Object-Oriented Programming | 3–4 weeks |
| 3 | Intermediate Java | 3–4 weeks |
| 4 | Data Structures & Algorithms | 6–8 weeks |
| 5 | Real Projects | Ongoing |
| 6 | Ecosystem & Career | 4–6 weeks |

> **Total time to job-ready:** ~6 to 9 months with consistent daily practice.

---

## ⚙️ Phase 0 — Setup & Your First Program (Days 1–2)

### What to Install
1. **JDK (Java Development Kit)** — Download from [adoptium.net](https://adoptium.net) (choose Java 21 LTS).
2. **IDE (Integrated Development Environment)**
    - Recommended: **IntelliJ IDEA Community Edition** (free, industry standard)
    - Alternative: VS Code with the Java Extension Pack

### Verify Your Install
Open a terminal and type:
```bash
java --version
javac --version
```
Both should print a version number. If they do — you're ready.

### Your First Program

Create a file called `HelloWorld.java` and type this out (don't copy-paste yet!):

```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
```

Run it. See `Hello, World!` appear. You are now a programmer. 🎉

### Key Concepts to Understand Here

- Every Java program lives inside a **class**
- Execution starts at the **`main` method**
- `System.out.println()` prints to the console
- Java files end in `.java`; compiled files end in `.class`

> 💡 **Check my repo:** See `src/Basics/HelloWorld.java` for this exact program.

---

## 🧱 Phase 1 — Core Java Basics (Weeks 1–4)

This is your foundation. Master every topic here before moving on.

### 1.1 Variables & Data Types

```java
int age = 25;
double price = 9.99;
boolean isStudent = true;
char grade = 'A';
String name = "Alice";
```

**Topics:**
- Primitive types: `int`, `double`, `float`, `long`, `short`, `byte`, `char`, `boolean`
- Reference types: `String`, arrays, objects
- Variable naming rules and conventions (camelCase)
- `final` keyword (constants)
- Type casting (converting between types)

### 1.2 Operators

- Arithmetic: `+`, `-`, `*`, `/`, `%`
- Comparison: `==`, `!=`, `<`, `>`, `<=`, `>=`
- Logical: `&&`, `||`, `!`
- Assignment: `=`, `+=`, `-=`, `*=`
- Increment/Decrement: `++`, `--`

### 1.3 Control Flow

```java
// If-else
if (age >= 18) {
    System.out.println("Adult");
} else {
    System.out.println("Minor");
}

// Switch
switch (grade) {
    case 'A': System.out.println("Excellent"); break;
    case 'B': System.out.println("Good"); break;
    default:  System.out.println("Keep trying");
}
```

### 1.4 Loops

```java
// For loop
for (int i = 0; i < 5; i++) {
    System.out.println(i);
}

// While loop
int count = 0;
while (count < 5) {
    count++;
}

// For-each (used with arrays/collections)
int[] numbers = {1, 2, 3, 4, 5};
for (int n : numbers) {
    System.out.println(n);
}
```

Also learn: `break`, `continue`, nested loops

### 1.5 Arrays

```java
int[] scores = new int[5];         // declare
int[] marks = {90, 85, 78, 92};    // declare + initialize
System.out.println(marks[0]);      // access element
System.out.println(marks.length);  // array length
```

**Topics:**
- 1D and 2D arrays
- Iterating over arrays
- Common array operations (find max, sum, reverse)

### 1.6 Methods (Functions)

```java
// Defining a method
public static int add(int a, int b) {
    return a + b;
}

// Calling a method
int result = add(3, 4);  // result = 7
```

**Topics:**
- Parameters and return types
- `void` methods (no return value)
- Method overloading (same name, different parameters)
- Scope of variables

### 1.7 Strings

```java
String s = "Hello, Java!";
s.length();                    // 12
s.toUpperCase();               // "HELLO, JAVA!"
s.substring(0, 5);             // "Hello"
s.contains("Java");            // true
s.replace("Java", "World");    // "Hello, World!"
```

**Topics:**
- Common String methods
- String comparison (`equals()` vs `==`)
- `StringBuilder` for building strings efficiently
- String formatting with `printf` / `String.format()`

### ✅ Phase 1 Mini-Projects

- Calculator (handles `+`, `-`, `*`, `/`)
- FizzBuzz (classic loop exercise)
- Simple number guessing game
- Basic student grade calculator

> 📁 See examples in my repo: `src/Basics/`, `src/Conditionals/`, `src/Loops/`, `src/Arrays/`

---

## 🏗️ Phase 2 — Object-Oriented Programming (Weeks 5–8)

OOP is Java's heart. This is what makes Java *Java*.

### 2.1 Classes & Objects

```java
// Define a class
public class Dog {
    String name;   // field (attribute)
    int age;

    // Constructor
    public Dog(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Method
    public void bark() {
        System.out.println(name + " says: Woof!");
    }
}

// Create an object
Dog myDog = new Dog("Rex", 3);
myDog.bark();  // Rex says: Woof!
```

### 2.2 The Four Pillars of OOP

#### 🔒 Encapsulation — Hide internal data

```java
public class BankAccount {
    private double balance;  // private = hidden

    public double getBalance() { return balance; }
    public void deposit(double amount) {
        if (amount > 0) balance += amount;
    }
}
```

#### 🧬 Inheritance — Reuse and extend

```java
public class Animal {
    public void eat() { System.out.println("Eating..."); }
}

public class Cat extends Animal {  // Cat inherits from Animal
    public void meow() { System.out.println("Meow!"); }
}

Cat cat = new Cat();
cat.eat();   // inherited from Animal
cat.meow();  // Cat's own method
```

#### 🎭 Polymorphism — One interface, many forms

```java
Animal a = new Cat();   // A Cat IS-AN Animal
a.eat();               // Works!
```

#### 🔲 Abstraction — Hide complexity, show essentials

```java
public abstract class Shape {
    public abstract double area();  // must be implemented by subclasses
}

public class Circle extends Shape {
    double radius;
    public double area() { return Math.PI * radius * radius; }
}
```

### 2.3 Interfaces

```java
public interface Flyable {
    void fly();  // contract: any class implementing this must define fly()
}

public class Bird implements Flyable {
    public void fly() { System.out.println("Flapping wings!"); }
}
```

### 2.4 Key OOP Concepts to Learn

- `this` keyword
- `super` keyword
- `static` fields and methods
- `final` classes and methods
- Access modifiers: `public`, `private`, `protected`, *(default)*
- Abstract classes vs interfaces
- `instanceof` operator

### ✅ Phase 2 Mini-Projects

- Library book management system
- Simple bank account system
- Shape calculator (area/perimeter of circle, rectangle, triangle)
- Basic employee payroll system

> 📁 Check my repo: `src/OOP/` – many solutions use OOP principles.

---

## 🔧 Phase 3 — Intermediate Java (Weeks 9–12)

### 3.1 Collections Framework

Java's built-in toolkit for storing groups of data.

```java
import java.util.*;

// ArrayList — ordered, resizable list
List<String> fruits = new ArrayList<>();
fruits.add("Apple");
fruits.add("Mango");
fruits.remove("Apple");

// HashMap — key-value pairs
Map<String, Integer> ages = new HashMap<>();
ages.put("Alice", 25);
ages.put("Bob", 30);
int aliceAge = ages.get("Alice");

// HashSet — unique elements, no duplicates
Set<String> names = new HashSet<>();
names.add("Alice");
names.add("Alice");  // ignored — already exists
```

**Learn:** `ArrayList`, `LinkedList`, `HashMap`, `HashSet`, `TreeMap`, `Stack`, `Queue`

### 3.2 Exception Handling

```java
try {
    int result = 10 / 0;  // throws ArithmeticException
} catch (ArithmeticException e) {
    System.out.println("Can't divide by zero! " + e.getMessage());
} finally {
    System.out.println("This always runs.");
}
```

**Topics:**
- Checked vs unchecked exceptions
- `throw` and `throws`
- Creating custom exceptions
- Common exceptions: `NullPointerException`, `ArrayIndexOutOfBoundsException`, `NumberFormatException`

### 3.3 File I/O

```java
import java.io.*;

// Write to file
FileWriter writer = new FileWriter("notes.txt");
writer.write("Hello, File!");
writer.close();

// Read from file
BufferedReader reader = new BufferedReader(new FileReader("notes.txt"));
String line = reader.readLine();
reader.close();
```

### 3.4 Generics

```java
// A method that works with any type
public static <T> void printArray(T[] array) {
    for (T element : array) {
        System.out.print(element + " ");
    }
}
```

### 3.5 Lambda Expressions & Streams (Java 8+)

```java
import java.util.*;
import java.util.stream.*;

List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);

// Filter even numbers, double them, collect to list
List<Integer> result = numbers.stream()
    .filter(n -> n % 2 == 0)
    .map(n -> n * 2)
    .collect(Collectors.toList());
// result: [4, 8, 12]
```

**Topics:**
- Lambda syntax: `(params) -> expression`
- `stream()`, `filter()`, `map()`, `reduce()`, `collect()`
- Functional interfaces: `Predicate`, `Function`, `Consumer`

### 3.6 Enums

```java
public enum Day { MON, TUE, WED, THU, FRI, SAT, SUN }

Day today = Day.MON;
if (today == Day.SAT || today == Day.SUN) {
    System.out.println("Weekend!");
}
```

### ✅ Phase 3 Mini-Projects

- Contact book (store/search/delete contacts using `HashMap`)
- Word frequency counter (reads a file, counts word occurrences)
- Simple to-do list app (with file saving/loading)
- Student grade management system with sorting

> 📁 My repo examples: `src/LeetCode/` uses many collections; also `src/Collections/`.

---

## 📊 Phase 4 — Data Structures & Algorithms (Weeks 13–20)

This is what gets you through coding interviews at top companies.

### 4.1 Data Structures to Master

| Structure | When to Use | Java Class |
|-----------|-------------|------------|
| Array | Fixed-size, index access | `int[]`, `String[]` |
| ArrayList | Dynamic list | `ArrayList<>` |
| LinkedList | Frequent insert/delete | `LinkedList<>` |
| Stack | LIFO (undo, backtracking) | `Stack<>` or `Deque<>` |
| Queue | FIFO (scheduling, BFS) | `LinkedList<>` or `ArrayDeque<>` |
| HashMap | Key-value lookup | `HashMap<>` |
| HashSet | Unique elements | `HashSet<>` |
| Tree | Hierarchical data | Build from scratch |
| Graph | Networks, paths | Build from scratch |

### 4.2 Algorithms to Learn

**Sorting:**
- Bubble Sort, Selection Sort, Insertion Sort (understand them)
- Merge Sort, Quick Sort (know these well — O(n log n))
- `Collections.sort()` and `Arrays.sort()` in Java

**Searching:**
- Linear Search — O(n)
- Binary Search — O(log n) — only on sorted arrays

**Key Techniques:**
- Two Pointers
- Sliding Window
- Recursion (understand the call stack!)
- Divide and Conquer
- Dynamic Programming (start simple: Fibonacci, coin change)

### 4.3 Big-O Notation

Understand how to measure algorithm efficiency:

| Complexity | Name | Speed |
|------------|------|-------|
| O(1) | Constant | Best |
| O(log n) | Logarithmic | Great |
| O(n) | Linear | Good |
| O(n log n) | Linearithmic | Okay |
| O(n²) | Quadratic | Avoid if possible |
| O(2ⁿ) | Exponential | Very bad |

### 4.4 Practice Platforms

Start easy and build up:

1. **HackerRank** — Java-specific tracks for beginners
2. **LeetCode** — Filter by "Easy" first; then "Medium"
3. **Codewars** — Fun, gamified challenges

**Goal:** Solve 50–100 LeetCode problems before interviews.

> 📁 My LeetCode solutions: Browse `src/LeetCode/` in my [java_progress](https://github.com/rajit2004/java_progress) repo – categorized by pattern (Arrays, BinarySearch, Strings, TwoPointers, BitManipulation, Math).

---

## 🚀 Phase 5 — Real Projects (Ongoing from Week 10+)

Building real projects is what separates learners from developers.

### Beginner Projects
- **CLI Quiz App** — Multiple choice questions, score tracking
- **Expense Tracker** — Add expenses, view summary, save to file
- **Tic-Tac-Toe** — Two-player game in the console

### Intermediate Projects
- **Library Management System** — Add/search/return books, store data in files
- **Bank App** — Accounts, deposits, withdrawals, transfer with OOP design
- **Chat App (Console)** — Multi-user text chat using basic networking

### Advanced Projects *(Phase 6 territory)*
- **REST API** — Using Spring Boot (see Phase 6)
- **Web Scraper** — Using Jsoup library
- **Simple Blog App** — Spring Boot + MySQL database

> 💡 **Tip:** Put every project on GitHub. It becomes your portfolio.

---

## 🌐 Phase 6 — Java Ecosystem & Career Prep (Weeks 21–26)

### 6.1 Build Tools

- **Maven** or **Gradle** — Manage project dependencies (like npm in Node.js)
- Learn to read and write a `pom.xml` (Maven) or `build.gradle` (Gradle)

### 6.2 Spring Boot *(Most Important Framework)*

Spring Boot is used in the majority of Java backend jobs. It lets you build web apps and REST APIs quickly.

```java
@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello() {
        return "Hello from Spring Boot!";
    }
}
```

**Learn:**
- REST APIs (GET, POST, PUT, DELETE)
- `@RestController`, `@GetMapping`, `@PostMapping`
- Spring Data JPA (connect to databases)
- Basic security concepts

### 6.3 Databases

- **SQL basics** — `SELECT`, `INSERT`, `UPDATE`, `DELETE`, `JOIN`
- **MySQL** or **PostgreSQL** — Free, widely used
- **JDBC** — Java's way to connect to databases
- **Hibernate / JPA** — ORM: work with databases using Java objects

### 6.4 Version Control — Git

Non-negotiable for any developer.

```bash
git init
git add .
git commit -m "Initial commit"
git push origin main
```

**Learn:** `clone`, `add`, `commit`, `push`, `pull`, `branch`, `merge`

### 6.5 Testing

```java
import org.junit.jupiter.api.*;

class CalculatorTest {
    @Test
    void testAdd() {
        Calculator calc = new Calculator();
        assertEquals(5, calc.add(2, 3));
    }
}
```

- **JUnit 5** — Java's standard testing framework
- Learn to write unit tests for all your methods

### 6.6 Design Patterns *(Understand the classics)*

- **Singleton** — Only one instance of a class
- **Factory** — Create objects without specifying exact class
- **Observer** — Subscribe/notify pattern
- **Builder** — Construct complex objects step by step

---

## 📚 Best Learning Resources

### Free

| Resource | What It's Great For |
|----------|---------------------|
| [docs.oracle.com/javase](https://docs.oracle.com/javase/) | Official Java tutorials |
| [javatpoint.com](https://www.javatpoint.com) | Quick concept references |
| [w3schools.com/java](https://www.w3schools.com/java/) | Beginners, simple examples |
| [baeldung.com](https://www.baeldung.com) | Intermediate/advanced topics |
| Telusko (YouTube) | Great video tutorials in Java |
| Bro Code (YouTube) | Fast, beginner-friendly videos |

### Books

- **Head First Java** — Best beginner book, visual and fun
- **Effective Java** by Joshua Bloch — Read this after Phase 3

### Paid *(Worth It)*

- **Udemy: Java Masterclass by Tim Buchalka** — Comprehensive, often on sale

---

## 🧭 Weekly Study Schedule (Suggested)

| Day | Activity |
|-----|----------|
| Monday | Learn 1 new concept (read + watch) |
| Tuesday | Code examples from that concept |
| Wednesday | Solve 2–3 practice problems |
| Thursday | Build on your current project |
| Friday | Review the week's topics |
| Saturday | Longer coding session / mini-project |
| Sunday | Rest OR light review, plan next week |

---

## 🚩 Common Beginner Mistakes to Avoid

1. **Copy-pasting code** — Type it out. Your hands need to learn too.
2. **Tutorial hell** — Watching videos without building anything. Code > consume.
3. **Skipping the basics** — OOP confusion later almost always traces back to weak Phase 1.
4. **Not reading error messages** — Errors are clues, not failures. Read them carefully.
5. **Giving up when stuck** — Being stuck for 30 minutes on a bug is normal. It's how you learn.
6. **Not using Git** — Start committing your code from Day 1.
7. **Trying to memorize syntax** — Use it repeatedly and look things up. You'll remember naturally.

---

## 🎯 Milestone Checklist

### Phase 1 Complete ✅
- [ ] Can write and run a Java program from scratch
- [ ] Understand variables, loops, conditions, methods
- [ ] Can work with arrays and Strings comfortably

### Phase 2 Complete ✅
- [ ] Can design a multi-class Java program
- [ ] Understand all four OOP pillars with examples
- [ ] Can use interfaces and abstract classes

### Phase 3 Complete ✅
- [ ] Comfortable with `ArrayList`, `HashMap`, `HashSet`
- [ ] Can handle exceptions properly
- [ ] Understand and can write lambda expressions

### Phase 4 Complete ✅
- [ ] Can implement common sorting/searching algorithms
- [ ] Solved 50+ coding problems
- [ ] Understand Big-O for common operations

### Job-Ready ✅
- [ ] Built 2–3 real projects (on GitHub)
- [ ] Know Spring Boot basics
- [ ] Can write SQL queries
- [ ] Practiced 100+ LeetCode problems
- [ ] Have a GitHub profile with code

---

## 💬 Final Words

> *"The best time to start learning Java was yesterday. The second best time is right now."*

Java is verbose, opinionated, and sometimes frustrating — and it's also one of the most stable, employable, and powerful languages in the world. Millions of Android apps, banking systems, and enterprise backends run on Java.

Every line you write today is an investment. Be patient with yourself. The confusion you feel right now is not a sign you're bad at this — it's a sign you're learning.

**Now go write some code. ☕**

---

*Last updated: June 2026 | Covers Java 17–21 LTS*  
*Accompanied by the [java_progress](https://github.com/rajit2004/java_progress) repository — 173+ solved problems and growing.*
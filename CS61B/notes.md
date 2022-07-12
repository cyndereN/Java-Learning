# W1
## First JAVA Program

```
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}
```

**Key Syntax Features**. Our first programs reveal several important syntax features of Java:

- All code lives inside a class.
- The code that is executed is inside a function, a.k.a. method, called main.
- Curly braces are used to denote the beginning and end of a section of code, e.g. a class or method declaration.
- Statements end with semi-colons.
- Variables have declared types, also called their “static type”.
- Variables must be declared before use.
-Functions must have a return type. If a function does not return anything, we use void,
- The compiler ensures type consistency. If types are inconsistent, the program will not compile.

**Static Typing**.  Static typing is (in my opinion) one of the best features of Java. It gives us a number of important advantages over languages without static typing:
- Types are checked before the program is even run, allowing developers to catch type errors with ease.
- If you write a program and distribute the compiled version, it is (mostly) guaranteed to be free of any type errors. This makes your code more reliable.
- Every variable, parameter, and function has a declared type, making it easier for a programmer to understand and reason about code.

**Command line compilation and execution**. javac is used to compile programs. java is used to execute programs. We must always compile before execution.

- **Static vs. Instance methods**. The distinction between static and instance methods is incredibly important. Instance methods are actions that can only be taken by an instance of the class (i.e. a specific object), whereas static methods are taken by the class itself. An instance method is invoked using a reference to a specific instance, e.g. d.bark(), whereas static methods should be invoked using the class name, e.g. Math.sqrt(). Know when to use each.

- **Static variables**. Variables can also be static. Static variables should be accessed using the class name, e.g. Dog.binomen as opposed to d.binomen. Technically Java allows you to access using a specific instance, but we strongly encourage you not to do this to avoid confusion.

- **Command Line Arguments**. Arguments can be provided by the operating system to your program as “command line arguments,” and can be accessed using the args parameter in main. For example if we call our program from the command line like this ```java ArgsDemo these are command line arguments```, then the main method of ArgsDemo will have an array containing the Strings “these”, “are”, “command”, “line”, and “arguments”.

# W2
## Lists 1

**Golden Rule of Equals** "Create box, Copy bits". For primitives, the line int y = x copies the bits inside the x box into the y box. For reference types, we do the exact same thing. In the line Walrus newWalrus = oldWalrus;, we copy the 64 bit address in the oldWalrus box into the newWalrus box. So we can think of this golden rule of equals (GroE) as: when we assign a value with equals, we are just copying the bits from one memory box to another!

**Parameter Passing** Say we have a method average(double a, double b). This method takes two doubles as parameters. Parameter passing also follows the GRoE, i.e. when we call this method and pass in two doubles, we copy the bits from those variables into the parameter variables.
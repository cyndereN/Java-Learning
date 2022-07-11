# First JAVA Program

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
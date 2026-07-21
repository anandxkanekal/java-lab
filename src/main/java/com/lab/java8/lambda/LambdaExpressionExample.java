package com.lab.java8.lambda;

public class LambdaExpressionExample {

    static void main() {
        IFunctionalExample greeting = name -> System.out.println("Hello, " + name + "!");
        greeting.printGreeting("John");
    }
}

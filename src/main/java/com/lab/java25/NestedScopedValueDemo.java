package com.lab.java25;

public class NestedScopedValueDemo {

    private static final ScopedValue<String> LEVEL = ScopedValue.newInstance();

    static void main() {
        ScopedValue.where(LEVEL, "Level 1")
                .run(() -> {
                    IO.println("Inside parent scope");
                    IO.println("Level = " + LEVEL.get());
                    processChildTask();
                });
    }

    private static void processChildTask() {
        ScopedValue.where(LEVEL, "Level 2")
                .run(() -> {
                    IO.println("Inside child scope");
                    IO.println("Level = " + LEVEL.get());
                });

        IO.println("Inside processChildTask");
        IO.println("Level = " + LEVEL.get());
    }
}
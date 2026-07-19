package com.lab.java25;

/**
 * Demonstrates nested/shadowed ScopedValue bindings.
 *
 * When inner scopes rebind the same ScopedValue to a different value,
 * the inner binding "shadows" the outer one. After the inner scope exits,
 * the outer binding is automatically restored.
 */
public class NestedScopedValueExample {

    // Define a scoped value representing the current security clearance level
    private static final ScopedValue<String> SECURITY_CLEARANCE_LEVEL = ScopedValue.newInstance();

    static void main() {
        demonstrateNestedScopes();
    }

    /**
     * Demonstrates how inner scopes shadow outer scope bindings.
     */
    static void demonstrateNestedScopes() {
        IO.println("=== Nested/Shadowed ScopedValue Example (Security Clearance) ===\n");

        // Bind clearance level to "INTERNAL" in outer scope
        ScopedValue.where(SECURITY_CLEARANCE_LEVEL, "INTERNAL")
                .run(() -> {
                    IO.println(">>> Outer scope entered");
                    IO.println("    Security Clearance: " + SECURITY_CLEARANCE_LEVEL.get());

                    // Inner scope will shadow this binding
                    executeNestedScope();

                    IO.println(">>> Back in outer scope");
                    IO.println("    Security Clearance: " + SECURITY_CLEARANCE_LEVEL.get() + " (restored)");
                });

        IO.println("\n>>> Outer scope exited - SECURITY_CLEARANCE_LEVEL is no longer accessible");
    }

    /**
     * Creates an inner scope that shadows the outer binding with elevated clearance.
     */
    private static void executeNestedScope() {
        IO.println("  [Before inner scope]");
        IO.println("    Security Clearance: " + SECURITY_CLEARANCE_LEVEL.get());

        // Inner scope shadows outer binding with "CONFIDENTIAL"
        ScopedValue.where(SECURITY_CLEARANCE_LEVEL, "CONFIDENTIAL")
                .run(() -> {
                    IO.println("  [Inside inner scope - shadowing active]");
                    IO.println("    Security Clearance: " + SECURITY_CLEARANCE_LEVEL.get() + " (shadowed)");

                    executeDoubleNestedScope();
                });

        // Outer binding is restored after inner scope exits
        IO.println("  [After inner scope - shadowing ended]");
        IO.println("    Security Clearance: " + SECURITY_CLEARANCE_LEVEL.get() + " (restored)");
    }

    /**
     * A deeper nested scope showing multiple levels of shadowing.
     */
    private static void executeDoubleNestedScope() {
        ScopedValue.where(SECURITY_CLEARANCE_LEVEL, "TOP_SECRET")
                .run(() -> {
                    IO.println("    [Inside double-nested scope]");
                    IO.println("      Security Clearance: " + SECURITY_CLEARANCE_LEVEL.get() + " (double-shadowed)");
                });
    }
}

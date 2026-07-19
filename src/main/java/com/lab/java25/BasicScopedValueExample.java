package com.lab.java25;

/**
 * Demonstrates basic ScopedValue usage in Java 21.
 *
 * ScopedValues are immutable bindings scoped to a task or thread.
 * They're inherited by nested calls and inaccessible outside their scope.
 * Use cases: request IDs, user context, transaction IDs, etc.
 */
public class BasicScopedValueExample {

    // Define scoped values at class level - these act as "slots" for binding values
    private static final ScopedValue<String> REQUEST_ID = ScopedValue.newInstance();
    private static final ScopedValue<String> USER_ID = ScopedValue.newInstance();

    static void main() {
        BasicScopedValueExample example = new BasicScopedValueExample();
        example.demonstrateScopedValues();
    }

    /**
     * Binds REQUEST_ID and USER_ID to a scope and processes the request.
     * Both values are accessible to all methods called within this scope.
     */
    void demonstrateScopedValues() {
        IO.println("=== Basic ScopedValue Example ===\n");

        // Bind values to scope - accessible in the lambda and called methods
        ScopedValue.where(REQUEST_ID, "req-a7f3-4d2b-9e1c-3f5a2b8d9c4e")
                .where(USER_ID, "user-12847")
                .run(() -> {
                    IO.println(">>> Entering request scope with REQUEST_ID and USER_ID bound");
                    processRequest();
                });

        // Outside the scope: values are not accessible (NoSuchElementException)
        IO.println("\n>>> Exited scope - values are no longer accessible");
        IO.println("(Calling .get() here would throw NoSuchElementException)");
    }

    /**
     * Access scoped values without them being passed as parameters.
     */
    private void processRequest() {
        String requestId = REQUEST_ID.get();
        String userId = USER_ID.get();

        IO.println("  [processRequest] Request ID: " + requestId);
        IO.println("  [processRequest] User ID: " + userId);

        processSubTask();
    }

    /**
     * Nested methods automatically inherit the scoped values from their caller.
     */
    private void processSubTask() {
        String requestId = REQUEST_ID.get();
        String userId = USER_ID.get();

        IO.println("    [processSubTask] Request ID: " + requestId);
        IO.println("    [processSubTask] User ID: " + userId);

        processDeepNestedTask();
    }

    /**
     * Shows that scoped values propagate through multiple levels of nesting.
     */
    private void processDeepNestedTask() {
        String requestId = REQUEST_ID.get();
        String userId = USER_ID.get();

        IO.println("      [processDeepNestedTask] Request ID: " + requestId);
        IO.println("      [processDeepNestedTask] User ID: " + userId);
    }
}

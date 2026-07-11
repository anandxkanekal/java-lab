package com.lab.java25;

public class ScopedValueDemo {

    private static final ScopedValue<String> USER_NAME = ScopedValue.newInstance();
    private static final ScopedValue<String> TRANSACTION_ID = ScopedValue.newInstance();

    static void main() {
        ScopedValueDemo demo = new ScopedValueDemo();
        demo.doWork();
    }

    void doWork() {
        ScopedValue.where(USER_NAME, "john_doe")
                .where(TRANSACTION_ID, "dcd62e7d8466f8448749e078ef38a6ef1a9d43103722c41589ffa717db2ee857")
                .run(() -> {
                    processChildTask();
                });

        IO.println("Outside scope");
        String username  = USER_NAME.get();
        String transactionId = TRANSACTION_ID.get();
        IO.println("Username = " + username);
        IO.println("TransactionId = " + transactionId);
    }

    private void processChildTask() {
        String username = USER_NAME.get();
        String transactionId = TRANSACTION_ID.get();

        IO.println("Inside processChildTask");
        IO.println("Username = " + username);
        IO.println("TransactionId = " + transactionId);
        processSubChildTask();
    }

    private void processSubChildTask() {
        String username = USER_NAME.get();
        String transactionId = TRANSACTION_ID.get();

        IO.println("Inside processSubChildTask");
        IO.println("Username = " + username);
        IO.println("TransactionId = " + transactionId);
    }
}

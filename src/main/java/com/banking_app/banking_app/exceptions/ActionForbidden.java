package com.banking_app.banking_app.exceptions;

public class ActionForbidden extends RuntimeException {
    public ActionForbidden() {
        super("No rights for this action");
    }
}

package com.scoutzknifez.tictactoe.utility.exceptions;

import java.util.Collection;

public class ListsLengthMismatchException extends RuntimeException {
    public ListsLengthMismatchException(Object[] array1, Object[] array2) {
        super(createErrorMessage(array1, array2));
    }

    public ListsLengthMismatchException(Collection<?> collection1, Collection<?> collection2) {
        this(collection1.toArray(), collection2.toArray());
    }

    private static String createErrorMessage(Object[] array1, Object[] array2) {
        return "The length of arrays do not match! Array sizes = {" + array1.length + ", " + array2.length + "}";
    }
}

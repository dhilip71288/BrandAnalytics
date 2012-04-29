package ru.brandanalyst.core.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 12/30/11
 * Time: 8:41 PM
 */
public abstract class Batch<T> {
    private final static int BATCH_SIZE = 1024;
    private List<T> batchList;

    public Batch() {
        this(BATCH_SIZE);
    }

    public Batch(final int size) {
        batchList = new ArrayList<T>(size);
    }

    public synchronized void submit(T element) {
        batchList.add(element);
        if (batchList.size() == BATCH_SIZE) {
            handle(batchList);
            batchList.clear();
        }
    }

    public void flush() {
        if (batchList.size() != 0) {
            handle(batchList);
        }
    }

    public abstract void handle(List<T> list);
}

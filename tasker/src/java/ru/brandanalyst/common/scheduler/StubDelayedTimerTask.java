package ru.brandanalyst.common.scheduler;

/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 2/9/12
 */
public class StubDelayedTimerTask extends AbstractDelayedTimerTask {
    @Override
    public void runTask() {
        throw new RuntimeException();
    }
}

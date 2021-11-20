package io.github.tobero.utils;

public class ReturnThread<T> implements Runnable {

    private T returnParm;
    private Run<T> run;

    public ReturnThread(Run<T> run) {
        this.run = run;
    }

    @Override
    public void run() {
        returnParm = run.Run();
    }

    public interface Run<T> {
        T Run();
    }

    public T getReturnParm() {
        return returnParm;
    }
}

package io.github.mczeyrox.footballmanager.core.utility.threads;

import io.github.mczeyrox.footballmanager.core.exception.AlreadyRunException;
import io.github.mczeyrox.footballmanager.core.utility.callbacks.Callback;

/**
 * Run tasks using the threading settings in config.yml performance section
 */
public class AsyncCore {

    /**
     * Run tasks using the threading settings in config.yml performance section
     * @param callback
     */
    public static AsyncCore Run(Callback callback) {
        AsyncCore core = new AsyncCore(callback);
        core.runThread();
        return core;
    }

    protected Callback callback;
    protected Callback finishCallback;
    protected Thread thread;
    protected boolean hasFinished;

    protected AsyncCore(Callback callback) {
        this.callback = callback;
        this.finishCallback = null;
        this.thread = runThread();
        this.hasFinished = false;
    }

    /**
     * Pauses the thread it's called in until the task is done
     * @return the callback
     */
    public AsyncCore await() {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * This will give you if the thread has already finished
     * @return if thread has finished
     */
    public boolean hasFinished() {
        return hasFinished;
    }

    /**
     * This callback will be fired when the thread finished.
     * When thread is already finished, the callback is run instanly
     * @param finishCallback
     */
    public AsyncCore setFinishCallback(Callback finishCallback) {
        this.finishCallback = finishCallback;
        if (hasFinished()) { finishCallback.Callback(); }
        return this;
    }

    protected Thread runThread() {
        return new Thread(() -> {
            if (hasFinished) {
                throw new AlreadyRunException("The runnable got called twice. Please make sure that you didn't called it manually");
            }
            if (callback != null) callback.Callback();
            hasFinished = true;
            if (finishCallback != null) finishCallback.Callback();
        });
    }
}

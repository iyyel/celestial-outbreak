package io.iyyel.celestialoutbreak.util;

import org.apache.commons.lang3.time.StopWatch;

import java.util.concurrent.TimeUnit;

public final class Timer {

    private final StopWatch watch = new StopWatch();

    public void startTimer() {
        if (!watch.isStarted()) {
            watch.start();
        }
    }

    public void stopTimer() {
        if (!watch.isStopped()) {
            watch.stop();
        }
    }

    public void pauseTimer() {
        if (!watch.isSuspended()) {
            watch.suspend();
        }
    }

    public void resumeTimer() {
        if (watch.isSuspended()) {
            watch.resume();
        }
    }

    public void resetTimer() {
        if (watch.isStopped()) {
            watch.reset();
        }
    }

    public boolean isTimerStarted() {
        return watch.isStarted();
    }

    public long getSecondsElapsed() {
        return watch.getTime(TimeUnit.SECONDS);
    }

}

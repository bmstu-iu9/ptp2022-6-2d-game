package com.qualityworkstudio.uninvitedguests;


public class Timer {
    private Task currentTask;
    private boolean active;

    public Timer(Task task) {
        currentTask = task;
    }

    public void update(float delta) {
        if (!active) {
            return;
        }

        currentTask.update(delta);

        if (currentTask.isComplete()) {
            active = false;
        }
    }

    public float getCurrentTime() {
        return currentTask.getCurrentTime();
    }

    public void start(float delay) {
        active = true;
        currentTask.start(delay);
    }

    public void setTask(Task task) {
        currentTask = task;
    }

    public boolean isActive() {
        return active;
    }

    public static abstract class Task {
        private float currentTime;
        private boolean complete;

        public Task() {
        }

        public void start(float delay) {
            currentTime = delay;
            complete = false;
        }

        public abstract void doTask();

        public void update(float delta) {
            if (isComplete()) {
                return;
            }

            if (currentTime <= 0) {
                complete = true;
                doTask();
                return;
            }

            currentTime -= delta;
        }

        public boolean isComplete() {
            return complete;
        }

        public float getCurrentTime() {
            return currentTime;
        }
    }
}

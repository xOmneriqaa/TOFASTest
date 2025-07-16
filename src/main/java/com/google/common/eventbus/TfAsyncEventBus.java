package com.google.common.eventbus;
/* T40127 @ 22.10.2015. */

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This class is replacement for AsyncEventBus, it makes possible to send future events
 * without blocking the main thread of the http request.
 */
public class TfAsyncEventBus extends EventBus {


    private final ScheduledExecutorService executor;

    /** the queue of events is shared across all threads */
    private final ConcurrentLinkedQueue<EventWithHandler> eventsToDispatch =
            new ConcurrentLinkedQueue<>();



    /**
     * Creates a new AsyncEventBus that will use {@code executor} to dispatch
     * events.
     *
     * @param executor Executor to use to dispatch events. It is the caller's
     *        responsibility to shut down the executor after the last event has
     *        been posted to this event bus.
     */
    public TfAsyncEventBus(ScheduledExecutorService executor) {
        this.executor = executor;
    }

    @Override
    void enqueueEvent(Object event, EventHandler handler) {
        eventsToDispatch.offer(new EventWithHandler(event, handler));
    }

    /**
     * Dispatch {@code events} in the order they were posted, regardless of
     * the posting thread.
     */
    @SuppressWarnings("deprecation") // only deprecated for external subclasses
    @Override
    protected void dispatchQueuedEvents() {
        while (true) {
            EventWithHandler eventWithHandler = eventsToDispatch.poll();
            if (eventWithHandler == null) {
                break;
            }

            dispatch(eventWithHandler.event, eventWithHandler.handler);
        }
    }

    /**
     * Calls the {@link #executor} to dispatch {@code event} to {@code handler}.
     */
    @Override
    void dispatch(final Object event, final EventHandler handler) {
        executor.schedule(new Runnable() {
            @Override
            public void run() {
                TfAsyncEventBus.super.dispatch(event, handler);
            }
        }, 5, TimeUnit.SECONDS);
    }
}

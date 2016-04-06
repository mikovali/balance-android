package io.github.mikovali.android.mvp;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.util.SparseArray;

import java.util.Locale;

import io.github.mikovali.android.navigation.NavigationService;
import io.github.mikovali.balance.android.application.ObservableRegistry;
import rx.Observable;
import rx.Subscription;

public abstract class BasePresenter<T extends View> implements Presenter {

    protected final T view;

    protected final NavigationService navigationService;

    private final ObservableRegistry observableRegistry;

    private final SparseArray<Subscriber<?>> subscribers;
    private final SparseArray<Subscription> subscriptions;

    private final int screenId;
    private final int viewId;

    public BasePresenter(T view, NavigationService navigationService,
                         ObservableRegistry observableRegistry) {
        this.view = view;
        this.navigationService = navigationService;
        this.observableRegistry = observableRegistry;
        subscribers = new SparseArray<>();
        subscriptions = new SparseArray<>();
        view.addOnAttachStateChangeListener(new PresenterOnAttachStateChangeListener(this));
        screenId = navigationService.getCurrent().getId();
        viewId = view.getId();
    }

    @CallSuper
    @Override
    public void onAttachedToWindow() {
        final SparseArray<Observable<?>> observables = observableRegistry.get(screenId, viewId);
        final int size = observables.size();
        for (int i = 0; i < size; i++) {
            final int observableId = observables.keyAt(i);
            final Observable observable = observables.valueAt(i);
            //noinspection unchecked
            subscriptions.put(observableId, observable.subscribe(subscribers.get(observableId)));
        }
    }

    @CallSuper
    @Override
    public void onDetachedFromWindow() {
        final int size = subscriptions.size();
        for (int i = 0; i < size; i++) {
            final Subscription subscription = subscriptions.valueAt(i);
            if (!subscription.isUnsubscribed()) {
                subscription.unsubscribe();
                subscriptions.removeAt(i);
            }
        }
    }

    @CallSuper
    @Override
    public void onSaveState(Bundle state) {}

    @CallSuper
    @Override
    public void onRestoreState(Bundle state) {}

    protected void putSubscriber(int observableId, Subscriber<?> subscriber) {
        subscribers.put(observableId, subscriber);
    }

    protected void subscribe(Observable observable, int observableId) {
        final Subscriber<?> subscriber = subscribers.get(observableId);
        if (subscriber == null) {
            throw new IllegalArgumentException(String.format(Locale.ENGLISH,
                    "No registered subscriber for observable with ID %d", observableId));
        }
        observable = observable.publish().refCount().cacheWithInitialCapacity(1);
        observableRegistry.set(screenId, viewId, observableId, observable);
        //noinspection unchecked
        subscriptions.put(observableId, observable.subscribe(subscriber));
    }

    public abstract class Subscriber<A> extends rx.Subscriber<A> {

        private final int observableId;

        public Subscriber(int observableId) {
            this.observableId = observableId;
        }

        @CallSuper
        @Override
        public void onCompleted() {}

        @CallSuper
        @Override
        public void onError(Throwable e) {}

        @CallSuper
        @Override
        public void onNext(A t) {
            subscriptions.get(observableId).unsubscribe();
            subscriptions.remove(observableId);
            observableRegistry.remove(screenId, viewId, observableId);
        }
    }
}

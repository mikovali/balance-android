package io.github.mikovali.balance.android.application;

import android.util.SparseArray;

import rx.Observable;

/**
 * TODO things to do
 *  * figure out flow when creating/updating something
 *  * figure out flow when refreshing/loading more items
 *  * figure out how to make it simple as possible to use in presenters (BasePresenter)
 *  * figure out the best place to remove observables from registry on navigation
 */
public class ObservableRegistry {

    private final SparseArray<SparseArray<SparseArray<Observable<?>>>> observables;

    public ObservableRegistry() {
        observables = new SparseArray<>();
    }

    public void set(int screenId, int viewId, int id, Observable observable) {
        final SparseArray<SparseArray<Observable<?>>> screen = observables
                .get(screenId, new SparseArray<SparseArray<Observable<?>>>());
        final SparseArray<Observable<?>> view = screen.get(viewId, new SparseArray<Observable<?>>());
        view.put(id, observable);

        screen.put(viewId, view);
        observables.put(screenId, screen);
    }

    public <T> Observable<T> get(int screenId, int viewId, int id) {
        return (Observable<T>) observables.get(screenId, new SparseArray<SparseArray<Observable<?>>>())
                .get(viewId, new SparseArray<Observable<?>>())
                .get(id);
    }

    public boolean has(int screenId, int viewId, int id) {
        return observables
                .get(screenId, new SparseArray<SparseArray<Observable<?>>>())
                .get(viewId, new SparseArray<Observable<?>>())
                .get(id) == null;
    }

    public void remove(int screenId, int viewId, int id) {
        final SparseArray<SparseArray<Observable<?>>> screenObservables = observables.get(screenId);
        if (screenObservables == null) {
            return;
        }
        final SparseArray<Observable<?>> viewObservables = screenObservables.get(viewId);
        if (viewObservables == null) {
            return;
        }
        viewObservables.delete(id);
    }
}

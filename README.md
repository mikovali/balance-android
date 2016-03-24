# Balance

## WIP

### onActivityResult

Figure out a way to handle onActivityResult in views.

Best way is using observables instead of callbacks and request codes in the API.
Under the hood obviously codes are needed.

Example:
```java
Observable<Boolean> observable = service.requestPermission(int permission);

Observable<SparseBooleanArray> observable = service.requestPermissions(int... permissions);

Observable<Uri> observable = service.getImageFromUser();
```

### WindowService

* `confirm` ([AlertDialog](https://developer.android.com/reference/android/app/AlertDialog.html))
* `progress` ([ProgressDialog](https://developer.android.com/reference/android/app/ProgressDialog.html) or cover view with overlay)
* `alert` ([Snackbar](https://developer.android.com/reference/android/support/design/widget/Snackbar.html))

### NotificationService

Easy API for showing/hiding [notifications](https://developer.android.com/reference/android/app/Notification.html).

### NavigationService

* Figure out animations
* Figure out how to replace/add screens

### MVP

#### View

* Has an ID
* Has access to `Screen`
* Injects services via Dagger
* Constructs `Presenter` in the constructor
* Delegates `onAttachedToWindow` / `onDetachedFromWindow` to presenter
* Delegates `onSaveInstanceState` / `onRestoreInstanceState` to presenter
* Has public method `getPresenter` so `ScreenView` can use it

#### ViewPresenter

* Has access to `View` via constructor
* Has access to `Screen` via `View`
* Gets all dependencies via constructor parameters

#### Screen

* Knows how to construct the `ScreenView`

#### ScreenView

* Has an ID
* Has access to all `View` objects on the screen and calls their or their `Presenter` methods
* Manages different layout styles (portrait/landscape, phone/tablet, etc)
* Manages `Toolbar` and other per-screen elements
* If logic gets too complicated, may need to introduce `ScreenViewPresenter`

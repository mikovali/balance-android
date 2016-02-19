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

### NotificationService

Easy API for showing/hiding [notifications](https://developer.android.com/reference/android/app/Notification.html).

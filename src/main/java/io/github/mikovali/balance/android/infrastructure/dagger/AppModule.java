package io.github.mikovali.balance.android.infrastructure.dagger;

import android.app.Application;
import android.database.sqlite.SQLiteOpenHelper;

import dagger.Module;
import dagger.Provides;
import flow.Flow;
import flow.StateParceler;
import io.github.mikovali.android.navigation.NavigationService;
import io.github.mikovali.balance.android.application.ActivityProvider;
import io.github.mikovali.balance.android.application.DeviceService;
import io.github.mikovali.balance.android.application.ObservableRegistry;
import io.github.mikovali.balance.android.application.WindowService;
import io.github.mikovali.balance.android.domain.model.TransactionRepository;
import io.github.mikovali.balance.android.infrastructure.android.content.AppDatabase;
import io.github.mikovali.balance.android.infrastructure.android.content.SqliteTransactionRepository;
import io.github.mikovali.balance.android.infrastructure.flow.FlowNavigationService;
import io.github.mikovali.balance.android.infrastructure.flow.ScreenDispatcher;
import io.github.mikovali.balance.android.infrastructure.flow.ScreenStateParceler;

@Module
public class AppModule {

    private final Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    // Application

    @Provides
    @AppScope
    ActivityProvider provideActivityProvider() {
        return new ActivityProvider();
    }

    @Provides
    @AppScope
    DeviceService provideDeviceService(ActivityProvider activityProvider) {
        return new DeviceService(activityProvider);
    }

    @Provides
    @AppScope
    NavigationService provideNavigationService(StateParceler stateParceler,
                                               Flow.Dispatcher dispatcher) {
        return new FlowNavigationService(stateParceler, dispatcher);
    }

    @Provides
    @AppScope
    WindowService provideWindowService(ActivityProvider activityProvider) {
        return new WindowService(activityProvider);
    }

    @Provides
    @AppScope
    ObservableRegistry provideObservableRegistry() {
        return new ObservableRegistry();
    }

    // SQLite

    @Provides
    @AppScope
    SQLiteOpenHelper provideAppDatabaseOpenHelper() {
        return new AppDatabase(application);
    }

    // Flow

    @Provides
    @AppScope
    StateParceler provideStateParceler() {
        return new ScreenStateParceler();
    }

    @Provides
    @AppScope
    Flow.Dispatcher provideScreenDispatcher(ActivityProvider activityProvider) {
        return new ScreenDispatcher(activityProvider);
    }

    // Transaction

    @Provides
    @AppScope
    TransactionRepository provideTransactionRepository(SQLiteOpenHelper appDatabaseOpenHelper) {
        return new SqliteTransactionRepository(appDatabaseOpenHelper);
    }
}

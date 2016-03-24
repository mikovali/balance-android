package io.github.mikovali.balance.android.infrastructure.dagger;

import android.app.Application;
import android.database.sqlite.SQLiteOpenHelper;

import dagger.Module;
import dagger.Provides;
import flow.StateParceler;
import io.github.mikovali.balance.android.application.ActivityProvider;
import io.github.mikovali.balance.android.application.DeviceService;
import io.github.mikovali.balance.android.application.NavigationService;
import io.github.mikovali.balance.android.application.ObservableRegistry;
import io.github.mikovali.balance.android.application.WindowService;
import io.github.mikovali.balance.android.domain.model.TransactionRepository;
import io.github.mikovali.balance.android.infrastructure.android.content.AppDatabase;
import io.github.mikovali.balance.android.infrastructure.android.content.SqliteTransactionRepository;
import io.github.mikovali.balance.android.infrastructure.flow.ScreenDispatcher;
import io.github.mikovali.balance.android.infrastructure.flow.ScreenStateParceler;

@Module
public class AppModule {

    private final Application application;

    private final ActivityProvider activityProvider;

    public AppModule(Application application) {
        this.application = application;
        activityProvider = new ActivityProvider(application);
    }

    // Application

    @Provides
    @AppScope
    SQLiteOpenHelper provideAppDatabaseOpenHelper() {
        return new AppDatabase(application);
    }

    @Provides
    @AppScope
    StateParceler provideStateParceler() {
        return new ScreenStateParceler();
    }

    @Provides
    @AppScope
    ScreenDispatcher provideScreenDispatcher() {
        return new ScreenDispatcher();
    }

    @Provides
    @AppScope
    ObservableRegistry provideObservableRegistry() {
        return new ObservableRegistry();
    }

    @Provides
    @AppScope
    DeviceService provideDeviceService() {
        return new DeviceService(activityProvider);
    }

    @Provides
    @AppScope
    NavigationService provideNavigationService() {
        return new NavigationService();
    }

    @Provides
    @AppScope
    WindowService provideWindowService() {
        return new WindowService(application, activityProvider);
    }

    // Transaction

    @Provides
    @AppScope
    TransactionRepository provideTransactionRepository(SQLiteOpenHelper appDatabaseOpenHelper) {
        return new SqliteTransactionRepository(appDatabaseOpenHelper);
    }
}

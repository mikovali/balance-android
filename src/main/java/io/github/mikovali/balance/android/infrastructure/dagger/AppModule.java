package io.github.mikovali.balance.android.infrastructure.dagger;

import android.app.Application;
import android.database.sqlite.SQLiteOpenHelper;

import dagger.Module;
import dagger.Provides;
import io.github.mikovali.balance.android.domain.model.TransactionRepository;
import io.github.mikovali.balance.android.infrastructure.android.content.AppDatabase;
import io.github.mikovali.balance.android.infrastructure.android.content.SqliteTransactionRepository;
import io.github.mikovali.balance.android.infrastructure.flow.ScreenDispatcher;

@Module
public class AppModule {

    private final Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    // Application

    @Provides
    @AppScope
    SQLiteOpenHelper provideAppDatabaseOpenHelper() {
        return new AppDatabase(application);
    }

    @Provides
    @AppScope
    ScreenDispatcher provideScreenDispatcher() {
        return new ScreenDispatcher();
    }

    // Transaction

    @Provides
    @AppScope
    TransactionRepository provideTransactionRepository(SQLiteOpenHelper appDatabaseOpenHelper) {
        return new SqliteTransactionRepository(appDatabaseOpenHelper);
    }
}

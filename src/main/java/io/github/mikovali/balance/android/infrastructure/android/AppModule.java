package io.github.mikovali.balance.android.infrastructure.android;

import android.app.Application;
import android.database.sqlite.SQLiteOpenHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.github.mikovali.balance.android.domain.model.TransactionRepository;
import io.github.mikovali.balance.android.infrastructure.android.content.AppDatabase;
import io.github.mikovali.balance.android.infrastructure.android.content.SqliteTransactionRepository;

@Module
public class AppModule {

    private final Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    // Application

    @Provides
    @Singleton
    SQLiteOpenHelper provideAppDatabaseOpenHelper() {
        return new AppDatabase(application);
    }

    // Transaction

    @Provides
    @Singleton
    TransactionRepository provideTransactionRepository(SQLiteOpenHelper appDatabaseOpenHelper) {
        return new SqliteTransactionRepository(appDatabaseOpenHelper);
    }
}

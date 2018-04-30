package com.eduardosantos.foursquareexercise;

import android.app.Application;

import com.eduardosantos.foursquareexercise.base.BaseScheduler;
import com.eduardosantos.foursquareexercise.data.local.DatabaseManager;
import com.eduardosantos.foursquareexercise.data.local.RealmDatabaseManager;
import com.eduardosantos.foursquareexercise.data.model.local.Venue;
import com.eduardosantos.foursquareexercise.data.remote.FourSquareApi;
import com.eduardosantos.foursquareexercise.data.remote.NetworkClient;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.annotations.RealmModule;
import toothpick.Scope;
import toothpick.Toothpick;
import toothpick.config.Module;
import toothpick.configuration.Configuration;
import toothpick.registries.FactoryRegistryLocator;
import toothpick.registries.MemberInjectorRegistryLocator;
import toothpick.smoothie.FactoryRegistry;
import toothpick.smoothie.MemberInjectorRegistry;

public class FourSquareExerciseApplication extends Application {
    public static final String APPLICATION_SCOPE = "application_scope";
    public static final String DB_NAME = "app.realm";

    @Override
    public void onCreate() {
        super.onCreate();
        initDatabase();
        initDependencies();
    }

    protected void initDependencies() {
        Toothpick.setConfiguration(Configuration.forProduction().disableReflection());
        Toothpick.setConfiguration(Configuration.forDevelopment().disableReflection());
        MemberInjectorRegistryLocator.setRootRegistry(new MemberInjectorRegistry());
        FactoryRegistryLocator.setRootRegistry(new FactoryRegistry());

        Scope appScope = Toothpick.openScopes(APPLICATION_SCOPE);
        appScope.installModules(new Module() {{
            bind(BaseScheduler.class).toInstance(new BaseScheduler());
            bind(NetworkClient.class).toInstance(new NetworkClient());
            bind(DatabaseManager.class).toInstance(new RealmDatabaseManager());
        }});

        Toothpick.openScope(APPLICATION_SCOPE);

        if (BuildConfig.DEBUG) {
            Toothpick.setConfiguration(Configuration.forDevelopment());
            return;
        }

        Toothpick.setConfiguration(Configuration.forProduction());
    }

    protected void initDatabase() {
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(DB_NAME)
                .schemaVersion(1)
                .modules(new FoursquareRealmModule())
                .build();
        Realm.setDefaultConfiguration(config);
    }


    @RealmModule(classes = {Venue.class})
    private class FoursquareRealmModule {
    }

}

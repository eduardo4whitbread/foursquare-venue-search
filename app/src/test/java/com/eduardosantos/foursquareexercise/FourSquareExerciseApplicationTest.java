package com.eduardosantos.foursquareexercise;

import com.eduardosantos.foursquareexercise.base.BaseScheduler;
import com.eduardosantos.foursquareexercise.base.BaseTestScheduler;
import com.eduardosantos.foursquareexercise.data.local.DatabaseManager;
import com.eduardosantos.foursquareexercise.data.local.RealmDatabaseManager;
import com.eduardosantos.foursquareexercise.data.remote.NetworkClient;

import toothpick.Scope;
import toothpick.Toothpick;
import toothpick.config.Module;
import toothpick.configuration.Configuration;
import toothpick.registries.FactoryRegistryLocator;
import toothpick.registries.MemberInjectorRegistryLocator;
import toothpick.smoothie.FactoryRegistry;
import toothpick.smoothie.MemberInjectorRegistry;

public class FourSquareExerciseApplicationTest extends FourSquareExerciseApplication {
    public static final String APPLICATION_SCOPE = "application_scope";

    @Override
    protected void initDependencies() {
        Toothpick.setConfiguration(Configuration.forProduction().disableReflection());
        Toothpick.setConfiguration(Configuration.forDevelopment().disableReflection());
        MemberInjectorRegistryLocator.setRootRegistry(new MemberInjectorRegistry());
        FactoryRegistryLocator.setRootRegistry(new FactoryRegistry());

        Scope rootScope = Toothpick.openScopes(APPLICATION_SCOPE);
        rootScope.installModules(new Module() {{
            bind(NetworkClient.class).toInstance(new NetworkClient());
            bind(BaseScheduler.class).toInstance(new BaseTestScheduler());
            bind(DatabaseManager.class).toInstance(new RealmDatabaseManager());
        }});


        Toothpick.openScope(APPLICATION_SCOPE);

        if (BuildConfig.DEBUG) {
            Toothpick.setConfiguration(Configuration.forDevelopment());
            return;
        }

        Toothpick.setConfiguration(Configuration.forProduction());
    }

    @Override
    protected void initDatabase() {
        //no-op
    }

}

package com.antigravity.twentyfortyeight.di;

import android.content.Context;
import com.antigravity.twentyfortyeight.data.PreferencesDataStore;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation"
})
public final class DiModule_ProvidePreferencesDataStoreFactory implements Factory<PreferencesDataStore> {
  private final Provider<Context> contextProvider;

  public DiModule_ProvidePreferencesDataStoreFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public PreferencesDataStore get() {
    return providePreferencesDataStore(contextProvider.get());
  }

  public static DiModule_ProvidePreferencesDataStoreFactory create(
      Provider<Context> contextProvider) {
    return new DiModule_ProvidePreferencesDataStoreFactory(contextProvider);
  }

  public static PreferencesDataStore providePreferencesDataStore(Context context) {
    return Preconditions.checkNotNullFromProvides(DiModule.INSTANCE.providePreferencesDataStore(context));
  }
}

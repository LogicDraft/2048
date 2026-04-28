package com.antigravity.twentyfortyeight.di;

import android.content.Context;
import com.antigravity.twentyfortyeight.data.ScoreDatabase;
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
public final class DiModule_ProvideScoreDatabaseFactory implements Factory<ScoreDatabase> {
  private final Provider<Context> contextProvider;

  public DiModule_ProvideScoreDatabaseFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public ScoreDatabase get() {
    return provideScoreDatabase(contextProvider.get());
  }

  public static DiModule_ProvideScoreDatabaseFactory create(Provider<Context> contextProvider) {
    return new DiModule_ProvideScoreDatabaseFactory(contextProvider);
  }

  public static ScoreDatabase provideScoreDatabase(Context context) {
    return Preconditions.checkNotNullFromProvides(DiModule.INSTANCE.provideScoreDatabase(context));
  }
}

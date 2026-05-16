Release: v1.0.0
=================

**Tag:** v1.0.0

**Release title:** Initial public release — 2048

**Summary**

This is the first official release of the `2048` Android app (version 1.0 / `v1.0.0`). It contains the core game, scoring persistence, settings screen, and basic UI/UX polish.

**Highlights**

- Game engine: implemented tile merging and scoring logic with `GameBoard`, `TileMerger`, and `ScoreCalculator`.
- Persistence: local high score storage using Room (`ScoreDatabase`, `ScoreDao`) and Preferences DataStore (`PreferencesDataStore`).
- UI: Jetpack Compose screens for game, home, scores, settings, splash, and simple animations.
- Single-player and local previews: `MiniGridPreview`, `GridComposable`, `TileComposable`.
- Theming: dark/light support with `Theme`, `Color`, and `Typography` definitions.

**Files changed / core modules**

- `app/build.gradle.kts` — versionCode=1, versionName="1.0"
- `app/src/main/java/com/antigravity/twentyfortyeight/engine` — game engine classes
- `app/src/main/java/com/antigravity/twentyfortyeight/ui` — Compose UI screens and components

**How to build**

1. Open the project in Android Studio (Arctic Fox or newer recommended).
2. Build the app or run on a device/emulator with Android API level matching the project's compile SDK.

Command-line (Gradle wrapper):

```bash
./gradlew assembleDebug
```

On Windows (PowerShell):

```powershell
.\gradlew.bat assembleDebug
```

**Known issues & notes**

- No network or multiplayer backend in this release — local play only.
- Accessibility and localization may need improvements in later releases.

**Acknowledgements & License**

See repository license and README for credits.

**Suggested release options for GitHub**

- Tag: `v1.0.0` (create new tag)
- Target branch: `main`
- Release title: "Initial public release — 2048"
- Description: Use the contents of this file as the Release Notes body.

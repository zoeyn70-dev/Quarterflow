# QuarterFlow

QuarterFlow is an Android-first personal planner MVP for a Portland State University quarter. It keeps the daily plan, next-day preview, quarter schedule, Task Oversight validation, transit planning rules, and phone-wallpaper rules in one small native app.

## MVP 0.1.0 features

- Native Kotlin + Jetpack Compose Android app.
- **Today** and **Next** daily schedule screens.
- **Quarter** overview for Fall 2026 classes.
- **Task Oversight** validation for key planning rules.
- **Wallpaper** rules screen preserving the established phone-safe layout constraints.
- **Settings** summary for commute and briefing rules.
- Encoded recurring school-day templates:
  - Monday/Wednesday: Calculus I and Intro to Programming in the Engineering Building.
  - Tuesday/Thursday: Physics with Calculus in Hoffman Hall.
  - Tuesday: Physics Lab, 5:30–8:30 PM.
- Morning order is fixed as: get ready → protected 20-minute dog walk → breakfast/final prep.
- First-week plans preserve a separate 30+ minute arrival buffer at the actual class building.
- Standard commute policy: Route 14 to PSU; Route 15 home. Unverified transit times are explicitly not guessed.
- Wallpaper validator rejects headers containing **Tomorrow**.

## Not in this MVP yet

Calendar sync, live TriMet API data, automatic image generation, and automatically setting the phone wallpaper are intentionally not enabled yet.

## Build in GitHub

Every push to `main` runs `.github/workflows/build-apk.yml`, which:

1. Sets up Java 17 and Android SDK 36.
2. Sets up Gradle 8.13.
3. Runs unit tests.
4. Builds the debug APK.
5. Publishes `QuarterFlow-MVP-0.1.0.apk` as a downloadable GitHub Actions artifact.

After uploading the project, open **Actions → Build QuarterFlow APK** to watch the build. When it finishes, open the workflow run and download the APK artifact.

## Repository layout

```text
.github/workflows/build-apk.yml
app/
  build.gradle.kts
  proguard-rules.pro
  src/main/...
  src/test/...
gradle/libs.versions.toml
.gitignore
build.gradle.kts
gradle.properties
settings.gradle.kts
README.md
```

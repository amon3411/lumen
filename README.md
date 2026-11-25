# Lumen

Smart home control for Home Assistant (lights, Sonos speakers and TVs), packaged as an Android app.

## Install
Add `https://github.com/amon3411/lumen` in [Obtainium](https://github.com/ImranR98/Obtainium) and tap **Install**. Updates show up in Obtainium automatically.

## Connect
Open Lumen, tap the link icon, enter your Home Assistant address and a long-lived access token (Profile → Security in Home Assistant), then tap **Connect**.

## Releasing
Bump `versionName` and `versionCode` in `app/build.gradle.kts` and push to `main`. GitHub Actions builds, signs and publishes the APK.

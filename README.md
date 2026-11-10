# Sby Patches

Morphe patch source for the **foodpanda** Android app, by [Saad Bin Yousaf](https://saadbinyousaf.dev).

Based on the prior work of [ReVanced](https://github.com/ReVanced/revanced-patches) and built on the
[Morphe](https://github.com/MorpheApp) patcher. Licensed under GPLv3.

## Target

| App | Package | Version |
|-----|---------|---------|
| foodpanda | `com.global.foodpanda.android` | 26.44.0 |

## Patches

| Patch | Default | What it does |
|-------|:---:|--------------|
| **Bypass anti-tamper** | ✅ | Skips DeliveryHero's MAS integrity check so the re-signed build opens instead of hitting the "device not supported" screen. Required for every patched build. |
| **Remove ads** | ✅ | Empties the adtech banner feed (home, order-tracking, discovery, reels) and suppresses the PandaMart cross-sell / grocery upsell popup. |
| **Force dark mode** | ⬜ | Rewrites foodpanda's baked-in light colour constants to dark equivalents. Palette classes are found by their colours, not by name, so it survives obfuscation. Off by default — enable it in Morphe Manager. |
| **Spoof signature (maps fix)** | ⬜ | **Experimental.** Redirects `getPackageInfo` so the app reports foodpanda's original certificate, which *may* restore Google Maps on re-signed builds. See the note below. |

## The maps caveat

Re-signing a patched APK changes its signing certificate. foodpanda's Google Maps key is restricted to the
original certificate, so Google stops serving map tiles. The **Spoof signature** patch makes the app report
the original certificate to code running *inside* the app — but Google Play Services validates the installed
signature *out of process*, so this may not fully restore maps. It's off by default; enable and test on-device.
If it doesn't work, the reliable fix is to inject your own unrestricted Maps API key (planned for a later version).

## Using these patches in Morphe

1. Open on the Android device where Morphe is installed:
   `https://morphe.software/add-source?github=saadbinyousaf1/sby-patches`
   Or paste the repo URL into Morphe Manager's "add source" field.
2. Select foodpanda, enable **Expert Mode** to choose patches individually, and patch.

## Building

CI builds the bundle on every push. A `feat:` / `fix:` commit on `main` cuts a release with `patches-<version>.mpp`
attached; any other commit just compiles to verify the project.

Locally (requires a GitHub token with `read:packages` for the Morphe registry):

```
./gradlew :patches:buildAndroid
```

## Credits

- [ReVanced](https://github.com/ReVanced/revanced-patches) — original patcher work (GPLv3)
- [Morphe](https://github.com/MorpheApp) — patcher framework and ecosystem

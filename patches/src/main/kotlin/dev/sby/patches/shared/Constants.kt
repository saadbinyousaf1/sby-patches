// © https://saadbinyousaf.dev/ © //
package dev.sby.patches.shared

import app.morphe.patcher.patch.ApkFileType
import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility

// © https://saadbinyousaf.dev/ © //
object Constants {
    val COMPATIBILITY_FOODPANDA = Compatibility(
        packageName = "com.global.foodpanda.android",
        name = "foodpanda",
        apkFileType = ApkFileType.APKS,
        appIconColor = 0xFFD70F64.toInt(),
        targets = listOf(
            AppTarget(version = "26.44.0"),
        ),
    )
}
// © https://saadbinyousaf.dev/ © //

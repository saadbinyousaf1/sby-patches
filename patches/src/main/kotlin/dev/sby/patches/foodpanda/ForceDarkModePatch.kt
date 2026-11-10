// © https://saadbinyousaf.dev/ © //
package dev.sby.patches.foodpanda

import app.morphe.patcher.extensions.InstructionExtensions.instructions
import app.morphe.patcher.extensions.InstructionExtensions.replaceInstruction
import app.morphe.patcher.patch.bytecodePatch
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.OneRegisterInstruction
import com.android.tools.smali.dexlib2.iface.instruction.WideLiteralInstruction
import dev.sby.patches.shared.Constants.COMPATIBILITY_FOODPANDA

// © https://saadbinyousaf.dev/ © //
private val DARK_BY_LIGHT: Map<Long, Long> = mapOf(
    0xFFFFFFFFL to 0xFF121417L,
    0xFFF7F8F8L to 0xFF121417L,
    0xFFFAF9F6L to 0xFF121417L,
    0xFFF5F6F7L to 0xFF16181CL,
    0xFFFDFDFEL to 0xFF181A1EL,
    0xFFF0F2F2L to 0xFF1C1F22L,
    0xFFE8E9EAL to 0xFF23262AL,
    0xFFE4E8E4L to 0xFF23262AL,
    0xFFE3E6E8L to 0xFF2A2D31L,
    0xFFD3D5D7L to 0xFF2F3338L,
    0xFFD0D6D5L to 0xFF2F3338L,
    0xFFB7BABCL to 0xFF767C82L,
    0xFFABB2B2L to 0xFF767C82L,
    0xFF919DA6L to 0xFFA0A8AEL,
    0xFF6B7175L to 0xFFB4B9BFL,
    0xFF626B6BL to 0xFFB4B9BFL,
    0xFF6B7680L to 0xFFB4B9BFL,
    0xFF39434DL to 0xFFE8EAEDL,
    0xFF2E3138L to 0xFFF2F3F5L,
    0xFF2A3333L to 0xFFF2F3F5L,
    0xFF090C0FL to 0xFFE8EAEDL,
    0xFF0C0F0FL to 0xFFE8EAEDL,
    0xFFFFEBEBL to 0xFF2B1820L,
    0xFFFFDDDDL to 0xFF3B1D2BL,
    0xFFFFC4CCL to 0xFF52253AL,
    0xFFFFE3DBL to 0xFF3A211BL,
    0xFFFDEFEBL to 0xFF2E1D19L,
    0xFFFFB4A0L to 0xFF52291FL,
    0xFFFFF4DDL to 0xFF2B2616L,
    0xFFFFEDC6L to 0xFF33291AL,
    0xFFFFE9B8L to 0xFF3A2F18L,
    0xFFFEEFCDL to 0xFF2F2818L,
    0xFFFEE2A6L to 0xFF40331AL,
    0xFFFFB413L to 0xFF3A3018L,
    0xFFDFEDF7L to 0xFF1A2836L,
    0xFFEDF6FCL to 0xFF16212BL,
    0xFFC9DDEDL to 0xFF22384AL,
    0xFFB2E2F0L to 0xFF1C2B2EL,
    0xFFE0F5EDL to 0xFF15291FL,
    0xFFCBF1E1L to 0xFF1B3528L,
    0xFF97D8BCL to 0xFF2A3A34L,
    0xFFFCF3FEL to 0xFF241A2CL,
    0xFFEFDDF5L to 0xFF2E1F38L,
    0xFFDEC0EBL to 0xFF3D2A4DL,
    0xFFC69ADFL to 0xFF5A3F5FL,
    0xFF25537CL to 0xFF8FBCE6L,
    0xFF357258L to 0xFF7CC8A4L,
    0xFFBB2800L to 0xFFFF7A5CL,
    0xFFC21760L to 0xFFFF7AA8L,
    0xFF6E3DABL to 0xFFC98CF0L,
    0x00FFFFFFL to 0x00121417L,
    0x00F7F8F8L to 0x00121417L,
    0x00FFEBEBL to 0x002B1820L,
    0x00E0F5EDL to 0x0015291FL,
    0x80FFFFFFL to 0xFF121417L,
    0x8FFFFFFFL to 0xFF121417L,
    0xCCFFFFFFL to 0xFF1A1C20L
)

// © https://saadbinyousaf.dev/ © //
private val WIDE_CONST_OPCODES = setOf(
    Opcode.CONST_WIDE,
    Opcode.CONST_WIDE_32,
    Opcode.CONST_WIDE_16,
    Opcode.CONST_WIDE_HIGH16
)

// © https://saadbinyousaf.dev/ © //
@Suppress("unused")
val forceDarkModePatch = bytecodePatch(
    name = "Force dark mode",
    description = "Repaints foodpanda's built-in light colours with dark ones so the app runs in a real dark theme. " +
        "The foodpanda pink brand colour is kept.",
    default = false
) {
    compatibleWith(COMPATIBILITY_FOODPANDA)

    dependsOn(bypassAntiTamperPatch)

    execute {
        val methods = listOf(PaletteConstructorFingerprint.method) +
            GradientConstructorFingerprint.matchAll().map { it.method }

        methods.forEach { method ->
            val instructions = method.instructions
            for (index in instructions.indices) {
                val instruction = instructions[index]
                if (instruction.opcode !in WIDE_CONST_OPCODES) continue
                val light = (instruction as WideLiteralInstruction).wideLiteral
                val dark = DARK_BY_LIGHT[light] ?: continue
                val register = (instruction as OneRegisterInstruction).registerA
                method.replaceInstruction(index, "const-wide v$register, 0x${dark.toString(16)}L")
            }
        }
    }
}
// © https://saadbinyousaf.dev/ © //

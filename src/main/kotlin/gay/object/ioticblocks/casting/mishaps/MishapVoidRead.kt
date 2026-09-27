package gay.`object`.ioticblocks.casting.mishaps

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.Mishap
import at.petrak.hexcasting.api.pigment.FrozenPigment
import net.minecraft.network.chat.Component

class MishapVoidRead : Mishap() {

    override fun accentColor(ctx: CastingEnvironment, errorCtx: Context): FrozenPigment {
        return ctx.pigment
    }

    override fun execute(
        env: CastingEnvironment,
        errorCtx: Context,
        stack: MutableList<Iota>,
    ) {
    }

    override fun errorMessage(ctx: CastingEnvironment, errorCtx: Context): Component {
        val key = if (ctx.world.random.nextInt(100) < 5) {
            "ioticblocks.mishap.void_read.rare"
        } else if (ctx.world.random.nextInt(100) in 6..<10) {
            "ioticblocks.mishap.void_read.rare2"
        } else
        {
            "ioticblocks.mishap.void_read"
        }
        return Component.translatable(key)
    }
}
package gay.`object`.ioticblocks.casting.actions

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getDouble
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.ListIota
import at.petrak.hexcasting.api.casting.iota.NullIota
import at.petrak.hexcasting.xplat.IXplatAbstractions
import gay.`object`.ioticblocks.api.IoticBlocksAPI
import gay.`object`.ioticblocks.utils.getEntityOrBlockPos
import gay.`object`.ioticblocks.utils.mishapBadEntityOrBlock
import kotlin.math.roundToInt

object OpReadIndex : ConstMediaAction {
    override val argc = 2

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val target = args.getEntityOrBlockPos(env.world, argc)
        val index = args.getDouble(1, argc).roundToInt()

        target.map(env::assertEntityInRange, env::assertPosInRange)

        val datumHolder = target.map(
            IXplatAbstractions.INSTANCE::findDataHolder,
            { IoticBlocksAPI.INSTANCE.findIotaHolder(env.world, it) },
        ) ?: throw mishapBadEntityOrBlock(target, "iota.read")

        // читаем сразу готовую иоту — Hex Casting делает всё сам
        val iota = datumHolder.readIota()
            ?: throw mishapBadEntityOrBlock(target, "iota.read")

        // ожидаем именно список
        if (iota !is ListIota) {
            throw mishapBadEntityOrBlock(target, "iota.read.list")
        }

        val spellList = iota.list

        // SpellList.getAt(int) — если индекс вне диапазона, Hex Casting кинет исключение.
        // Проверяем вручную, чтобы вернуть NullIota, как было в старом коде.
        if (index < 0 || index >= spellList.size()) {
            return listOf(NullIota())
        }

        return listOf(spellList.getAt(index))
    }
}
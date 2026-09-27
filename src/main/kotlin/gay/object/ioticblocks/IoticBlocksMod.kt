package gay.`object`.ioticblocks

import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.common.Mod

@Mod(IoticBlocks.MODID)
class IoticBlocksMod(eventBus: IEventBus) {
    init {
        IoticBlocks.init()
    }
}
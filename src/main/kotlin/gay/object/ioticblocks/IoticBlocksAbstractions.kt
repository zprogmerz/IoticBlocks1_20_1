@file:JvmName("IoticBlocksAbstractions")

package gay.`object`.ioticblocks

import gay.`object`.ioticblocks.registry.IoticBlocksRegistrar
import net.neoforged.neoforge.registries.RegisterEvent
import thedarkcolour.kotlinforforge.neoforge.KotlinModLoadingContext

fun initRegistries(vararg registries: IoticBlocksRegistrar<*>) {
    for (registry in registries) {
        initRegistry(registry)
    }
}

fun <T : Any> initRegistry(registrar: IoticBlocksRegistrar<T>) {
    // We get the mod’s event bus directly from the loading context
    // For some reason, the compiler just complained about the regular MOD_BUS import.
    val modBus = KotlinModLoadingContext.get().getKEventBus()

    modBus.addListener { event: RegisterEvent ->
        event.register(registrar.registryKey) { helper ->
            registrar.init(helper::register)
        }
    }
}

//package com.chaosbuffalo.mkultra.abilities.wet_wizard;
//
//import com.chaosbuffalo.mkultra.MKUltra;
//import com.chaosbuffalo.mkultra.core.IPlayerData;
//import com.chaosbuffalo.mkultra.core.PlayerAbility;
//import com.chaosbuffalo.mkultra.entities.projectiles.EntityGeyserProjectile;
//import com.chaosbuffalo.mkultra.fx.ParticleEffects;
//import com.chaosbuffalo.mkultra.init.ModSounds;
//import com.chaosbuffalo.mkultra.network.packets.ParticleEffectSpawnPacket;
//import com.chaosbuffalo.targeting_api.Targeting;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.util.EnumParticleTypes;
//import net.minecraft.util.SoundEvent;
//import net.minecraft.util.math.Vec3d;
//import net.minecraft.world.World;
//import net.minecraftforge.event.RegistryEvent;
//import net.minecraftforge.fml.common.Mod;
//import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
//
//import javax.annotation.Nullable;
//
//@Mod.EventBusSubscriber(modid = MKUltra.MODID)
//public class Geyser extends PlayerAbility {
//    public static final Geyser INSTANCE = new Geyser();
//
//    @SubscribeEvent
//    public static void register(RegistryEvent.Register<PlayerAbility> event) {
//        event.getRegistry().register(INSTANCE.setRegistryName(INSTANCE.getAbilityId()));
//    }
//
//    public static float PROJECTILE_SPEED = 2.0f;
//    public static float PROJECTILE_INACCURACY = 0.2f;
//
//    private Geyser() {
//        super(MKUltra.MODID, "ability.geyser");
//    }
//
//    @Override
//    public int getCooldown(int currentRank) {
//        return 14 - 3 * currentRank;
//    }
//
//    @Override
//    public Targeting.TargetType getTargetType() {
//        return Targeting.TargetType.ENEMY;
//    }
//
//    @Override
//    public float getManaCost(int currentRank) {
//        return 1 + 2 * currentRank;
//    }
//
//
//    @Override
//    public int getRequiredLevel(int currentRank) {
//        return currentRank * 2;
//    }
//
//    @Nullable
//    @Override
//    public SoundEvent getSpellCompleteSoundEvent() {
//        return ModSounds.spell_water_6;
//    }
//
//    @Override
//    public void execute(EntityPlayer entity, IPlayerData pData, World theWorld) {
//
//        int level = pData.getAbilityRank(getAbilityId());
//        pData.startAbility(this);
//        EntityGeyserProjectile projectile = new EntityGeyserProjectile(theWorld, entity);
//        projectile.setAmplifier(level);
//        projectile.shoot(entity, entity.rotationPitch,
//                entity.rotationYaw, 0.0F, PROJECTILE_SPEED, PROJECTILE_INACCURACY);
//        theWorld.spawnEntity(projectile);
//
//        Vec3d lookVec = entity.getLookVec();
//        MKUltra.packetHandler.sendToAllAround(
//                new ParticleEffectSpawnPacket(
//                        EnumParticleTypes.WATER_SPLASH.getParticleID(),
//                        ParticleEffects.CIRCLE_PILLAR_MOTION, 40, 10,
//                        entity.posX, entity.posY + 0.05,
//                        entity.posZ, 1.0, 1.0, 1.0, 1.5,
//                        lookVec),
//                entity.dimension, entity.posX,
//                entity.posY, entity.posZ, 50.0f);
//    }
//}
//
//

package com.chaosbuffalo.mkultra.init;

import com.chaosbuffalo.mkultra.MKUltra;
import com.chaosbuffalo.mkultra.core.PlayerAttributes;
import com.chaosbuffalo.mkultra.core.talents.BaseTalent;
import com.chaosbuffalo.mkultra.core.talents.RangedAttributeTalent;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.UUID;

@Mod.EventBusSubscriber
public class ModTalents {

    @SubscribeEvent
    public static void registerTalents(RegistryEvent.Register<BaseTalent> event){
        RangedAttributeTalent magicArmor = new RangedAttributeTalent(
                new ResourceLocation(MKUltra.MODID, "talent.magic_armor"),
                PlayerAttributes.MAGIC_ARMOR,
                UUID.fromString("5588f6cc-7278-434e-9cf1-a3d2262a9c76"));
        event.getRegistry().register(magicArmor);
        RangedAttributeTalent maxMana = new RangedAttributeTalent(
                new ResourceLocation(MKUltra.MODID, "talent.max_mana"),
                PlayerAttributes.MAX_MANA,
                UUID.fromString("50338dba-eaca-4ec8-a71f-13b5924496f4"));
        event.getRegistry().register(maxMana);
        RangedAttributeTalent maxHealth = new RangedAttributeTalent(
                new ResourceLocation(MKUltra.MODID, "talent.max_health"),
                (RangedAttribute) SharedMonsterAttributes.MAX_HEALTH,
                UUID.fromString("5d95bcd4-a06e-415a-add0-f1f85e20b18b"));
        event.getRegistry().register(maxHealth);
        RangedAttributeTalent magicAttackDamage = new RangedAttributeTalent(
                new ResourceLocation(MKUltra.MODID, "talent.magic_damage"),
                PlayerAttributes.MAGIC_ATTACK_DAMAGE,
                UUID.fromString("23a0f1c5-37c6-4d88-ac3b-51df84165afe"));
        event.getRegistry().register(magicAttackDamage);
        RangedAttributeTalent attackDamage = new RangedAttributeTalent(
                new ResourceLocation(MKUltra.MODID, "talent.attack_damage"),
                (RangedAttribute) SharedMonsterAttributes.ATTACK_DAMAGE,
                UUID.fromString("ec2d2cfe-ba12-4955-a94e-2c389c696004"));
        event.getRegistry().register(attackDamage);
        RangedAttributeTalent healBonus = new RangedAttributeTalent(
                new ResourceLocation(MKUltra.MODID, "talent.heal_bonus"),
                PlayerAttributes.HEAL_BONUS,
                UUID.fromString("711e57c3-cf2a-4fb5-a503-3dff0a1e007d"));
        event.getRegistry().register(healBonus);
        RangedAttributeTalent armor = new RangedAttributeTalent(
                new ResourceLocation(MKUltra.MODID, "talent.armor"),
                (RangedAttribute) SharedMonsterAttributes.ARMOR,
                UUID.fromString("1f917d51-efa1-43ee-8af0-b49175c97c0b"));
        event.getRegistry().register(armor);
        RangedAttributeTalent manaRegen = new RangedAttributeTalent(
                new ResourceLocation(MKUltra.MODID, "talent.mana_regen"),
                PlayerAttributes.MANA_REGEN,
                UUID.fromString("87cd1a11-682f-4635-97db-4fedf6a7496b"));
        event.getRegistry().register(manaRegen);
        RangedAttributeTalent healthRegen = new RangedAttributeTalent(
                new ResourceLocation(MKUltra.MODID, "talent.health_regen"),
                PlayerAttributes.HEALTH_REGEN,
                UUID.fromString("cee8e51d-e216-4ddd-897b-6618d1ef6868"));
        event.getRegistry().register(healthRegen);
        RangedAttributeTalent cooldownRate = new RangedAttributeTalent(
                new ResourceLocation(MKUltra.MODID, "talent.cooldown_reduction"),
                PlayerAttributes.COOLDOWN,
                UUID.fromString("5378ff4c-0606-4781-abc0-c7d3e945b378"));
        event.getRegistry().register(cooldownRate);
        RangedAttributeTalent meleeCritDamage = new RangedAttributeTalent(
                new ResourceLocation(MKUltra.MODID, "talent.melee_crit_damage"),
                PlayerAttributes.MELEE_CRITICAL_DAMAGE,
                UUID.fromString("0032d49a-ed71-4dfb-a9f5-f0d3dd183e96"));
        event.getRegistry().register(meleeCritDamage);
        RangedAttributeTalent spellCritDamage = new RangedAttributeTalent(
                new ResourceLocation(MKUltra.MODID, "talent.spell_crit_damage"),
                PlayerAttributes.SPELL_CRITICAL_DAMAGE,
                UUID.fromString("a9d6069c-98b9-454d-b59f-c5a6e81966d5"));
        event.getRegistry().register(spellCritDamage);
        RangedAttributeTalent meleeCrit = new RangedAttributeTalent(
                new ResourceLocation(MKUltra.MODID, "talent.melee_crit"),
                PlayerAttributes.MELEE_CRIT,
                UUID.fromString("3b9ea27d-61ca-47b4-9bba-e82679b74ddd"));
        event.getRegistry().register(meleeCrit);
        RangedAttributeTalent spellCrit = new RangedAttributeTalent(
                new ResourceLocation(MKUltra.MODID, "talent.spell_crit"),
                PlayerAttributes.SPELL_CRIT,
                UUID.fromString("9fbc7b94-4836-45ca-933a-4edaabcf2c6a"));
        event.getRegistry().register(spellCrit);
        RangedAttributeTalent movementSpeed = new RangedAttributeTalent(
                new ResourceLocation(MKUltra.MODID, "talent.movement_speed"),
                PlayerAttributes.SPELL_CRIT,
                UUID.fromString("95fcf4d0-aaa9-413f-8362-7706e29412f7"));
        event.getRegistry().register(movementSpeed);
    }
}

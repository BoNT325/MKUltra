package com.chaosbuffalo.mkultra.init;

import com.chaosbuffalo.mkultra.GameConstants;
import com.chaosbuffalo.mkultra.MKUltra;
import com.chaosbuffalo.mkultra.core.MobAbility;
import com.chaosbuffalo.mkultra.core.IMobData;
import com.chaosbuffalo.mkultra.core.MKUMobData;
import com.chaosbuffalo.mkultra.core.MKURegistry;
import com.chaosbuffalo.mkultra.core.mob_abilities.MobFireArrow;
import com.chaosbuffalo.mkultra.core.mob_abilities.MobFireball;
import com.chaosbuffalo.mkultra.core.mob_abilities.ShadowDash;
import com.chaosbuffalo.mkultra.core.mob_abilities.MobNaturesRemedy;
import com.chaosbuffalo.mkultra.mob_ai.*;
import com.chaosbuffalo.mkultra.spawn.*;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.function.BiFunction;


@Mod.EventBusSubscriber
public class ModSpawn {

    public static final int MAX_LEVEL = 10;
    public static AIModifier ADD_STANDARD_AI;
    public static AIModifier REMOVE_SKELETON_AI;

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void registerItemOptions(RegistryEvent.Register<ItemOption> event) {
        ItemOption iron_weapons = new ItemOption(
                new ResourceLocation(MKUltra.MODID, "iron_weapons"),
                ItemAssigners.MAINHAND,
                new ItemChoice(new ItemStack(Items.IRON_SWORD, 1), 5, 0),
                new ItemChoice(new ItemStack(Items.IRON_AXE, 1), 5, 0));
        event.getRegistry().register(iron_weapons);
        ItemOption bow = new ItemOption(
                new ResourceLocation(MKUltra.MODID, "bow"),
                ItemAssigners.MAINHAND,
                new ItemChoice(new ItemStack(Items.BOW, 1), 5, 0));
        event.getRegistry().register(bow);
        ItemOption grunt_helm = new ItemOption(
                new ResourceLocation(MKUltra.MODID, "grunt_helm"),
                ItemAssigners.HEAD,
                new ItemChoice(new ItemStack(Items.IRON_HELMET, 1), 5, 0));
        event.getRegistry().register(grunt_helm);
        ItemOption grunt_chest = new ItemOption(
                new ResourceLocation(MKUltra.MODID, "grunt_chest"),
                ItemAssigners.CHEST,
                new ItemChoice(new ItemStack(Items.LEATHER_CHESTPLATE, 1), 5, 0),
                new ItemChoice(ItemStack.EMPTY, 10, 0));
        event.getRegistry().register(grunt_chest);
        ItemOption captain_helm = new ItemOption(
                new ResourceLocation(MKUltra.MODID, "captain_helm"),
                ItemAssigners.HEAD,
                new ItemChoice(new ItemStack(Items.IRON_HELMET, 1), 7, 0));
        event.getRegistry().register(captain_helm);
        ItemOption captain_chest = new ItemOption(
                new ResourceLocation(MKUltra.MODID, "captain_chest"),
                ItemAssigners.CHEST,
                new ItemChoice(new ItemStack(Items.LEATHER_CHESTPLATE, 1), 5, 0));
        event.getRegistry().register(captain_chest);

    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void registerAttributeRanges(RegistryEvent.Register<AttributeRange> event) {
        AttributeRange grunt_health = new AttributeRange(
                new ResourceLocation(MKUltra.MODID, "grunt_health"),
                BaseSpawnAttributes.MAX_HEALTH, 30.0, 60.0);
        event.getRegistry().register(grunt_health);
        AttributeRange captain_health = new AttributeRange(
                new ResourceLocation(MKUltra.MODID, "captain_health"),
                BaseSpawnAttributes.MAX_HEALTH, 50.0, 120.0);
        event.getRegistry().register(captain_health);
        AttributeRange boss_health = new AttributeRange(
                new ResourceLocation(MKUltra.MODID, "boss_health"),
                BaseSpawnAttributes.MAX_HEALTH, 200.00, 500.0);
        event.getRegistry().register(boss_health);
        AttributeRange set_follow = new AttributeRange(
                new ResourceLocation(MKUltra.MODID, "follow_range"),
                BaseSpawnAttributes.FOLLOW_RANGE, 20.0, 20.0);
        event.getRegistry().register(set_follow);
        AttributeRange melee_aggro = new AttributeRange(
                new ResourceLocation(MKUltra.MODID, "melee_aggro"),
                MKSpawnAttributes.SET_AGGRO_RADIUS, 10.0, 10.0);
        event.getRegistry().register(melee_aggro);
        AttributeRange range_aggro = new AttributeRange(
                new ResourceLocation(MKUltra.MODID, "ranged_aggro"),
                MKSpawnAttributes.SET_AGGRO_RADIUS, 16.0, 16.0);
        event.getRegistry().register(range_aggro);
//        AttributeRange size_range = new AttributeRange(
//                new ResourceLocation(MKUltra.MODID, "test_size"),
//                BaseSpawnAttributes.SCALE_SIZE, 2.0, 2.0);
//        event.getRegistry().register(size_range);
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void registerMobAbilities(RegistryEvent.Register<MobAbility> event) {
        MobAbility test_buff = new MobNaturesRemedy();
        event.getRegistry().register(test_buff);
        MobAbility shadow_dash = new ShadowDash();
        event.getRegistry().register(shadow_dash);
        MobAbility fireball = new MobFireball();
        event.getRegistry().register(fireball);
        MobAbility fire_arrow = new MobFireArrow();
        event.getRegistry().register(fire_arrow);
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void registerMobDefinitions(RegistryEvent.Register<MobDefinition> event) {

        MobDefinition skeletal_grunt =  new MobDefinition(
                new ResourceLocation(MKUltra.MODID, "skeletal_grunt"),
                EntitySkeleton.class, 10)
                .withAttributeRanges(
                        MKURegistry.getAttributeRange(
                                new ResourceLocation(MKUltra.MODID, "grunt_health")),
                        MKURegistry.getAttributeRange(
                                new ResourceLocation(MKUltra.MODID, "melee_aggro"))
                )
                .withItemOptions(
                        MKURegistry.getItemOption(
                                new ResourceLocation(MKUltra.MODID, "iron_weapons")),
                        MKURegistry.getItemOption(
                                new ResourceLocation(MKUltra.MODID, "grunt_helm")),
                        MKURegistry.getItemOption(
                                new ResourceLocation(MKUltra.MODID, "grunt_chest")))
                .withAbilities(
                        MKURegistry.getMobAbility(
                                new ResourceLocation(MKUltra.MODID, "mob_ability.natures_remedy")))
                .withAIModifiers(
                        REMOVE_SKELETON_AI,
                        ADD_STANDARD_AI
                )
                .withMobName("Skeletal Grunt");
        event.getRegistry().register(skeletal_grunt);
        MobDefinition skeletal_archer =  new MobDefinition(
                new ResourceLocation(MKUltra.MODID, "skeletal_archer"),
                EntitySkeleton.class, 10)
                .withAttributeRanges(
                        MKURegistry.getAttributeRange(
                                new ResourceLocation(MKUltra.MODID, "grunt_health")),
                        MKURegistry.getAttributeRange(
                                new ResourceLocation(MKUltra.MODID, "ranged_aggro"))
                )
                .withItemOptions(
                        MKURegistry.getItemOption(
                                new ResourceLocation(MKUltra.MODID, "bow")),
                        MKURegistry.getItemOption(
                                new ResourceLocation(MKUltra.MODID, "grunt_helm")),
                        MKURegistry.getItemOption(
                                new ResourceLocation(MKUltra.MODID, "grunt_chest")))
                .withAbilities(
                        MKURegistry.getMobAbility(
                                new ResourceLocation(MKUltra.MODID, "mob_ability.fire_arrow")))
                .withAIModifiers(
                        REMOVE_SKELETON_AI,
                        ADD_STANDARD_AI
                )
                .withMobName("Skeletal Archer");
        event.getRegistry().register(skeletal_archer);
        MobDefinition skeletal_skulker =  new MobDefinition(
                new ResourceLocation(MKUltra.MODID, "skeletal_skulker"),
                EntitySkeleton.class, 10)
                .withAttributeRanges(
                        MKURegistry.getAttributeRange(
                                new ResourceLocation(MKUltra.MODID, "captain_health")),
                        MKURegistry.getAttributeRange(
                                new ResourceLocation(MKUltra.MODID, "melee_aggro"))
                )
                .withItemOptions(
                        MKURegistry.getItemOption(
                                new ResourceLocation(MKUltra.MODID, "iron_weapons")),
                        MKURegistry.getItemOption(
                                new ResourceLocation(MKUltra.MODID, "captain_helm")),
                        MKURegistry.getItemOption(
                                new ResourceLocation(MKUltra.MODID, "captain_chest")))
                .withAbilities(
                        MKURegistry.getMobAbility(
                                new ResourceLocation(MKUltra.MODID, "mob_ability.shadow_dash")),
                        MKURegistry.getMobAbility(
                                new ResourceLocation(MKUltra.MODID, "mob_ability.natures_remedy")))
                .withAIModifiers(
                        REMOVE_SKELETON_AI,
                        ADD_STANDARD_AI
                )
                .withMobName("Skeletal Skulker");
        event.getRegistry().register(skeletal_skulker);
        MobDefinition skeletal_mage =  new MobDefinition(
                new ResourceLocation(MKUltra.MODID, "skeletal_mage"),
                EntitySkeleton.class, 10)
                .withAttributeRanges(
                        MKURegistry.getAttributeRange(
                                new ResourceLocation(MKUltra.MODID, "grunt_health")),
                        MKURegistry.getAttributeRange(
                                new ResourceLocation(MKUltra.MODID, "ranged_aggro"))
                )
                .withItemOptions(
                        MKURegistry.getItemOption(
                                new ResourceLocation(MKUltra.MODID, "grunt_helm")),
                        MKURegistry.getItemOption(
                                new ResourceLocation(MKUltra.MODID, "grunt_chest")))
                .withAbilities(
                        MKURegistry.getMobAbility(
                                new ResourceLocation(MKUltra.MODID, "mob_ability.natures_remedy")),
                        MKURegistry.getMobAbility(
                                new ResourceLocation(MKUltra.MODID, "mob_ability.fireball")
                        ))
                .withAIModifiers(
                        REMOVE_SKELETON_AI,
                        ADD_STANDARD_AI
                )
                .withMobName("Skeletal Mage");
        event.getRegistry().register(skeletal_mage);
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void registerMobFactions(RegistryEvent.Register<MobFaction> event) {
        MobFaction skeleton_faction = new MobFaction(MKUltra.MODID, "skeletons");
        skeleton_faction.addSpawnList(MobFaction.MobGroups.MELEE_GRUNT, MKURegistry.getSpawnList(
                new ResourceLocation(MKUltra.MODID, "skeletal_grunts")), 1);
        skeleton_faction.addSpawnList(MobFaction.MobGroups.MELEE_CAPTAIN, MKURegistry.getSpawnList(
                new ResourceLocation(MKUltra.MODID, "skeletal_skulkers")), 1);
        skeleton_faction.addSpawnList(MobFaction.MobGroups.RANGE_GRUNT, MKURegistry.getSpawnList(
                new ResourceLocation(MKUltra.MODID, "skeletal_mages")), 1);
        skeleton_faction.addSpawnList(MobFaction.MobGroups.RANGE_GRUNT, MKURegistry.getSpawnList(
                new ResourceLocation(MKUltra.MODID, "skeletal_archers")), 1);
        event.getRegistry().register(skeleton_faction);

    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void registerSpawnLists(RegistryEvent.Register<SpawnList> event) {
        SpawnList skeletal_grunts = new SpawnList(new ResourceLocation(MKUltra.MODID, "skeletal_grunts"));
        skeletal_grunts.addOption(MKURegistry.getMobDefinition(
                new ResourceLocation(MKUltra.MODID, "skeletal_grunt")));
        event.getRegistry().register(skeletal_grunts);
        SpawnList skeletal_skulkers = new SpawnList(new ResourceLocation(MKUltra.MODID, "skeletal_skulkers"));
        skeletal_skulkers.addOption(MKURegistry.getMobDefinition(
                new ResourceLocation(MKUltra.MODID, "skeletal_skulker")));
        event.getRegistry().register(skeletal_skulkers);
        SpawnList skeletal_mages = new SpawnList(new ResourceLocation(MKUltra.MODID, "skeletal_mages"));
        skeletal_mages.addOption(MKURegistry.getMobDefinition(
                new ResourceLocation(MKUltra.MODID, "skeletal_mage")));
        event.getRegistry().register(skeletal_mages);
        SpawnList skeletal_archers = new SpawnList(new ResourceLocation(MKUltra.MODID, "skeletal_archers"));
        skeletal_archers.addOption(MKURegistry.getMobDefinition(
                new ResourceLocation(MKUltra.MODID, "skeletal_archer")));
        event.getRegistry().register(skeletal_archers);
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void registerAIGenerators(RegistryEvent.Register<AIGenerator> event){
        BiFunction<EntityLiving, BehaviorChoice, EntityAIBase> getWatchClosestLongRange =
                (entity, choice) -> new EntityAIWatchClosest(entity, EntityPlayer.class, 20.0F);
        AIGenerator watchClosest = new AIGenerator(MKUltra.MODID, "long_range_watch_closest",
                getWatchClosestLongRange);
        event.getRegistry().register(watchClosest);
        BiFunction<EntityLiving, BehaviorChoice, EntityAIBase> addBeneficialSpells = (entity, choice) -> {
            IMobData mobData = MKUMobData.get(entity);
            return new EntityAIBuffTeammates(entity, mobData, .75f,
                    3 * GameConstants.TICKS_PER_SECOND);
        };
        AIGenerator beneficialSpells = new AIGenerator(MKUltra.MODID, "beneficial_spells", addBeneficialSpells);
        event.getRegistry().register(beneficialSpells);
        BiFunction<EntityLiving, BehaviorChoice, EntityAIBase> addAggroTarget =
                (entity, choice) -> new EntityAINearestAttackableTargetMK((EntityCreature) entity,true);
        AIGenerator aggroTarget = new AIGenerator(MKUltra.MODID, "aggro_target", addAggroTarget);
        event.getRegistry().register(aggroTarget);
        BiFunction<EntityLiving, BehaviorChoice, EntityAIBase> addOffensiveSpells = (entity, choice) -> {
            IMobData mobData = MKUMobData.get(entity);
            return new EntityAIRangedSpellAttack(entity, mobData, 3 * GameConstants.TICKS_PER_SECOND,
                    .25f, .75f);
        };
        AIGenerator offensiveSpells = new AIGenerator(MKUltra.MODID, "offensive_spells", addOffensiveSpells);
        event.getRegistry().register(offensiveSpells);
        BiFunction<EntityLiving, BehaviorChoice, EntityAIBase> addLeashRange = (entity, choice) -> {
            IMobData mobData = MKUMobData.get(entity);
            return new EntityAIReturnToSpawn((EntityCreature)entity, mobData, 1.0);
        };
        AIGenerator leashRange = new AIGenerator(MKUltra.MODID, "leash_range", addOffensiveSpells);
        event.getRegistry().register(leashRange);
        BiFunction<EntityLiving, BehaviorChoice, EntityAIBase> addHurtTarget = (entity, choice) ->
                new EntityAIHurtByTargetMK((EntityCreature)entity, true);
        AIGenerator hurtTarget = new AIGenerator(MKUltra.MODID, "hurt_target", addHurtTarget);
        event.getRegistry().register(hurtTarget);
    }


    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void registerAIModifiers(RegistryEvent.Register<AIModifier> event) {
        AIModifier remove_all_tasks = new AIModifier(
                new ResourceLocation(MKUltra.MODID, "remove_all_tasks"),
                AIModifiers.REMOVE_ALL_TASKS);
        event.getRegistry().register(remove_all_tasks);
        AIModifier remove_all_target_tasks = new AIModifier(
                new ResourceLocation(MKUltra.MODID, "remove_all_target_tasks"),
                AIModifiers.REMOVE_ALL_TARGET_TASKS);
        event.getRegistry().register(remove_all_target_tasks);

        ADD_STANDARD_AI = new AIModifier(
                new ResourceLocation(MKUltra.MODID, "add_standard_ai"),
                AIModifiers.ADD_TASKS,
                new BehaviorChoice(MKURegistry.getAIGenerator(
                        new ResourceLocation(MKUltra.MODID, "beneficial_spells")),
                        0, 2, BehaviorChoice.TaskType.TASK),
                new BehaviorChoice(MKURegistry.getAIGenerator(
                        new ResourceLocation(MKUltra.MODID, "aggro_target")),
                        0, 2, BehaviorChoice.TaskType.TARGET_TASK),
                new BehaviorChoice(
                        MKURegistry.getAIGenerator(new ResourceLocation(MKUltra.MODID, "offensive_spells")),
                        0, 3, BehaviorChoice.TaskType.TASK),
                new BehaviorChoice(
                        MKURegistry.getAIGenerator(new ResourceLocation(MKUltra.MODID, "long_range_watch_closest")),
                        0, 6, BehaviorChoice.TaskType.TASK),
                new BehaviorChoice(MKURegistry.getAIGenerator(new ResourceLocation(MKUltra.MODID, "leash_range")),
                        0, 1, BehaviorChoice.TaskType.TASK),
                new BehaviorChoice(MKURegistry.getAIGenerator(new ResourceLocation(MKUltra.MODID, "hurt_target")),
                        0, 1, BehaviorChoice.TaskType.TARGET_TASK)
        );
        event.getRegistry().register(ADD_STANDARD_AI);

        REMOVE_SKELETON_AI = new AIModifier(
                new ResourceLocation(MKUltra.MODID, "remove_skeleton_ai"),
                AIModifiers.REMOVE_AI,
                new BehaviorChoice(EntityAIWanderAvoidWater.class, 0, BehaviorChoice.TaskType.TASK),
                new BehaviorChoice(EntityAINearestAttackableTarget.class, 0, BehaviorChoice.TaskType.TARGET_TASK),
                new BehaviorChoice(EntityAIWatchClosest.class, 0, BehaviorChoice.TaskType.TASK),
                new BehaviorChoice(EntityAIHurtByTarget.class, 0, BehaviorChoice.TaskType.TARGET_TASK));
        event.getRegistry().register(REMOVE_SKELETON_AI);

    }

}

package cn.paindar.academymorevanilla.core;

import cn.academy.ability.api.CategoryManager;
import cn.academy.core.AcademyCraft;
import cn.paindar.academymorevanilla.client.render.RenderTrickShotMissile;
import cn.paindar.academymorevanilla.config.AMVConfig;
import cn.paindar.academymorevanilla.vanilla.CatDanmaku.CatDanmaku;
import cn.paindar.academymorevanilla.vanilla.CatDanmaku.entity.TrickShotMissile;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Created by Paindar on 2016/12/19.
 */

@Mod(modid = "academymorevanilla", name = "AcademyMoreVanilla", version = AcademyMoreVanilla.VERSION,
        dependencies = "required-after:academy-craft@@AC_VERSION@")

public class AcademyMoreVanilla
{

    @Instance("academymorevanilla")
    public static AcademyMoreVanilla INSTANCE;
    public static final String VERSION = "@VERSION@";
    public static final Logger log = LogManager.getLogger("AcademyMoreVanilla");
    public static CreativeTabs creativeTabs = AcademyCraft.cct;


    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        log.info("Starting AcademyMoreVanilla");
        Render render=new RenderTrickShotMissile();
        RenderManager.instance.entityRenderMap.put(TrickShotMissile.class,render);
        render.setRenderManager(RenderManager.instance);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        CategoryManager.INSTANCE.register(CatDanmaku.instance);

    }
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        AMVConfig.init();
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event)
    {

    }

    @EventHandler
    public void serverStopping(FMLServerStoppingEvent event)
    {

    }

    @SubscribeEvent
    public void onClientDisconnectionFromServer(
            FMLNetworkEvent.ClientDisconnectionFromServerEvent e)
    {

    }


}

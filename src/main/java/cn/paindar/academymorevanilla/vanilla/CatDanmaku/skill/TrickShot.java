package cn.paindar.academymorevanilla.vanilla.CatDanmaku.skill;

import cn.academy.ability.api.Skill;
import cn.academy.ability.api.context.ClientRuntime;
import cn.academy.ability.api.context.Context;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;

import scala.runtime.AbstractFunction1;

/**
 * Created by Paindar on 2016/12/28.
 */
public class TrickShot extends Skill
{
    public static TrickShot instance=new TrickShot();
    private TrickShot()
    {
        super("trickshot", 1);
    }
    private Context createContext(EntityPlayer player) {return new TrickShotContext(player);}

    @SideOnly(Side.CLIENT)
    @Override
    public void activate(ClientRuntime rt, int keyID)
    {
        activateSingleKey(rt,keyID,new AbstractFunction1<EntityPlayer, Context>()
        {
            public Context apply(EntityPlayer player) {
                return createContext(player);
            }
        });
    }
}


package cn.paindar.academymorevanilla.vanilla.CatDanmaku.skill;

import cn.academy.ability.api.context.Context;
import cn.lambdalib.s11n.network.NetworkMessage.Listener;
import cn.lambdalib.util.mc.BlockSelectors;
import cn.lambdalib.util.mc.EntitySelectors;
import cn.lambdalib.util.mc.Raytrace;
import cn.lambdalib.util.mc.WorldUtils;
import cn.paindar.academymorevanilla.core.AcademyMoreVanilla;
import cn.paindar.academymorevanilla.vanilla.CatDanmaku.entity.TrickShotMissile;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;

import java.util.List;

import static cn.lambdalib.util.generic.MathUtils.lerpf;

public class TrickShotContext extends Context
{
    private static final String MSG_SYNC = "msg_sync";
    private static final String MSG_SPAWN_POS = "msg_effect";
    private static final String MSG_ACQUIRE = "msg_acquire";

    public TrickShotContext(EntityPlayer player)
    {
        super(player,TrickShot.instance);
        targetVec=player.getLookVec();
        this.player=player;
        canFocus=(ctx.getSkillExp()>0.7);
    }

    private double life = 0;
    private double maxLife = 3f;
    private EntityPlayer player;
    private EntityLiving target=null;
    TrickShotMissile missile;
    private Vec3 targetVec=null;
    private boolean canFocus=false;
    private boolean consume()
    {
        float exp = ctx.getSkillExp();
        float overload = lerpf(16, 13, exp);
        float cp = lerpf(35, 80, exp);
        return ctx.consume(overload, cp);
    }

    @Listener(channel=MSG_MADEALIVE,side={Side.CLIENT})
    private void setCooldown()
    {
        if (consume()) {
            ctx.setCooldown((int) lerpf(20, 10, ctx.getSkillExp()));
        }
    }

    @Listener(channel=MSG_MADEALIVE, side={Side.SERVER})
    private void execute()
    {
        if (consume()) {
            float exp = ctx.getSkillExp();
            missile = new TrickShotMissile(player);
            missile.setPosition(player.posX, player.posY + player.getEyeHeight(), player.posZ);
            missile.setRGB(255, 128, 192);

            player.worldObj.spawnEntityInWorld(missile);

            ctx.addSkillExp(.005f);
            ctx.setCooldown((int) lerpf(20, 10, exp));
            AcademyMoreVanilla.log.info("start running.");
        }

    }



    private void explode()
    {
        Explosion explosion = new Explosion(world(), player,
                missile.posX, missile.posY, missile.posZ,
                1);
        explosion.isSmoking = true;
        if (ctx.canBreakBlock(world())) {
            explosion.doExplosionA();
        }
        explosion.doExplosionB(true);
    }

    @Listener(channel =MSG_TICK,side={Side.SERVER})
    private void s_onTicks()
    {
        if(missile==null){terminate();return; }
        if(life>maxLife) {
            explode();
            missile.setDead();
            terminate();
            return;
        }
        life+=0.05;
        if(target==null || Math.sqrt((target.posX- missile.posX)*(target.posX- missile.posX)
                +(target.posY- missile.posY)*(target.posY- missile.posY)
                +((target.posZ- missile.posZ)*(target.posZ- missile.posZ)))>=5)
            target=null;
        else if(canFocus)
        {
            List list = WorldUtils.getEntities(missile, lerpf(4,8,ctx.getSkillExp()), EntitySelectors.living().and(EntitySelectors.exclude(player)));
            if(!list.isEmpty()) {
                target = (EntityLiving) list.get(0);
            }
            else
                target=null;
        }
        else
            target=null;
        if(target!=null)
            targetVec=Vec3.createVectorHelper(target.posX- missile.posX,target.posY- missile.posY,target.posZ-missile.posZ).normalize();
        missile.setPosition(missile.posX+targetVec.xCoord * missile.speed,missile.posY+targetVec.yCoord * missile.speed,missile.posZ+targetVec.zCoord * missile.speed);
        sendToClient(MSG_SYNC,targetVec);
        missile.speed+=0.1;
        List reason = WorldUtils.getEntities(missile, lerpf(2,4,ctx.getSkillExp()), EntitySelectors.living().and(EntitySelectors.exclude(player)));
        if(!reason.isEmpty())
        {
            for(Object entityLiving:reason)
            {
                ctx.attack((EntityLiving)entityLiving,getDamage(ctx.getSkillExp()));

            }
            explode();
            terminate();
            missile.setDead();
        }
        else
        {
            Vec3 pos=Vec3.createVectorHelper(missile.posX,missile.posY,missile.posZ);
            MovingObjectPosition trace = Raytrace.rayTraceBlocks(world(),pos,pos.addVector(targetVec.xCoord,targetVec.yCoord,targetVec.zCoord), BlockSelectors.filNormal);
            if(trace!=null)
            {
                explode();
                missile.setDead();
                terminate();
            }

        }
    }

    @SideOnly(Side.CLIENT)
    @Listener(channel = MSG_SYNC,side={Side.CLIENT})
    private void syncPosition(Vec3 pos)
    {
        if (missile!=null)
        {
            missile.setPosition(pos.xCoord, pos.yCoord, pos.zCoord);
            AcademyMoreVanilla.log.info("move unit.");
        }
        else
        {
            sendToServer(MSG_ACQUIRE);
        }
    }

    @SideOnly(Side.SERVER)
    @Listener(channel=MSG_ACQUIRE,side={Side.SERVER})
    private void getAcquired(){
        AcademyMoreVanilla.log.info("key = ");
        sendToClient(MSG_SPAWN_POS,missile);
    }

    @SideOnly(Side.CLIENT)
    @Listener(channel=MSG_SPAWN_POS,side={Side.CLIENT})
    private void spawnMissile(TrickShotMissile shotMissile)
    {
        AcademyMoreVanilla.log.info("try.");
        missile=shotMissile;
        if(shotMissile==null)
        {
            AcademyMoreVanilla.log.info("failed to init: argument is null.");
        }


    }

    @Override
    public void terminate()
    {
        missile.setDead();
        super.terminate();
        missile=null;
    }
    private float getDamage(float exp){
        return lerpf(6, 12, exp);
    }
}

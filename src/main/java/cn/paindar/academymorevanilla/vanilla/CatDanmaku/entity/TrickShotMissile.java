package cn.paindar.academymorevanilla.vanilla.CatDanmaku.entity;

import cn.lambdalib.annoreg.core.Registrant;
import cn.lambdalib.annoreg.mc.RegEntity;
import cn.lambdalib.util.entityx.EntityAdvanced;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;

/**
 * Created by Paindar on 2016/12/29.
 */

@Registrant
@RegEntity
public class TrickShotMissile extends EntityAdvanced
{
    private EntityPlayer spawner;
    public float size=1;
    public float maxSize;
    double expandRate;
    public double speed=1;
    public double alphaWiggle = 0.5;
    public int r,g,b;
    int texturesId=0;
    public int life=0;
    static final int MAX_TETXURES = 5;
    static IIcon itemIcon ;

    public TrickShotMissile(EntityPlayer player)
    {
        super(player.getEntityWorld());
        spawner=player;
        this.isDead=false;
    }

    protected EntityPlayer getSpawner() {
        return spawner;
    }
    public void setExpandRate(float rate){expandRate=rate;}
    public void setSpeed(float speed){this.speed=speed;}
    public double getAlpha(){return alphaWiggle;}
    public int getTexturesId(){return texturesId;}
    public void setRGB(int r,int g,int b)
    {
        this.r=r;
        this.g=g;
        this.b=b;
    }
    @Override
    public void entityInit() {
        dataWatcher.addObject(3, Integer.valueOf(0));
        dataWatcher.addObject(4, Float.valueOf(0));
        dataWatcher.addObject(5, Float.valueOf(0));
        dataWatcher.addObject(6, Float.valueOf(0));
        dataWatcher.addObject(7, Integer.valueOf(0));
    }

    @SideOnly(Side.CLIENT)
    public boolean updateRenderTick()
    {
        if(rand.nextInt(8) < 2) {
            texturesId = rand.nextInt(MAX_TETXURES);
        }
        return spawner!=null;
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {}

    @Override
    protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {}

}

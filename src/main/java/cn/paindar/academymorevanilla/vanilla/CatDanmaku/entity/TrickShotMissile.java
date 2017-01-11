package cn.paindar.academymorevanilla.vanilla.CatDanmaku.entity;


import cn.academy.core.client.ACRenderingHelper;
import cn.lambdalib.annoreg.core.Registrant;
import cn.lambdalib.annoreg.mc.RegEntity;
import cn.lambdalib.template.client.render.entity.RenderIcon;
import cn.lambdalib.util.client.RenderUtils;
import cn.lambdalib.util.client.shader.ShaderSimple;
import cn.lambdalib.util.entityx.EntityAdvanced;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

/**
 * Created by Paindar on 2016/12/29.
 */

public class TrickShotMissile extends EntityAdvanced
{

    EntityPlayer spawner;
    public float size=1;
    public float maxSize;
    double expandRate;
    public double speed=1;
    public double alphaWiggle = 0.5;
    public int r,g,b;
    int texturesId=0;
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
    protected void entityInit() {

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
    protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {setDead();}

    @Override
    protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {}

}

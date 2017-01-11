package cn.paindar.academymorevanilla.client.render;

import cn.lambdalib.template.client.render.entity.RenderIcon;
import cn.lambdalib.util.client.RenderUtils;
import cn.lambdalib.util.client.shader.ShaderSimple;
import cn.lambdalib.util.deprecated.ViewOptimize;
import cn.lambdalib.util.helper.Color;
import cn.paindar.academymorevanilla.vanilla.CatDanmaku.entity.TrickShotMissile;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL20;

/**
 * Created by Paindar on 2016/12/30.
 */
@SideOnly(Side.CLIENT)
public class RenderTrickShotMissile extends RenderIcon
{
    int MAX_TETXURES=5;
    ResourceLocation[] textures=new ResourceLocation[MAX_TETXURES];
    static ResourceLocation glowTexture=new ResourceLocation("academymorevanilla:textures/effects/trickshot/glow.png");
    boolean hasLight=false;
    Color color=Color.white();
    //debug
    static int count=0;

    public RenderTrickShotMissile() {
        super(glowTexture);
        String baseName = "academymorevanilla:textures/effects/trickshot/";
        for(int i = 0; i < MAX_TETXURES; ++i) {
            textures[i] = new ResourceLocation(baseName + i+".png" );
        }
        //this.minTolerateAlpha = 0.05f;
        //this.shadowOpaque = 0;
    }
    @Override
    public void doRender(Entity par1Entity, double x, double y,
                         double z, float par8, float par9) {
        if (RenderUtils.isInShadowPass()) {
            return;
        }
        TrickShotMissile ent = (TrickShotMissile) par1Entity;
        if(!ent.updateRenderTick())
            return;
        color.setColor4i(ent.r,ent.g,ent.b,128);
        if(!ent.isDead)
        {
            count++;
            if(count%20==0)
                System.out.println("doRender in ");
        }
        super.doRender(ent,x,y,z,par8,par9);
        /*
        GL11.glPushMatrix();

        {
            ShaderSimple.instance().useProgram();

            double alpha = ent.getAlpha();
            float size = ent.size;

            //Glow texture
            this.color.r = ent.r;
            this.color.g = ent.g;
            this.color.b = ent.b;
            this.color.a = alpha * (0.3 + ent.alphaWiggle * 0.7);
            this.icon = glowTexture;
            this.setSize(0.7f * size);
            super.doRender(par1Entity, x, y, z, par8, par9);

            //Core
            this.color.r = ent.r;
            this.color.g = ent.g;
            this.color.b = ent.b;
            this.color.a = alpha * (0.8 + 0.2 * ent.alphaWiggle);
            this.icon = textures[ent.getTexturesId()];
            this.setSize(0.5f * size);
            super.doRender(par1Entity, x, y, z, par8, par9);
            GL20.glUseProgram(0);
        }
        GL11.glPopMatrix();
        */

    }


    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     *
     * @param p_110775_1_
     */
    @Override
    protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
        return glowTexture;
    }
}

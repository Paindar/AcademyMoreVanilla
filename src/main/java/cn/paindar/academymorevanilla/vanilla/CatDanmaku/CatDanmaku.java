package cn.paindar.academymorevanilla.vanilla.CatDanmaku;

import cn.academy.ability.api.Category;
import cn.academy.vanilla.ModuleVanilla;
import cn.paindar.academymorevanilla.vanilla.CatDanmaku.skill.TrickShot;
import cpw.mods.fml.common.Mod.Instance;


/**
 * Created by Paindar on 2016/12/26.
 */
public class CatDanmaku extends Category {

    public static final TrickShot trickShot=TrickShot.instance;
    public static final CatDanmaku instance=new CatDanmaku();
    public CatDanmaku()
    {
        super("danmakumaster");
        colorStyle.fromHexColor(0xffff80c0);

        trickShot.setPosition(15, 45);

        this.addSkill(trickShot);
        ModuleVanilla.addGenericSkills(this);


    }


}

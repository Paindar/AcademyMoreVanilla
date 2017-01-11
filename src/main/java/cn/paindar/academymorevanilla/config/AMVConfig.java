package cn.paindar.academymorevanilla.config;

import cn.academy.core.config.ACConfig;
import cn.lambdalib.util.generic.RegistryUtils;
import cn.paindar.academymorevanilla.core.AcademyMoreVanilla;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.nio.file.Files;

/**
 * Created by Paindar on 2016/12/29.
 */
public class AMVConfig
{
    private static Config config;

    public static void init() {
        Logger log = AcademyMoreVanilla.log;

        ResourceLocation defaultRes = new ResourceLocation("academymorevanilla:config/default.conf");

        Reader reader = new InputStreamReader(RegistryUtils.getResourceStream(defaultRes));

        config = ConfigFactory.parseReader(reader);
        File path=new File("config/academymorevanilla");
        if  (!path.exists()  && !path.isDirectory())
            path.mkdir();
        File customFile = new File("config/academymorevanilla/data.conf");
        if (!customFile.isFile()) {
            try {
                Files.copy(RegistryUtils.getResourceStream(defaultRes), customFile.toPath());
            } catch (IOException ex) {
                log.error("Error when copying config template to config folder", ex);
            }
        }

        try {
            Config customConfig = ConfigFactory.parseFile(customFile);

            config = customConfig.withFallback(config);
        } catch (RuntimeException ex) {
            log.error("An error occured parsing custom config", ex);
        }
        Config originConfig;
        try{
            Field field=ACConfig.class.getDeclaredField("config");
            field.setAccessible(true);
            originConfig=(Config)field.get(ACConfig.instance());
            config=originConfig.withFallback(config);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


        ACConfig.updateConfig(config);
        log.info("Sync config successfully.");
    }
}

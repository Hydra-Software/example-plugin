package org.example.plugin;

import com.hydraclient.hydra.api.HydraPlugin;
import com.hydraclient.hydra.api.IPluginManager;
import com.hydraclient.hydra.api.utils.render.Renderer3D;
import org.example.plugin.command.ExampleCommand;
import org.example.plugin.module.ExampleModule;

import com.hydraclient.hydra.api.utils.render.Renderer2D;
import org.example.plugin.utils.RenderUtils;

public class PluginMain implements HydraPlugin {

    /**
     * Plugin entry
     * Register commands and modules here
     */
    @Override
    public void onLoad(IPluginManager pluginManager) {

        // first parameter is the command name and aliases (if any) in lowercase
        pluginManager.registerCommand(new String[]{"examplecommand", "ec"}, new ExampleCommand(), this);

        pluginManager.registerModule(ExampleModule.class, this);

        System.out.println("Loaded example plugin");

        RenderUtils.renderer2d = pluginManager.getRenderer2d();
        RenderUtils.renderer3d = pluginManager.getRenderer3d();
    }

    /**
     * Plugin exit
     * Disable any breaking changes
     */
    @Override
    public void onUnload() {
        System.out.println("Unloaded example plugin");
    }

    /**
     * Like fabric modid, but for plugins
     * @return Unique identifier for your plugin
     */
    @Override
    public String getID() {
        return "ExamplePlugin";
    }
}

package org.example.plugin.module;

import com.hydraclient.hydra.api.AnticheatUtil;
import com.hydraclient.hydra.api.event.Event;
import com.hydraclient.hydra.api.event.EventTarget;
import com.hydraclient.hydra.api.event.gameplay.EventUpdate;
import com.hydraclient.hydra.api.event.movement.EventPostTickNoVehicle;
import com.hydraclient.hydra.api.event.render.EventRender2D;
import com.hydraclient.hydra.api.event.render.EventRender3D;
import com.hydraclient.hydra.api.module.Category;
import com.hydraclient.hydra.api.module.RotationModule;
import com.hydraclient.hydra.api.setting.ColorSetting;
import com.hydraclient.hydra.api.setting.Setting;
import com.hydraclient.hydra.api.setting.SettingGroup;
import com.hydraclient.hydra.api.utils.entity.EntityUtils;
import com.hydraclient.hydra.api.utils.gameplay.ChatUtils;
import com.hydraclient.hydra.api.utils.render.Color;
import com.hydraclient.hydra.api.utils.render.ScreenPos;
import com.hydraclient.hydra.api.utils.world.Rotation;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.example.plugin.event.EventCustomInput;
import org.example.plugin.utils.RenderUtils;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.Arrays;

public class ExampleModule extends RotationModule {

    /**
     * Click actions are useful for operations that only need to be performed when a setting value changes
     */
    Setting value = new Setting(this, "Boolean Setting", true, new Setting.ClickAction() {
        @Override
        public void action(Setting setting) {
            ChatUtils.message("Value was changed");
        }
    });

    Setting value2 = new Setting(this, "Int Setting", 5, 1, 10);
    Setting value3 = new Setting(this, "Float Setting", 0.5f, 0.1f, 0.77f);
    Setting value4 = new Setting(this, "String Setting", "Foo", new ArrayList<>(Arrays.asList("Foo","Bar")));

    /**
     * Color can be passed as rgb or rgba
     * canChangeAlpha determines if a slider should be present for the alpha value.
     */
    ColorSetting color = new ColorSetting(this, "Color Setting", new Color(255, 255, 255), false);

    /**
     * Setting groups can be used to combine multiple settings into a single drop down setting
     * Useful for modules with lots of settings
     * Subsettings of the group should not be registered in the constructor.
     */
    SettingGroup group = new SettingGroup(this, "Setting Group", value, value2, value3, value4);

    /**
     * Module will be shown as "ExampleModule" in clickgui and module list
     * GLFW_KEY_UNKNOWN for no default bind
     * "visible" arg dictates if the module will be shown in module list hud by default. This can be changed by the user with the drawn command
     * Setting registration is done in the constructor
     * Constructors must have no additional parameters, or the module will fail to load.
     */
    public ExampleModule(AnticheatUtil anticheat) {
        super("ExampleModule", GLFW.GLFW_KEY_UNKNOWN, Category.COMBAT, true, anticheat);
        addSetting(group);
        addSetting(color);
    }

    /**
     * Update is called at the beginning of each tick
     * Most recurring calculations and rotation target selection should be done here
     * Event listeners should all be private with a return type of void
     * @param e The event passed to the listener
     */
    @EventTarget
    private void onUpdate(EventUpdate e) {
        new EventCustomInput().call();
        Vec3d pos = mc.player.getPos();

        // set rotation target before tick
        updateTarget(new Rotation(0, 0));
    }

    /**
     * Perform actions requiring rotation after the tick
     * Always check for priority
     */
    @EventTarget
    private void onRotate(EventPostTickNoVehicle e) {
        if(hasPriority()) {
            // interact with target
        }
    }

    /**
     * Do rendering for block highlights, and other world items in Render3D
     */
    @EventTarget
    private void onRender3D(EventRender3D e) {
        RenderUtils.renderer3d.setup();
        RenderUtils.renderer3d.transformForBox(mc.player.getBoundingBox().offset(5, 0, 0));
        RenderUtils.renderer3d.setColor(color.getColor(), 1);
        RenderUtils.renderer3d.outlineBox(new Box(0, 0, 0, 1, 1, 1));
        RenderUtils.renderer3d.cleanup();
    }

    /**
     * Overlay items should be rendered in 2d. This includes text and any hud or gui drawing.
     * For rendering overlays at a point in the world, use worldToScreen to project the point in world space to screen space.
     */
    @EventTarget
    private void onRender2D(EventRender2D e) {
        ScreenPos pos = RenderUtils.renderer2d.worldToScreen(EntityUtils.getInterpolatedPos(mc.player, e.getTickDelta()).add(10, 2, 0));

        if(pos.visible) {
            RenderUtils.renderer2d.drawCenteredString(e.getMatrixStack(), "projection", pos.x, pos.y, color.getInt());
        }
    }

    /**
     * You can create and call your own events, for use with other plugins
     */
    @EventTarget
    private void onCustomEvent(EventCustomInput e) {
        if(e.getType() == Event.Type.PRE) {
            // meow!
        }
    }
}

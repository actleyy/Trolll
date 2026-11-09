package com.example.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

public class ExampleModClient implements ClientModInitializer {
  public static boolean enabled = false;
  public static double range = 6;
  static KeyBinding openKey;

  @Override
  public void onInitializeClient() {
    KeyBinding.Category cat = KeyBinding.Category.create(Identifier.of("troll", "main"));
    openKey = KeyBindingHelper.registerKeyBinding(
      new KeyBinding("key.troll.open", GLFW.GLFW_KEY_RIGHT_SHIFT, cat));

    ClientTickEvents.END_CLIENT_TICK.register(mc -> {
      while (openKey.wasPressed()) mc.setScreen(new MenuScreen());
      if (!enabled || mc.player == null || mc.world == null) return;

      Entity target = null;
      double best = range * range;
      for (Entity e : mc.world.getEntities()) {
        if (e == mc.player || !(e instanceof LivingEntity)) continue;
        double d = mc.player.squaredDistanceTo(e);
        if (d < best) { best = d; target = e; }
      }
      if (target != null) {
        Vec3d diff = target.getEyePos().subtract(mc.player.getEyePos());
        double flat = Math.sqrt(diff.x * diff.x + diff.z * diff.z);
        mc.player.setYaw((float) (Math.toDegrees(Math.atan2(diff.z, diff.x)) - 90));
        mc.player.setPitch((float) -Math.toDegrees(Math.atan2(diff.y, flat)));
      }
    });
  }
}

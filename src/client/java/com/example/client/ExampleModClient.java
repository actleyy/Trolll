package com.example.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.glfw.GLFW;

public class ExampleModClient implements ClientModInitializer {
  public static boolean enabled = false;
  public static double range = 6;
  static KeyMapping openKey;

  @Override
  public void onInitializeClient() {
    KeyMapping.Category cat = KeyMapping.Category.register(Identifier.fromNamespaceAndPath("troll", "main"));
    openKey = KeyBindingHelper.registerKeyBinding(
      new KeyMapping("key.troll.open", GLFW.GLFW_KEY_RIGHT_SHIFT, cat));

    ClientTickEvents.END_CLIENT_TICK.register(mc -> {
      while (openKey.consumeClick()) mc.setScreen(new MenuScreen());
      if (!enabled || mc.player == null || mc.level == null) return;

      Entity target = null;
      double best = range * range;
      for (Entity e : mc.level.entitiesForRendering()) {
        if (e == mc.player || !(e instanceof LivingEntity)) continue;
        double d = mc.player.distanceToSqr(e);
        if (d < best) { best = d; target = e; }
      }
      if (target != null) {
        Vec3 diff = target.getEyePosition().subtract(mc.player.getEyePosition());
        double flat = Math.sqrt(diff.x * diff.x + diff.z * diff.z);
        mc.player.setYRot((float) (Math.toDegrees(Math.atan2(diff.z, diff.x)) - 90));
        mc.player.setXRot((float) -Math.toDegrees(Math.atan2(diff.y, flat)));
      }
    });
  }
}

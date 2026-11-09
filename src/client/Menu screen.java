package com.example.client;

import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class MenuScreen extends Screen {
  public MenuScreen() { super(Component.literal("Troll")); }

  @Override
  protected void init() {
    addRenderableWidget(Button.builder(Component.literal("Hello"),
      b -> ExampleModClient.enabled = !ExampleModClient.enabled)
      .bounds(width / 2 - 50, height / 2 - 30, 100, 20).build());

    addRenderableWidget(new AbstractSliderButton(width / 2 - 75, height / 2, 150, 20,
        Component.literal("Range: " + (int) ExampleModClient.range),
        (ExampleModClient.range - 1) / 19) {
      @Override protected void updateMessage() {
        setMessage(Component.literal("Range: " + (int) ExampleModClient.range));
      }
      @Override protected void applyValue() {
        ExampleModClient.range = 1 + value * 19;
      }
    });
  }
}

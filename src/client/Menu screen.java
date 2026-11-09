package com.example.client;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;

public class MenuScreen extends Screen {
  public MenuScreen() { super(Text.literal("Troll")); }

  @Override
  protected void init() {
    addDrawableChild(ButtonWidget.builder(Text.literal("Hello"),
      b -> ExampleModClient.enabled = !ExampleModClient.enabled)
      .dimensions(width / 2 - 50, height / 2 - 30, 100, 20).build());

    addDrawableChild(new SliderWidget(width / 2 - 75, height / 2, 150, 20,
        Text.literal("Range: " + (int) ExampleModClient.range),
        (ExampleModClient.range - 1) / 19) {
      @Override protected void updateMessage() {
        setMessage(Text.literal("Range: " + (int) ExampleModClient.range));
      }
      @Override protected void applyValue() {
        ExampleModClient.range = 1 + value * 19;
      }
    });
  }
}

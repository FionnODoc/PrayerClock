package com.prayerclock;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import com.google.inject.Inject;
import net.runelite.api.Client;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.PanelComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

class ClockOverlay extends Overlay {
    private final Client client;
    private final PrayerClockConfig config;
    private final PanelComponent panelComponent = new PanelComponent();
    private int ticks;
    private boolean flash;

    @Inject
    private ClockOverlay(Client client, PrayerClockConfig config) {
        this.client = client;
        this.config = config;
        setPosition(OverlayPosition.ABOVE_CHATBOX_RIGHT);
    }

    public void incrementTicks() {
        ticks++;
        if (ticks >= config.ticksTillFlash()){
            flash = !flash;
        }
        else{
            flash = false;
        }
    }

    public void resetTicks() {
        ticks = 0;
    }

    @Override
    public Dimension render(Graphics2D graphics2D) {
        panelComponent.getChildren().clear();
        String overlayTitle = "";

        panelComponent.getChildren().add(TitleComponent.builder()
                .text(overlayTitle + ticks)
                .color(flash ? Color.GREEN : Color.WHITE)
                .build());

        panelComponent.setPreferredSize(new Dimension(
                graphics2D.getFontMetrics().stringWidth(overlayTitle) + 80,
                0
        ));

        return panelComponent.render(graphics2D);
    }
}
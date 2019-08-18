package com.chaosbuffalo.mkultra.client.gui;

import com.chaosbuffalo.mkultra.MKUltra;
import com.chaosbuffalo.mkultra.client.gui.lib.*;
import com.chaosbuffalo.mkultra.core.IPlayerData;
import com.chaosbuffalo.mkultra.core.PlayerPassiveAbility;
import com.chaosbuffalo.mkultra.log.Log;
import com.chaosbuffalo.mkultra.network.packets.ActivatePassivePacket;
import com.chaosbuffalo.mkultra.network.packets.AddRemoveTalentPointPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.HashSet;

public class PassiveAbilityButton extends MKButton {
    private static int X_POS_TALENT_SLOT_TEX = 22;
    private static int Y_POS_TALENT_SLOT_TEX = 259;
    private static int TALENT_SLOT_WIDTH = 20;
    private static int TALENT_SLOT_HEIGHT = 20;
    private static final int DROP_DOWN_WIDTH = 90;
    private static final int WIDTH = 70;
    private static int ICON_WIDTH = 16;
    private static int ICON_HEIGHT = 16;
    private static final int SLOT_Y_OFFSET = 4;
    private static final int TEXT_OFFSET = 4;
    private static final int SLOT_X_OFFSET = (WIDTH - TALENT_SLOT_WIDTH) / 2;
    public static final int HEIGHT = TALENT_SLOT_HEIGHT + TEXT_OFFSET + SLOT_Y_OFFSET + UIConstants.TEXT_HEIGHT;
    private static final int DROPDOWN_HEIGHT = 80;
    private static int ICON_X_OFFSET = SLOT_X_OFFSET + (TALENT_SLOT_WIDTH - ICON_WIDTH) / 2;
    private static int ICON_Y_OFFSET = SLOT_Y_OFFSET + (TALENT_SLOT_HEIGHT  - ICON_HEIGHT) / 2;

    private ArrayList<String> tooltip;

    public final PlayerPassiveAbility ability;
    public final IPlayerData playerData;
    private MKModal dropdown;
    private boolean isDropdownOpen;
    private int slotIndex;

    public PassiveAbilityButton(PlayerPassiveAbility ability, IPlayerData data, int slotIndex, int x, int y){
        super(x, y, WIDTH, HEIGHT, "");
        this.ability = ability;
        this.playerData = data;
        this.tooltip = new ArrayList<>();
        isDropdownOpen = false;
        this.slotIndex = slotIndex;
        if (ability != null){
            tooltip.add(ability.getAbilityName());
            tooltip.add(ability.getAbilityDescription());
        }
    }

    @Override
    public void longHoverDraw(Minecraft mc, int x, int y, int width, int height, int mouseX, int mouseY, float partialTicks) {
        HashSet<PlayerPassiveAbility> learned = playerData.getLearnedPassives();
        if (learned == null || learned.size() == 0){
            if (getScreen() != null){
                getScreen().addHoveringText(new HoveringTextInstruction(
                        I18n.format("mkultra.ui_msg.no_passives"),
                        getParentCoords(new Vec2d(mouseX, mouseY))));
            }
        } else if (ability == null){
            if (getScreen() != null){
                getScreen().addHoveringText(new HoveringTextInstruction(
                        I18n.format("mkultra.ui_msg.learn_passive_prompt"),
                        getParentCoords(new Vec2d(mouseX, mouseY))));
            }
        }
    }

    @Override
    public boolean isInBounds(int x, int y){
        if (this.skipBoundsCheck){
            return true;
        }
        return x >= this.getX() + SLOT_X_OFFSET &&
                y >= this.getY() + SLOT_Y_OFFSET &&
                x < this.getX() + SLOT_X_OFFSET + TALENT_SLOT_WIDTH &&
                y < this.getY() + SLOT_Y_OFFSET + TALENT_SLOT_HEIGHT;
    }


    public MKModal getDropdown(int mouseX, int mouseY){
        HashSet<PlayerPassiveAbility> learned = playerData.getLearnedPassives();
        if (learned == null || learned.size() == 0){
            return null;
        }
        MKModal dropdownModal = new MKModal();
        MKScrollView scrollView = new MKScrollView(mouseX - DROP_DOWN_WIDTH / 2, mouseY, DROP_DOWN_WIDTH, DROPDOWN_HEIGHT, true);
        scrollView.setToTop();
        scrollView.setDoScrollX(false);
        dropdownModal.addWidget(scrollView);
        MKStackLayoutVertical layout = new MKStackLayoutVertical(0, 0, DROP_DOWN_WIDTH - 4);
        layout.setPaddingTop(1).setPaddingBot(1).setMarginTop(2).setMarginBot(2);
        layout.doSetWidth(true);
        scrollView.addWidget(layout);
        for (PlayerPassiveAbility ability : learned){
            MKButton button = new MKButton(ability.getAbilityName());
            layout.addWidget(button);
            button.setPressedCallback((MKButton btn, Integer buttonType) -> {
                if (playerData.canActivatePassiveForSlot(ability.getAbilityId(), slotIndex)){
                    Log.info("sending learn passive packet");
                    MKUltra.packetHandler.sendToServer(new ActivatePassivePacket(ability.getAbilityId(), slotIndex));
                }
                MKScreen screen = getScreen();
                if (screen != null){
                    screen.closeModal(this.dropdown);
                }
                return true;
            });
        }
        scrollView.centerContentX();
        dropdownModal.setOnCloseCallback(() ->
        {
            isDropdownOpen = false;
            dropdown = null;
        });
        return dropdownModal;
    }

    @Override
    public boolean onMousePressed(Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == UIConstants.MOUSE_BUTTON_RIGHT){
            MKModal dropdown = getDropdown(mouseX, mouseY);
            if (dropdown != null && getScreen() != null){
                isDropdownOpen = true;
                this.dropdown = dropdown;
                getScreen().addModal(dropdown);
                return true;
            } else {
                return false;
            }

        } else {
            return super.onMousePressed(minecraft, mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public boolean checkHovered(int mouseX, int mouseY){
        return isVisible() && isEnabled() && isInBounds(mouseX, mouseY) && !isDropdownOpen;
    }

    @Override
    public void draw(Minecraft minecraft, int x, int y, int width, int height, int mouseX, int mouseY, float partialTicks){
        if (this.isVisible()) {
            FontRenderer fontrenderer = minecraft.fontRenderer;
            minecraft.getTextureManager().bindTexture(GuiTextures.CLASS_BACKGROUND_GRAPHIC);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableBlend();
            DrawingUtils.drawTexturedRect(this.getX() + SLOT_X_OFFSET ,
                    this.getY() + SLOT_Y_OFFSET,
                    X_POS_TALENT_SLOT_TEX, Y_POS_TALENT_SLOT_TEX,
                    TALENT_SLOT_WIDTH, TALENT_SLOT_HEIGHT,
                    GuiTextures.CLASS_BACKGROUND_WIDTH, GuiTextures.CLASS_BACKGROUND_HEIGHT);
            if (ability != null){
                ResourceLocation icon = ability.getAbilityIcon();
                minecraft.getTextureManager().bindTexture(icon);
                Gui.drawModalRectWithCustomSizedTexture(this.getX() + ICON_X_OFFSET ,
                        this.getY() + ICON_Y_OFFSET,
                        0, 0,
                        ICON_WIDTH, ICON_HEIGHT, ICON_WIDTH, ICON_HEIGHT);
                int textColor = 14737632;
                if (!this.isEnabled()) {
                    textColor = 10526880;
                } else if (isHovered()) {
                    textColor = 16777120;
                }
                this.drawCenteredString(fontrenderer, ability.getAbilityName(), this.getX() + this.getWidth() / 2,
                        this.getY() + SLOT_Y_OFFSET + TALENT_SLOT_HEIGHT + TEXT_OFFSET, textColor);
                if (isHovered()){
                    if (getScreen() != null){
                       getScreen().addHoveringText(new HoveringTextInstruction(tooltip,
                                getParentCoords(new Vec2d(mouseX, mouseY))));
                    }
                }
            }

        }
    }
}
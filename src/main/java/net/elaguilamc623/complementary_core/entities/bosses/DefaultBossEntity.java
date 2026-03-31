package net.elaguilamc623.complementary_core.entities.bosses;

import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public abstract class DefaultBossEntity extends Monster {

    protected boolean bossBarEnabled = false;
    protected boolean isSleep = true;
    protected boolean isWakingUp = false;

    protected DefaultBossEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    public void wakeUp() {
        this.isSleep = false;
        playWakeUpAnimation();
    }

    public abstract void playWakeUpAnimation();

    @Override
    protected void registerGoals() {
    }

    @Override
    public void tick() {
        super.tick();

        if (this.tickCount < 20) {
            return;
        }

        if (this.isSleep && !this.isWakingUp) {

            Player nearest = this.level().getNearestPlayer(this, 10.0F);

            if (nearest != null) {
                this.isSleep = false;
                this.isWakingUp = true;
                this.wakeUp();
            }
        }
    }

    protected final ServerBossEvent playerBossEvent =
            new ServerBossEvent(this.getType().getDescription(), BossEvent.BossBarColor.BLUE, BossEvent.BossBarOverlay.PROGRESS);

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        if (bossBarEnabled) {
            playerBossEvent.addPlayer(player);
        }
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        playerBossEvent.removePlayer(player);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        playerBossEvent.setProgress(this.getHealth() / this.getMaxHealth());
    }
}

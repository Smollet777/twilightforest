package twilightforest.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackRanged;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import twilightforest.TFAchievementPage;
import twilightforest.TFSounds;
import twilightforest.TwilightForestMod;
import twilightforest.client.particle.TFParticleType;

public class EntityTFIceShooter extends EntityMob implements IRangedAttackMob {

	public EntityTFIceShooter(World par1World) {
		super(par1World);
        this.setSize(0.8F, 1.8F);
	}

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAttackRanged(this, 1.25D, 20, 10.0F));
        this.tasks.addTask(2, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(3, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, 0, true, false, null));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
    }
    
    @Override
    public float getEyeHeight()
    {
        return this.height * 0.6F;
    }
 
    @Override
    protected Item getDropItem()
    {
        return Items.SNOWBALL;
    }

    @Override
    public void onLivingUpdate()
    {
    	super.onLivingUpdate();
    	// make snow particles
    	for (int i = 0; i < 3; i++) {
	    	float px = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.3F;
	    	float py = this.getEyeHeight() + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.5F;
	    	float pz = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.3F;
	    	
			TwilightForestMod.proxy.spawnParticle(this.world, TFParticleType.SNOW_GUARDIAN, this.lastTickPosX + px, this.lastTickPosY + py, this.lastTickPosZ + pz, 0, 0, 0);
    	}

    }
    
    @Override
	protected SoundEvent getAmbientSound()
    {
        return TFSounds.ICE_AMBIENT;
    }
    
    @Override
    protected SoundEvent getHurtSound()
    {
        return TFSounds.ICE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return TFSounds.ICE_DEATH;
    }

	@Override
	public void onDeath(DamageSource par1DamageSource) {
		super.onDeath(par1DamageSource);
		if (par1DamageSource.getSourceOfDamage() instanceof EntityPlayer) {
			((EntityPlayer)par1DamageSource.getSourceOfDamage()).addStat(TFAchievementPage.twilightHunter);
		}
	}

	@Override
    public int getMaxSpawnedInChunk()
    {
        return 8;
    }

	@Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float par2)
    {
		EntityTFIceSnowball snowball = new EntityTFIceSnowball(this.world, this);

		// [VanillaCopy] Part of EntitySkeleton.attackEntityWithRangedAttack
        double d0 = target.posX - this.posX;
        double d1 = target.getEntityBoundingBox().minY + (double)(target.height / 3.0F) - snowball.posY;
        double d2 = target.posZ - this.posZ;
        double d3 = (double)MathHelper.sqrt(d0 * d0 + d2 * d2);
        snowball.setThrowableHeading(d0, d1 + d3 * 0.20000000298023224D, d2, 1.6F, (float)(14 - this.world.getDifficulty().getDifficultyId() * 4));

        this.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        this.world.spawnEntity(snowball);
    }
}

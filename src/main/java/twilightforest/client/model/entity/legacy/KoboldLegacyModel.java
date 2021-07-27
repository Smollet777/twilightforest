// Date: 6/11/2012 3:12:45 PM
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX

package twilightforest.client.model.entity.legacy;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import twilightforest.entity.KoboldEntity;

public class KoboldLegacyModel extends HumanoidModel<KoboldEntity> {
	//fields

	ModelPart rightear;
	ModelPart leftear;
	ModelPart snout;
	ModelPart jaw;

	boolean isJumping;

	public KoboldLegacyModel() {
		super(0.0F);

		isJumping = false;

//		textureWidth = 64;
//		textureHeight = 32;

		head = new ModelPart(this, 0, 0);
		head.addBox(-3.5F, -7F, -3F, 7, 6, 6);
		head.setPos(0F, 13F, 0F);

		body = new ModelPart(this, 12, 19);
		body.addBox(0F, 0F, 0F, 7, 7, 4);
		body.setPos(-3.5F, 12F, -2F);

		rightArm = new ModelPart(this, 36, 17);
		rightArm.addBox(-3F, -1F, -1.5F, 3, 7, 3);
		rightArm.setPos(-3.5F, 12F, 0F);

		leftArm.mirror = true;
		leftArm = new ModelPart(this, 36, 17);
		leftArm.addBox(0F, -1F, -1.5F, 3, 7, 3);
		leftArm.setPos(3.5F, 12F, 0F);

		leftArm.mirror = false;
		rightLeg = new ModelPart(this, 0, 20);
		rightLeg.addBox(-1.5F, 0F, -1.5F, 3, 5, 3);
		rightLeg.setPos(-2F, 19F, 0F);

		leftLeg = new ModelPart(this, 0, 20);
		leftLeg.addBox(-1.5F, 0F, -1.5F, 3, 5, 3);
		leftLeg.setPos(2F, 19F, 0F);

		rightear = new ModelPart(this, 48, 20);
		rightear.addBox(0F, -4F, 0F, 4, 4, 1);
		rightear.setPos(3.5F, -3F, -1F);
		rightear.yRot = 0.2617994F;
		rightear.zRot = -0.3490659F;

		head.addChild(rightear);

		leftear = new ModelPart(this, 48, 25);
		leftear.addBox(-4F, -4F, 0F, 4, 4, 1);
		leftear.setPos(-3.5F, -3F, -1F);
		leftear.yRot = -0.2617994F;
		leftear.zRot = 0.3490659F;

		head.addChild(leftear);

		snout = new ModelPart(this, 28, 0);
		snout.addBox(-1.5F, -2F, -2F, 3, 2, 3);
		snout.setPos(0F, -2F, -3F);

		head.addChild(snout);

		jaw = new ModelPart(this, 28, 5);
		jaw.addBox(-1.5F, 0F, -2F, 3, 1, 3);
		jaw.setPos(0F, -2F, -3F);
		jaw.xRot = 0.20944F;

		head.addChild(jaw);
	}

	@Override
	public void setupAnim(KoboldEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.head.yRot = netHeadYaw / (180F / (float) Math.PI);
		this.head.xRot = headPitch / (180F / (float) Math.PI);

		this.rightArm.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F;
		this.leftArm.xRot = Mth.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
		this.rightArm.zRot = 0.0F;
		this.leftArm.zRot = 0.0F;

		this.rightArm.xRot = -((float) Math.PI * .15F);
		this.leftArm.xRot = -((float) Math.PI * .15F);

		this.rightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.leftLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
		this.rightLeg.yRot = 0.0F;
		this.leftLeg.yRot = 0.0F;

		this.rightArm.zRot += Mth.cos(ageInTicks * 0.19F) * 0.15F + 0.05F;
		this.leftArm.zRot -= Mth.cos(ageInTicks * 0.19F) * 0.15F + 0.05F;
		this.rightArm.xRot += Mth.sin(ageInTicks * 0.267F) * 0.25F;
		this.leftArm.xRot -= Mth.sin(ageInTicks * 0.267F) * 0.25F;

		if (this.isJumping) {
			// open jaw
			this.jaw.xRot = 1.44F;
		} else {
			this.jaw.xRot = 0.20944F;
		}
	}

	@Override
	public void prepareMobModel(KoboldEntity entity, float limbSwing, float limbSwingAmount, float partialTicks) {
		// check if entity is jumping
		this.isJumping = entity.getDeltaMovement().y() > 0;
	}

//	@Override
//	public void render(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
//		setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
//	}

	@Override
	protected Iterable<ModelPart> headParts() {
		return ImmutableList.of(head);
	}

	@Override
	protected Iterable<ModelPart> bodyParts() {
		return ImmutableList.of(
				body,
				rightArm,
				leftArm,
				rightLeg,
				leftLeg
		);
	}
}
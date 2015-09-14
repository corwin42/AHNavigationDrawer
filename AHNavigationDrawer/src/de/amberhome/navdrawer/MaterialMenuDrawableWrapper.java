package de.amberhome.navdrawer;

import anywheresoftware.b4a.AbsObjectWrapper;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BA.ActivityObject;
import anywheresoftware.b4a.BA.DependsOn;
import anywheresoftware.b4a.BA.ShortName;
import de.amberhome.navdrawer.internal.MaterialMenuDrawable;
import de.amberhome.navdrawer.internal.MaterialMenuDrawable.AnimationState;
import de.amberhome.navdrawer.internal.MaterialMenuDrawable.IconState;
import de.amberhome.navdrawer.internal.MaterialMenuDrawable.Stroke;

@ShortName("MaterialMenuDrawable")
@DependsOn(values = { "nineoldandroids-2.4.0" })
@ActivityObject
public class MaterialMenuDrawableWrapper extends AbsObjectWrapper<MaterialMenuDrawable> {

	public IconState ICONSTATE_ARROW = MaterialMenuDrawable.IconState.ARROW;
	public IconState ICONSTATE_BURGER = MaterialMenuDrawable.IconState.BURGER;
	public IconState ICONSTATE_CHECK = MaterialMenuDrawable.IconState.CHECK;
	public IconState ICONSTATE_X = MaterialMenuDrawable.IconState.X;
	
	public Stroke STROKE_THIN = MaterialMenuDrawable.Stroke.THIN;
	public Stroke STROKE_EXTRA_THIN = MaterialMenuDrawable.Stroke.EXTRA_THIN;
	public Stroke STROKE_REGULAR = MaterialMenuDrawable.Stroke.REGULAR;

	public AnimationState ANIMSTATE_ARROW_CHECK = MaterialMenuDrawable.AnimationState.ARROW_CHECK;
	public AnimationState ANIMSTATE_ARROW_X = MaterialMenuDrawable.AnimationState.ARROW_X;
	public AnimationState ANIMSTATE_BURGER_ARROW = MaterialMenuDrawable.AnimationState.BURGER_ARROW;
	public AnimationState ANIMSTATE_BURGER_CHECK = MaterialMenuDrawable.AnimationState.BURGER_CHECK;
	public AnimationState ANIMSTATE_BURGER_X = MaterialMenuDrawable.AnimationState.BURGER_X;
	public AnimationState ANIMSTATE_X_CHECK = MaterialMenuDrawable.AnimationState.X_CHECK;
	
	/**
	 * Initializes the object.
	 *
	 * Color - Color of the icon
	 * Stroke - Stroke width. Use one of the STROKE_XXX constants.
	 */
	public void Initialize(BA ba, int Color, Stroke Stroke) {
		MaterialMenuDrawable mmd = new MaterialMenuDrawable(ba.activity, Color, Stroke);
		setObject(mmd);
	}

	/**
	 * Sets the color of the icon.
	 */
	public void setColor(int Color) {
		getObject().setColor(Color);
	}

	/**
	 * Sets the alpha value for the icon.
	 */
	public void setAlpha(int Alpha) {
		getObject().setAlpha(Alpha);
	}
	
	/**
	 * Sets or gets the current IconState. You can set the current state with this property.
	 * The change is not animated.
	 */
	public void setIconState(IconState IconState) {
		getObject().setIconState(IconState);
	}

	public IconState getIconState() {
		return getObject().getIconState();
	}
	
	/**
	 * Animate the icon to a new state.
	 * 
	 * NewState - The new icon state to which the icon will animate.
	 * DrawTouch - Setting if a touch response should be drawn (ripple effect).
	 */
	public void AnimateIconState(IconState NewState, boolean DrawTouch) {
		getObject().animateIconState(NewState, DrawTouch);
	}
	
	/**
	 * Sets the transformation offset.
	 *  
	 * State - One of the ANIMSTATE_XXX_YYY constants. The Icon will animate from XXX to YYY state.
	 * Offset - A value between 0 and 2. Values between 0 and 1 is for forward animation,
	 * values between 1 and 2 are for backwards animation.
	 */
	public void SetTransformationOffset(AnimationState State, float Offset) {
		getObject().setTransformationOffset(State, Offset);
	}
	
	/**
	 * Sets of gets the current value of the Transformation.
	 */
	public void setTransformationValue(float Value) {
		getObject().setTransformationValue(Value);
	}
	
	public float getTransformationValue() {
		return getObject().getTransformationValue();
	}
	
	/**
	 * Sets, if a touch response should be drawn or not.
	 */
	public void setNeverDrawTouch(boolean Flag) {
		getObject().setNeverDrawTouch(Flag);
	}
	
	/**
	 * Sets the duration of the touch animation (ripple effect).
	 */
	public void setPressedDuration(int Duration) {
		getObject().setPressedDuration(Duration);
	}
	
	/**
	 * Sets the duration of the transformation animation.
	 */
	public void setTransformationDuration(int Duration) {
		getObject().setTransformationDuration(Duration);
	}
	
}

package de.amberhome.navdrawer;

import de.amberhome.navdrawer.internal.SlideDrawable;
import android.graphics.drawable.Drawable;
import anywheresoftware.b4a.AbsObjectWrapper;
import anywheresoftware.b4a.BA.ShortName;

@ShortName("SlideDrawable")
public class SlideDrawableWrapper extends AbsObjectWrapper<SlideDrawable> {

	public void Initialize(Drawable wrapped) {
		SlideDrawable sd = new SlideDrawable(wrapped);
		sd.setOffsetBy((float) (1.0 / 3.0));
		setObject(sd);
	}

	/**
	 * Sets the maximum slide offset. If you set this to 1 the image will be
	 * completely moved outside the visible area. Standard value is 0.33
	 */
	public void setOffsetBy(float Offset) {
		getObject().setOffsetBy(Offset);
	}

	/**
	 * Sets or gets the current offset of the image.
	 * 
	 * If set to 0 the image will be completely visible. If set to 1 the image
	 * will be moved out of the visible area as set by the OffsetBy value.
	 */
	public void setOffset(float Offset) {
		getObject().setOffset(Offset);
	}

	public float getOffset() {
		return getObject().getOffset();
	}

}

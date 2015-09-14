package de.amberhome.navdrawer;

import java.lang.reflect.Field;

import android.graphics.drawable.Drawable;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.LayoutParams;
import android.support.v4.widget.ViewDragHelper;
import android.view.Gravity;
import android.view.View;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BA.ActivityObject;
import anywheresoftware.b4a.BA.Author;
import anywheresoftware.b4a.BA.DependsOn;
import anywheresoftware.b4a.BA.Events;
import anywheresoftware.b4a.BA.ShortName;
import anywheresoftware.b4a.BA.Version;
import anywheresoftware.b4a.objects.PanelWrapper;
import de.amberhome.navdrawer.internal.MyPanelWrapper;

@ActivityObject
@ShortName("AHNavigationDrawer")
@Version(1.4f)
@Author("Markus Stipp")
@DependsOn(values = { "android-support-v4" })
@Events(values = { "DrawerClosed (DrawerGravity as Int)", "DrawerOpened (DrawerGravity as Int)",
		"DrawerSlide (Position As Float, DrawerGravity as Int)", "DrawerStateChanged (State as Int)" })
public class NavigationDrawer {

	private DrawerLayout mNavDrawer;
	private PanelWrapper mContentPanel;
	private PanelWrapper mNavigationPanel;
	private PanelWrapper mContentPanelInternal;
	private PanelWrapper mNavigationPanelInternal;
	private PanelWrapper mNavigationPanel2;
	private BA mba;
	private String mEventName;

	public int DRAWERSTATE_IDLE = DrawerLayout.STATE_IDLE;
	public int DRAWERSTATE_SETTLING = DrawerLayout.STATE_SETTLING;
	public int DRAWERSTATE_DRAGGING = DrawerLayout.STATE_DRAGGING;

	public int GRAVITY_START = GravityCompat.START;
	public int GRAVITY_END = GravityCompat.END;
	public int GRAVITY_LEFT = Gravity.LEFT;
	public int GRAVITY_RIGHT = Gravity.RIGHT;
	
	public int LOCK_MODE_UNLOCKED = DrawerLayout.LOCK_MODE_UNLOCKED;
	public int LOCK_MODE_LOCKED_OPEN = DrawerLayout.LOCK_MODE_LOCKED_OPEN;
	public int LOCK_MODE_LOCKED_CLOSED = DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
			
	public NavigationDrawer() {
	}

	/**
	 * Initializes the navigation drawer. The gravity for the drawer is GRAVITY_START.
	 * 
	 * EventName - Name of the event
	 * Panel - Parent panel for the navigation drawer.
	 * NavWidth - The width of the navigation panel.
	 */
	public void Initialize(BA ba, String EventName, PanelWrapper Panel,
			int NavWidth) {

		Initialize2(ba, EventName, Panel, NavWidth, GravityCompat.START);
	
	}

	/**
	 * Initializes the navigation drawer with the given gravity.
	 * 
	 * EventName - Name of the event
	 * Panel - Parent panel for the navigation drawer.
	 * NavWidth - The width of the navigation panel.
	 * Gravity - The Gravity for the drawer (START, END, LEFT, RIGHT)
	 */
	public void Initialize2(BA ba, String EventName, PanelWrapper Panel,
			int NavWidth, int Gravity) {

		LayoutParams lp = new LayoutParams(Panel.getWidth(), Panel.getHeight());

		mba = ba;
		mEventName = EventName.toLowerCase();

		mNavDrawer = new DrawerLayout(ba.context);
		mNavDrawer.setLayoutParams(lp);

		mContentPanel = new PanelWrapper();
		mContentPanel.Initialize(ba, "");

		mNavigationPanel = new PanelWrapper();
		mNavigationPanel.Initialize(ba, "");

		mContentPanelInternal = new PanelWrapper();
		mContentPanelInternal.Initialize(ba, "");

		mNavigationPanelInternal = new PanelWrapper();
		mNavigationPanelInternal.Initialize(ba, "");

		LayoutParams navlp = new LayoutParams(NavWidth,
				Panel.getHeight(), Gravity);
		
		mNavDrawer.addView(mContentPanelInternal.getObject(),
				Panel.getWidth(), Panel.getHeight());
		mNavDrawer.addView(mNavigationPanelInternal.getObject(), navlp);
		
		mContentPanelInternal.AddView(mContentPanel.getObject(), 0, 0, mContentPanelInternal.getWidth(), mContentPanelInternal.getHeight());
		mNavigationPanelInternal.AddView(mNavigationPanel.getObject(), 0, 0, mNavigationPanelInternal.getWidth(), mNavigationPanelInternal.getHeight());
		
		Panel.getObject().addView(mNavDrawer);
		Panel.getObject().setClickable(true);
		mNavigationPanelInternal.getObject().setClickable(true);
		mContentPanelInternal.getObject().setClickable(true);

		mNavDrawer.setDrawerListener(new DrawerLayout.DrawerListener() {

			@Override
			public void onDrawerClosed(View arg0) {
				if (mba.subExists(mEventName + "_drawerclosed")) {
					android.support.v4.widget.DrawerLayout.LayoutParams lp = (DrawerLayout.LayoutParams)arg0.getLayoutParams();
					mba.raiseEventFromUI(this, mEventName + "_drawerclosed",
							new Object[] { lp.gravity });
				}
			}

			@Override
			public void onDrawerOpened(View arg0) {
				if (mba.subExists(mEventName + "_draweropened")) {
					android.support.v4.widget.DrawerLayout.LayoutParams lp = (DrawerLayout.LayoutParams)arg0.getLayoutParams();
					mba.raiseEventFromUI(this, mEventName + "_draweropened",
							new Object[] { lp.gravity });
				}
			}

			@Override
			public void onDrawerSlide(View arg0, float arg1) {
				if (mba.subExists(mEventName + "_drawerslide")) {
					android.support.v4.widget.DrawerLayout.LayoutParams lp = (DrawerLayout.LayoutParams)arg0.getLayoutParams();
					mba.raiseEvent(this, mEventName + "_drawerslide",
							new Object[] { arg1, lp.gravity });
				}
			}

			@Override
			public void onDrawerStateChanged(int arg0) {
				if (mba.subExists(mEventName + "_drawerstatechanged")) {
					mba.raiseEventFromUI(this, mEventName + "_drawerstatechanged",
							new Object[] { arg0 });
				}
			}

		});
	}
	

	/**
	 * Add a second drawer to the NavigationDrawer. Only two drawers are supported currently.
	 * 
	 * Width - width of the new navigation drawer
	 * Gravity - gravity for the drawer
	 */
	@SuppressWarnings("deprecation")
	public void AddSecondaryDrawer(int Width, int Gravity) {

		mNavigationPanel2 = new MyPanelWrapper();
		mNavigationPanel2.Initialize(mba, "");

		android.support.v4.widget.DrawerLayout.LayoutParams navlp = new android.support.v4.widget.DrawerLayout.LayoutParams(Width,
				LayoutParams.FILL_PARENT, Gravity);
		mNavDrawer.addView(mNavigationPanel2.getObject(), navlp);
	}
	
	
	/**
	 * The Panel object where the content of your activity should go.
	 */
	public PanelWrapper getContentPanel() {
		return mContentPanel;
	}

	/**
	 * The Panel object for the navigation drawer. You should add something like a ListView to this panel. 
	 */
	public PanelWrapper getNavigationPanel() {
		return mNavigationPanel;
	}

	/**
	 * The Panel object for the secondary navigation drawer. You should add something like a ListView to this panel. 
	 */
	public PanelWrapper getSecondaryNavigationPanel() {
		return mNavigationPanel2;
	}	
	
	/**
	 * Opens the Drawer
	 */
	public void OpenDrawer() {
		mNavDrawer.openDrawer(GravityCompat.START);
	}

	/**
	 * Opens the Drawer with the given gravity.
	 * 
	 * Gravity - Gravity of the drawer
	 */
	public void OpenDrawer2(int Gravity) {
		mNavDrawer.openDrawer(Gravity);
	}
	
	/**
	 * Closes the Drawer
	 */
	public void CloseDrawer() {
		mNavDrawer.closeDrawer(GravityCompat.START);
	}

	/**
	 * Closes the Drawer with the given gravity.
	 * 
	 * Gravity - Gravity of the drawer
	 */
	public void CloseDrawer2(int Gravity) {
		mNavDrawer.closeDrawer(Gravity);
	}
	
	/**
	 * Check if the drawer is open.
	 */
	public boolean IsDrawerOpen() {
		return mNavDrawer.isDrawerOpen(GravityCompat.START);
	}

	/**
	 * Check if the drawer is open.
	 * 
	 * Gravity - Gravity of the drawer
	 */
	public boolean IsDrawerOpen2(int Gravity) {
		return mNavDrawer.isDrawerOpen(Gravity);
	}
	
	/**
	 * Sets a shadow drawable for the navigation drawer
	 */
	public void setShadowDrawable(Drawable Drawable) {
		mNavDrawer.setDrawerShadow(Drawable, GravityCompat.START);
	}

	/**
	 * Sets a shadow drawable for the navigation drawer
	 * 
	 * Gravity - Gravity of the drawer
	 * Drawable - Drawable of the shadow
	 */
	public void SetShadowDrawable(int Gravity, Drawable Drawable) {
		mNavDrawer.setDrawerShadow(Drawable, Gravity);
	}

	/**
	 * Set the LockMode of the drawer. You can unlock it or lock it in open or closed state.
	 * 
	 * Gravity - Gravity of the drawer
	 * LockMode - Locking mode
	 */
	public void SetLockMode(int Gravity, int LockMode) {
		mNavDrawer.setDrawerLockMode(LockMode, Gravity);
	}
	
	/**
	 * Get the LockMode of the drawer. You can unlock it or lock it in open or closed state.
	 * 
	 * Gravity - Gravity of the drawer
	 * LockMode - Locking mode
	 */
	public int GetLockMode(int Gravity) {
		return mNavDrawer.getDrawerLockMode(Gravity);
	}

	/**
	 * Get the touch sensitive edge size for the given drawer
	 *
	 * Gravity - Gravity of the drawer
	 */
	public int GetEdgeSize(int Gravity) throws RuntimeException, NoSuchFieldException, IllegalAccessException {
		DrawerLayout mDrawerLayout = mNavDrawer;
		String dragger;
		if (GravityCompat.getAbsoluteGravity(Gravity, ViewCompat.getLayoutDirection(mNavDrawer)) == android.view.Gravity.LEFT)
			dragger = "mLeftDragger";
		else
			dragger = "mRightDragger";
		
		Field mDragger = mDrawerLayout.getClass().getDeclaredField(
		        dragger);
		mDragger.setAccessible(true);
		ViewDragHelper draggerObj = (ViewDragHelper) mDragger
		        .get(mDrawerLayout);

		Field mEdgeSize = draggerObj.getClass().getDeclaredField(
		        "mEdgeSize");
		mEdgeSize.setAccessible(true);
		return mEdgeSize.getInt(draggerObj);
	}
	
	/**
	 * Set the touch sensitive edge size for the given drawer
	 *
	 * Gravity - Gravity of the drawer
	 * EdgeSize - Size of the touch sensitive edge (use dip values)
	 */
	public void SetEdgeSize(int Gravity, int EdgeSize) throws RuntimeException, NoSuchFieldException, IllegalAccessException {
		DrawerLayout mDrawerLayout = mNavDrawer;
		String dragger;
		if (GravityCompat.getAbsoluteGravity(Gravity, ViewCompat.getLayoutDirection(mNavDrawer)) == android.view.Gravity.LEFT)
			dragger = "mLeftDragger";
		else
			dragger = "mRightDragger";
		
		Field mDragger = mDrawerLayout.getClass().getDeclaredField(
		        dragger);
		mDragger.setAccessible(true);
		ViewDragHelper draggerObj = (ViewDragHelper) mDragger
		        .get(mDrawerLayout);

		Field mEdgeSize = draggerObj.getClass().getDeclaredField(
		        "mEdgeSize");
		mEdgeSize.setAccessible(true);

		mEdgeSize.setInt(draggerObj, EdgeSize);
	}

	/**
	 * Let the DrawerLayout fit the system window size (Overlaps status bar)
	 */
	public void setFitsSystemWindows(boolean Flag) {
		mNavDrawer.setFitsSystemWindows(Flag);
	}

	/**
	 * Sets the statusbar background color. Only works if FitsSystemWindows is set to true.
	 */
	public void setStatusBarColor(int Color) {
		mNavDrawer.setStatusBarBackgroundColor(Color);
	}
	
}



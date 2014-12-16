package com.mindstar;

import java.io.File;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.mindstar.util.Util;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.L;

/**
 * @author Sachit Wadhawan
 */
public class UILApplication extends Application {

	private int MAX_IMAGE_SIZE = 150;
	DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
			.build();

	@Override
	public void onCreate() {
		super.onCreate();
//		ACRA.init(this);
		initImageLoader(getApplicationContext());
	}

	public void initImageLoader(Context context) {

		File dir = null;
		String directoryString = "houseenumeration";
		/*
		 * File dir = new File(Environment.getExternalStorageDirectory() +
		 * directoryString);
		 */
		dir = getDir(directoryString, Activity.MODE_PRIVATE);
		if (dir != null && dir.exists() && dir.isDirectory()) {
			// directory exists

		}
		/** create new directory */
		else {
			boolean result = false;
			if (dir != null)
				result = dir.mkdir();
			else {
				// TODO susheel show alert dialog for not connected
				// to SD
				// card
			}
		}

		DisplayMetrics outMetrics = new DisplayMetrics();
		((WindowManager) getApplicationContext().getSystemService(
				Activity.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(
				outMetrics);

		MAX_IMAGE_SIZE = (int) (MAX_IMAGE_SIZE * outMetrics.density);

		Util.setMyApplicationContext(getApplicationContext());
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext())
				.defaultDisplayImageOptions(defaultOptions)
				.threadPoolSize(3)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.memoryCacheExtraOptions(MAX_IMAGE_SIZE, MAX_IMAGE_SIZE)
				// default = device screen dimensions
				// .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
				// .memoryCacheSize(2 * 1024 * 1024)
				// .discCacheExtraOptions(MAX_IMAGE_SIZE, MAX_IMAGE_SIZE,
				// CompressFormat.JPEG, 75)
				/** Adding a directory path to cache the images */
				.discCache(new UnlimitedDiscCache(dir))
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				// .enableLogging() // Not necessary in common
				.build();
		L.disableLogging();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}
}
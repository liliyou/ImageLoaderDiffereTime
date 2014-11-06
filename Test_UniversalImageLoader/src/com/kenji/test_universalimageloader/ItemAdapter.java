package com.kenji.test_universalimageloader;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class ItemAdapter extends BaseAdapter {

	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private Activity context;
	URL_Image list_Image;
	ImageLoader imageLoader;

	DisplayImageOptions options;

	public ItemAdapter(Activity context, DisplayImageOptions options,
			URL_Image list_Image, ImageLoader imageLoader) {
		this.context = context;
		this.list_Image = list_Image;
		this.imageLoader = imageLoader;
		this.options = options;
	}

	private class ViewHolder {
		public TextView text;
		public ImageView image;
	}

	@Override
	public int getCount() {
		return list_Image.IMAGES.length;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = convertView;
		final ViewHolder holder;
		if (convertView == null) {
			view = context.getLayoutInflater().inflate(R.layout.list_item, parent,
					false);
			holder = new ViewHolder();
			holder.text = (TextView) view.findViewById(R.id.text);
			holder.image = (ImageView) view.findViewById(R.id.image);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		holder.text.setText("這是第 " + (position + 1) + " 項");

		imageLoader.displayImage(list_Image.IMAGES[position], holder.image,
				options, animateFirstListener);

		return view;
	}

	// 圖片顯示動畫
	public static class AnimateFirstDisplayListener extends
			SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections
				.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
				} else {
					imageView.setImageBitmap(loadedImage);
				}
				displayedImages.add(imageUri);
			}
		}
	}
}

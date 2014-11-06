package com.kenji.test_universalimageloader;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

@SuppressLint("NewApi")
public class MainActivity extends Activity {

	DisplayImageOptions options;
	ImageLoader imageLoader = ImageLoader.getInstance();
	URL_Image list_Image=new URL_Image();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // ImageLoader 初始設定
        options = new DisplayImageOptions.Builder()
        	.showStubImage(R.drawable.ic_stub)
        	.showImageForEmptyUri(R.drawable.ic_empty)
        	.showImageOnFail(R.drawable.ic_error)
			.cacheInMemory()
			.cacheOnDisc()
			.build();
        
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
        	.defaultDisplayImageOptions(options)
        	.build();
        imageLoader.init(config);
        
        // ListView 設定
        ListView listview = (ListView) findViewById(R.id.listView1);
        ItemAdapter itemList=new ItemAdapter(this,options,list_Image,imageLoader);
        listview.setAdapter(itemList);
        listview.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this, "這是第 " + (arg2+1) +  " 項！", Toast.LENGTH_SHORT).show();
			}
        	
        });
        
    }
    
    @Override
	public void onBackPressed() {
		imageLoader.stop();
		ItemAdapter.AnimateFirstDisplayListener.displayedImages.clear();
		super.onBackPressed();
	}
    
    // 設定 menu
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.item_clear_memory_cache:
				imageLoader.clearMemoryCache();
				return true;
			case R.id.item_clear_disc_cache:
				imageLoader.clearDiscCache();
				return true;
			default:
				return false;
		}
	}
       
}

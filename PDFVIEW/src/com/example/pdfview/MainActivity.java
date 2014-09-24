package com.example.pdfview;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.sf.andpdf.pdfviewer.PdfViewerActivity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	String[] pdflist;
	File[] imagelist;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		finish();
		openPdfIntent();
	}

	private void openPdfIntent() {

		AssetManager assetManager = getAssets();
		InputStream in = null;
		OutputStream out = null;
		String pdfName = "sample.pdf";
		File file = new File(getFilesDir(), pdfName);
		try {
			in = assetManager.open(pdfName);
			out = openFileOutput(file.getName(), Context.MODE_WORLD_READABLE);
			copyFile(in, out);
			in.close();
			in = null;
			out.flush();
			out.close();
			out = null;
		} catch (Exception e) {
			Log.e("tag", e.getMessage());
		}

		String path = getFilesDir() + "/" + pdfName;

		try {
			final Intent intent = new Intent(MainActivity.this, Second.class);
			intent.putExtra(PdfViewerActivity.EXTRA_PDFFILENAME, path);
			startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void copyFile(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[1024];
		int read;
		while ((read = in.read(buffer)) != -1) {
			out.write(buffer, 0, read);
		}
	}

	public void getFiles() {
		final AssetManager assetManager = getAssets();
		Uri path = null;
		try {
			// for assets folder add empty string
			String[] filelist = assetManager.list("");
			// for assets/subFolderInAssets add only subfolder name
			String[] filelistInSubfolder = assetManager.list("pdfFolder");
			if (filelist == null) {
				// dir does not exist or is not a directory
			} else {
				for (int i = 0; i < filelist.length; i++) {
					// Get filename of file or directory
					String filename = filelist[i];

					Log.d("aaa", "file name:" + filename);
				}
			}

			// if(filelistInSubfolder == null) ............

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
package com.melashkov.dailyselfie;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

public class StorageUtil {

	final String ALBUM_NAME = "Daily_Selfie";
	final String FILE_EXTENSION = ".jpg";
	
	static final String TAG = "Selfie - StorageUtil : ";
	
	
	public File getStorageDir()
	{
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), ALBUM_NAME);
        storageDir.mkdirs();
        return storageDir;
	}
	
	public File getThumbnailStorageDir()
	{
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "." + ALBUM_NAME+"_Thumbnail");
        storageDir.mkdirs();
        return storageDir;
	}
	
	public File createImageFile() throws IOException {
		String imageFileName = "Daily_Selfie_"
				+ new SimpleDateFormat("yyyyMMdd_HHmmss")
						.format( 
								new Date().getTime() 
								);
		File storageDir = getStorageDir();

		File photoFile = new File(storageDir.getAbsolutePath() + File.separator
				+ imageFileName + FILE_EXTENSION);

		return photoFile;
	}
	
	public String saveThumbnail(File image)
	{
		String path = getThumbnailStorageDir() + File.separator + image.getName();
		Bitmap fullSized = BitmapFactory.decodeFile( image.getAbsolutePath() );
		Float aspectRatio = ((float)fullSized.getHeight())/(float)fullSized.getWidth();
		Bitmap thumb = Bitmap.createScaledBitmap(
				fullSized,
				160, 
				(int)(160*aspectRatio), 
				false);
        storeBitmapToFile(thumb, path);
        return path;
	}
	
	
	/**
	 * TODO: Would be nice to check if file exist, and maybe try to re-create thumbnail, assuming best case scenario
	 * @param image
	 * @return
	 */
	public Bitmap loadThumbnail(File image)
	{
		String path = getThumbnailStorageDir() + File.separator + image.getName();
		return BitmapFactory.decodeFile( path );
	}
	
		
	public boolean storeBitmapToFile(Bitmap bitmap, String filePath) {

		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {

			try {

				BufferedOutputStream bos = new BufferedOutputStream(
						new FileOutputStream(filePath));
				bitmap.compress(CompressFormat.PNG, 100, bos);
				bos.flush();
				bos.close();
			} catch (FileNotFoundException e) {
				return false;
			} catch (IOException e) {
				return false;
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Function returns all images in the folder
	 * @return
	 */
	public File[] getAllImages(){
        File storageDir = getStorageDir();
        File[] files = storageDir.listFiles();
        return files;
	}
	
	/**
	 * Function deletes all images in the folder including thumbnails
	 * @param path
	 */
	public void deleteAllImages() {
		File[] files = getAllImages();
		
		for (File f : files) {
			deleteImage(f.getAbsolutePath());
        }
	}
	
	/**
	 * Function deletes single image and associated thumbnail
	 * @param path
	 */
	public void deleteImage(String path) {
		File file = new File( path );
		File thumb = new File( getThumbnailStorageDir()+ File.separator + file.getName());
		file.delete();
		thumb.delete();
	}
	
	
}

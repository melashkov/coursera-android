package com.melashkov.dailyselfie;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class ListViewAdapter extends BaseAdapter {
	  
	static final String TAG = "Selfie - GridViewAdapter : ";
	
	private final Context mContext;
	private List<Selfie> mItems = new ArrayList<Selfie>();
	private LayoutInflater mInflater;
	private StorageUtil mStorageUtil;
	    
	
	// Disclaimer, constractor is not a very good place to populate values,
	// need to thing where to move this logic
	public ListViewAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mStorageUtil = new StorageUtil();

        reloadContent();
    }
	
	public void reloadContent()
	{
		mItems.clear();
        File[] files = mStorageUtil.getAllImages();
        for (File f : files) {
        	mItems.add(new Selfie(f.lastModified(), f.getAbsolutePath()));
        }
        notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return mItems.size();
	}

	@Override
	public Object getItem(int position) {
		return mItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return mItems.get(position).getId();
	}

	public void add(Selfie selfie)
	{
		mItems.add(selfie);
	    notifyDataSetChanged();
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ImageView thumb;
        TextView name;

        if (view == null) {
        	view = mInflater.inflate(R.layout.grid_item, parent, false);
        	view.setTag(R.id.selfie_thumb, view.findViewById(R.id.selfie_thumb));
        	view.setTag(R.id.selfie_name , view.findViewById(R.id.selfie_name));
        }

        thumb = (ImageView) view.getTag(R.id.selfie_thumb);
        name  = (TextView)  view.getTag(R.id.selfie_name);

        final Selfie item = mItems.get(position);

        thumb.setImageBitmap( mStorageUtil.loadThumbnail( new File(item.getImagePath()) ) );
        name.setText(item.getName());
        
        view.setOnClickListener(new View.OnClickListener() {
            private String path = item.getImagePath();

            @Override
            public void onClick(View v) {
                ((MainActivity) mContext).showImage(path);
            }
        });
        
        view.setOnLongClickListener(new View.OnLongClickListener() {
            private Selfie selfie = item;

            @Override
            public boolean onLongClick(View v) {
                Log.d(TAG, "Deleting Selfie.");
                new AlertDialog.Builder(mContext)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(mContext.getString(R.string.dialog_delete_one_title))
                        .setMessage(mContext.getString(R.string.dialog_delete_one_message))
                        .setPositiveButton(mContext.getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new File(selfie.getImagePath()).delete();
                                mItems.remove(selfie);
                                ListViewAdapter.this.notifyDataSetChanged();
                            }

                        })
                        .setNegativeButton(mContext.getString(R.string.no), null)
                        .show();
                return true;
            }
        });
        
        return view;

    }

}

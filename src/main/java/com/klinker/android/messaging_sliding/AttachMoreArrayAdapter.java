package com.klinker.android.messaging_sliding;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.google.android.mms.MMSPart;
import com.klinker.android.messaging_donate.R;

import java.util.ArrayList;

public class AttachMoreArrayAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final MMSPart[] images;

    static class ViewHolder {
        public ImageView image;
    }

    public AttachMoreArrayAdapter(Activity context, MMSPart[] images) {
        super(context, R.layout.image_view, new ArrayList<String>());
        this.context = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.image_view, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.image = (ImageView) rowView.findViewById(R.id.contactBubble1);
            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();

        if (images[position].Name.startsWith("Image")) {
            Bitmap bmp;
            bmp = BitmapFactory.decodeByteArray(images[position].Data, 0, images[position].Data.length);
            Bitmap mutableBitmap = bmp.copy(Bitmap.Config.ARGB_8888, true);

            holder.image.setImageBitmap(mutableBitmap);
        } else if (images[position].Name.startsWith("Video")) {
            String path = getPath(images[position].Path);

            Bitmap thumb = ThumbnailUtils.createVideoThumbnail(path,
                    MediaStore.Images.Thumbnails.MINI_KIND);

            holder.image.setImageBitmap(thumb);
        } else if (images[position].Name.startsWith("Audio")) {
            String path = getPath(images[position].Path);

            Bitmap thumb = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.ic_video_play);

            holder.image.setImageBitmap(thumb);
        }

        return rowView;
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        String path;

        try {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            path = cursor.getString(column_index);
            cursor.close();
        } catch (Exception e) {
            path = uri.getPath();
        }

        return path;
    }
}
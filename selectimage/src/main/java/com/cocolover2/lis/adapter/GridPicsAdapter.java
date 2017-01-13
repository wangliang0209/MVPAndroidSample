package com.cocolover2.lis.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cocolover2.lis.AlbumHelper;
import com.cocolover2.lis.LISConstant;
import com.cocolover2.lis.R;
import com.cocolover2.lis.entity.ImageItem;
import com.cocolover2.lis.interf.OnDisplayImgLisener;
import com.cocolover2.lis.interf.OnSelectResultListener;

import java.io.File;
import java.util.ArrayList;

import static com.cocolover2.lis.LISConstant.ACTION_PRE_SUFFIX;


public class GridPicsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int REQUEST_PREVIEW = 1;
    public static final int REQUEST_TAKE_PIC = 2;
    private final String FILTER_COLOR = "#88000000";
    private final String INIT_FILTER = "#1a000000";
    private OnSelectResultListener mListener = null;
    private OnDisplayImgLisener displayImgLisener;
    private static boolean sIsPreView;//是否支持预览
    private FrameLayout.MarginLayoutParams lp = null;
    private Activity activity;
    private ArrayList<ImageItem> datas;
    private int[] iconResource;
    private boolean isShowTakePhoto = true;
    private int mColumnNum;
    private final float density;
    private String mPhotoPath;

    public GridPicsAdapter(Activity activity, ArrayList<ImageItem> datas, int columnNum, @NonNull int[] iconResource) {
        this.activity = activity;
        this.datas = datas;
        this.mColumnNum = columnNum;
        DisplayMetrics metrics = activity.getResources().getDisplayMetrics();
        final int size = metrics.widthPixels / mColumnNum;
        density = metrics.density;
        lp = new FrameLayout.MarginLayoutParams(size, size);
        lp.leftMargin = lp.rightMargin = lp.topMargin = lp.bottomMargin = (int) density;
        this.iconResource = iconResource;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            return new GridTakePhotoViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_grid_take_photo, parent, false));
        } else {
            return new GridPicViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_grid_pic, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        generateDivider(holder, position);
        if (holder.getItemViewType() == 1 && isShowTakePhoto) {
            final GridTakePhotoViewHolder vh = (GridTakePhotoViewHolder) holder;
            vh.icon.setImageResource(iconResource[1]);
            vh.itemView.setBackgroundResource(iconResource[3]);
            vh.text.setTextColor(iconResource[2]);
            vh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    takePhoto(REQUEST_TAKE_PIC);
                }
            });
        } else {
            if (isShowTakePhoto) {
                position = position - 1;
            }
            final ImageItem item = datas.get(position);
            final GridPicViewHolder vh = (GridPicViewHolder) holder;
            handleChooseFromAlbum(position, vh, item);
        }
    }

    private void generateDivider(RecyclerView.ViewHolder holder, int position) {
        if (position % mColumnNum == 0)
            lp.leftMargin = 0;
        else
            lp.leftMargin = (int) density;

        if (position % mColumnNum == (mColumnNum - 1)) {
            lp.rightMargin = 0;
        } else {
            lp.rightMargin = (int) density;
        }
        holder.itemView.setLayoutParams(lp);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && isShowTakePhoto) {
            return 1;
        } else {
            return 2;
        }
    }

    private void takePhoto(int requestCode) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(activity, "请开启相机权限", Toast.LENGTH_SHORT).show();
            return;
        }
        mPhotoPath = getCameraPath();
        if (TextUtils.isEmpty(mPhotoPath)) {
            Toast.makeText(activity, "未找到SD卡,无法进行拍照", Toast.LENGTH_SHORT).show();
        } else {
            mPhotoPath += File.separator + "IMG_" + System.currentTimeMillis() + ".jpg";
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mPhotoPath)));
            activity.startActivityForResult(intent, requestCode);
        }
    }

    private String getCameraPath() {
        final String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Camera";
        } else {
            return null;
        }
    }

    public String getPhotoPath() {
        return mPhotoPath;
    }


    private void handleChooseFromAlbum(int position, GridPicViewHolder vh, ImageItem item) {
        if (AlbumHelper.getMaxSize() <= 1) {
            vh.checkBox.setVisibility(View.INVISIBLE);
        } else {
            vh.checkBox.setVisibility(View.VISIBLE);

            vh.checkBox.setChecked(item.isSelected);
            if (item.isSelected) {
                vh.iv.setColorFilter(Color.parseColor(FILTER_COLOR));
            } else {
                vh.iv.setColorFilter(Color.parseColor(INIT_FILTER));
            }
        }
        if (displayImgLisener == null) {
            throw new NullPointerException("setOnDisplayImgListener is null");
        }
        displayImgLisener.displayImg(item.imagePath, vh.iv, lp.width, lp.height);
        setClickListener(vh, item, position);
    }


    @Override
    public int getItemCount() {
        if (isShowTakePhoto)
            return datas == null ? 1 : datas.size() + 1;
        else
            return datas == null ? 0 : datas.size();
    }

    private void setClickListener(final GridPicViewHolder holder, final ImageItem item, final int position) {
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSelectClicked(item, holder);
            }
        });
        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sIsPreView && AlbumHelper.getMaxSize() > 1) {
                    startPreviewImgs(position, datas, false);
                } else {
                    doSelectClicked(item, holder);
                }
            }
        });

    }

    /**
     * 调整到预览界面
     *
     * @param position  起始位置
     * @param _datas    选择图片的集合
     * @param isTakePic 是否是拍照操作
     */
    public void startPreviewImgs(int position, ArrayList<ImageItem> _datas, boolean isTakePic) {
        String packageName = activity.getPackageName();

        Intent intent = new Intent(packageName + ACTION_PRE_SUFFIX);
        if (isTakePic)
            intent.addCategory(packageName + LISConstant.CATEGORY_PRE_TAKE_PIC_SUFFIX);
        else
            intent.addCategory(packageName + LISConstant.CATEGORY_PRE_SUFFIX);

        intent.putExtra(LISConstant.KEY_PRE_IMG_START_POSITION, position);
        intent.putParcelableArrayListExtra(LISConstant.KEY_PRE_IMG_PATH_LIST, _datas);
        activity.startActivityForResult(intent, REQUEST_PREVIEW);
    }


    private void doSelectClicked(ImageItem item, GridPicViewHolder holder) {
        if (item.isSelected) {
            holder.checkBox.setChecked(false);
            AlbumHelper.removeItem(item);
            holder.iv.setColorFilter(Color.parseColor(INIT_FILTER));
            item.isSelected = false;
        } else {
            if (AlbumHelper.getHasSelectCount() >= AlbumHelper.getMaxSize()) {
                if (AlbumHelper.getMaxSize() > 0)
                    Toast.makeText(activity, "最多选择" + AlbumHelper.getMaxSize() + "张图片", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(activity, "当前你不能选择图片", Toast.LENGTH_SHORT).show();
            } else {
                holder.checkBox.setChecked(true);
                holder.iv.setColorFilter(Color.parseColor(FILTER_COLOR));
                AlbumHelper.addtoSelectImgs(item);
                item.isSelected = true;
            }
        }
        if (mListener != null)
            mListener.onSelectImgs(AlbumHelper.getHasSelectCount());
    }

    public static void setPreView(boolean isPreView) {
        sIsPreView = isPreView;
    }

    public void setOnDisplayImgListener(OnDisplayImgLisener listener) {
        displayImgLisener = listener;
    }

    public void refresh(boolean isShowTakePhoto) {
        AlbumHelper.initDataList(datas);
        this.isShowTakePhoto = isShowTakePhoto;
        notifyDataSetChanged();
    }

    public void setOnSelectPicListener(OnSelectResultListener listener) {
        this.mListener = listener;
    }

    private class GridPicViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        CheckBox checkBox;

        GridPicViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.item_grid_pic_img);
            checkBox = (CheckBox) itemView.findViewById(R.id.item_grid_pic_img_checkbox);
            checkBox.setButtonDrawable(iconResource[0]);
        }
    }

    private static class GridTakePhotoViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView text;

        GridTakePhotoViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.item_grid_take_photo_icon);
            text = (TextView) itemView.findViewById(R.id.item_grid_take_photo_text);
        }
    }
}

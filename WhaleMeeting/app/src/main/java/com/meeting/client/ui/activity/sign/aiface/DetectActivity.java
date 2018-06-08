/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.meeting.client.ui.activity.sign.aiface;


import android.Manifest;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.TextureView;
import android.view.WindowManager;

import com.baidu.aip.ImageFrame;
import com.baidu.aip.face.CameraImageSource;
import com.baidu.aip.face.DetectRegionProcessor;
import com.baidu.aip.face.FaceDetectManager;
import com.baidu.aip.face.FaceFilter;
import com.baidu.aip.face.PreviewView;
import com.baidu.aip.face.camera.ICameraControl;
import com.baidu.aip.face.camera.PermissionCallback;
import com.baidu.idl.facesdk.FaceInfo;
import com.meeting.client.R;
import com.meeting.client.comm.util.ImageSaveUtil;
import com.meeting.client.domain.sign.UserInfoDomain;
import com.meeting.client.ui.activity.sign.SignUserInfoActivity;
import com.meeting.client.ui.activity.sign.aiface.util.BrightnessTools;
import com.meeting.client.ui.base.BaseFragmentActivity;

import java.lang.ref.WeakReference;

/**
 * 实时检测人脸框并把检测到得人脸图片绘制在屏幕上，每10帧截图一张。
 * Intent intent = new Intent(MainActivity.this, DetectActivity.class);
 * startActivity(intent);
 */
public class DetectActivity extends BaseFragmentActivity {

    private static final int MSG_INITVIEW = 1001;
    private PreviewView previewView;
    private boolean mDetectStoped = false;

    private FaceDetectManager faceDetectManager;
    private DetectRegionProcessor cropProcessor = new DetectRegionProcessor();
    private int mScreenW;
    private int mScreenH;
    private TextureView mTextureView;

    private Paint paint = new Paint();

//    private List<Bitmap> mList = new ArrayList<>();

    private Handler mHandler = new Handler();
    private LinearLayoutManager mLayoutManager;
    private int mFrameIndex = 0;
    private int mRound = 2;


    @Override
    protected boolean intentData() {
        return true;
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_detect);
    }

    /**
     * 获取屏幕参数
     */
    private void initScreen() {
        WindowManager manager = getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        mScreenW = outMetrics.widthPixels;
        mScreenH = outMetrics.heightPixels;
        mRound = getResources().getDimensionPixelSize(R.dimen.round);
    }



    @SuppressLint("WrongViewCast")
    @Override
    protected void initUI() {


        setTitle("人脸签到");

        faceDetectManager = new FaceDetectManager(this);
        initScreen();

        previewView = (PreviewView) findViewById(R.id.preview_view);
        mTextureView = (TextureView) findViewById(R.id.texture_view);
        mTextureView.setOpaque(false);
        // 不需要屏幕自动变黑。
        mTextureView.setKeepScreenOn(false);

        final CameraImageSource cameraImageSource = new CameraImageSource(this);
        cameraImageSource.setPreviewView(previewView);

        faceDetectManager.setImageSource(cameraImageSource);
        faceDetectManager.setOnFaceDetectListener(new FaceDetectManager.OnFaceDetectListener() {
            @Override
            public void onDetectFace(final int retCode, FaceInfo[] infos, ImageFrame frame) {

            }
        });
        faceDetectManager.setOnTrackListener(new FaceFilter.OnTrackListener() {
            @Override
            public void onTrack(final FaceFilter.TrackedModel trackedModel) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showFrame(trackedModel);
                    }
                });

            }
        });

        cameraImageSource.getCameraControl().setPermissionCallback(new PermissionCallback() {
            @Override
            public boolean onRequestPermission() {
                ActivityCompat.requestPermissions(DetectActivity.this,
                        new String[]{Manifest.permission.CAMERA}, 100);
                return true;
            }
        });


        ICameraControl control = cameraImageSource.getCameraControl();
        control.setPreviewView(previewView);
        // 设置检测裁剪处理器
        faceDetectManager.addPreProcessor(cropProcessor);

//        int orientation = getResources().getConfiguration().orientation;
//        boolean isPortrait = (orientation == Configuration.ORIENTATION_PORTRAIT);
//        if (isPortrait) {
//            previewView.setScaleType(PreviewView.ScaleType.FIT_WIDTH);
//        } else {
//            previewView.setScaleType(PreviewView.ScaleType.FIT_HEIGHT);
//        }
        previewView.setScaleType(PreviewView.ScaleType.FIT_WIDTH);

        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        cameraImageSource.getCameraControl().setDisplayOrientation(rotation);
        setCameraType(cameraImageSource);
        initBrightness();
        initPaint();

        mHandler = new InnerHandler(this);
        mHandler.sendEmptyMessageDelayed(MSG_INITVIEW, 500);

    }

    @Override
    protected void setUI() {

    }

    @Override
    protected void loadInitData() {

    }

    /**
     * 设置相机亮度，不够200自动调整亮度到200
     */
    private void initBrightness() {
        int brightness = BrightnessTools.getScreenBrightness(DetectActivity.this);
        if (brightness < 200) {
            BrightnessTools.setBrightness(this, 200);
        }
    }

    /**
     * 启动人脸检测
     */
    private void start() {
        RectF newDetectedRect = new RectF(0, 0, mScreenW, mScreenH);
        cropProcessor.setDetectedRect(newDetectedRect);
        faceDetectManager.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopFaceDetect();


//        int size = mList.size();
//        for (int i = 0; i < size; i++) {
//            Bitmap bmp = mList.get(i);
//            if (bmp != null && !bmp.isRecycled()) {
//                bmp.recycle();
//            }
//        }
//        mList.clear();
    }

    private void stopFaceDetect() {
        faceDetectManager.stop();
        mDetectStoped = true;
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        restartFaceDetect();
        mFrameIndex=0;
//        mHandler.postDelayed(scrollRunnable, 10);

    }

    private void restartFaceDetect() {
        if (mDetectStoped) {
            faceDetectManager.start();
            mDetectStoped = false;
        }
    }

    private static class InnerHandler extends Handler {
        private WeakReference<DetectActivity> mWeakReference;

        public InnerHandler(DetectActivity activity) {
            super();
            this.mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (mWeakReference == null || mWeakReference.get() == null) {
                return;
            }
            DetectActivity activity = mWeakReference.get();
            if (activity == null) {
                return;
            }
            if (msg == null) {
                return;

            }
            switch (msg.what) {
                case MSG_INITVIEW:
                    activity.start();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        paint.setColor(Color.parseColor("#6290FF"));
        paint.setStyle(Paint.Style.STROKE);
    }

    /**
     * 绘制人脸框。
     *
     * @param model 追踪到的人脸
     */
    private void showFrame(FaceFilter.TrackedModel model) {
        Canvas canvas = mTextureView.lockCanvas();
        if (canvas == null) {
            return;
        }
        // 清空canvas
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        if (model != null) {
            FaceInfo info = model.getInfo();
            model.getImageFrame().retain();
            RectF rectCenter = new RectF(info.mCenter_x - 2 - info.mWidth * 3 / 5,
                    info.mCenter_y - 2 - info.mWidth * 3 / 5,
                    info.mCenter_x + 2 + info.mWidth * 3 / 5,
                    info.mCenter_y + 2 + info.mWidth * 3 / 5);
            previewView.mapFromOriginalRect(rectCenter);
            // 绘制框
            paint.setStrokeWidth(mRound);
            paint.setAntiAlias(true);
            canvas.drawRect(rectCenter, paint);

            if (model.meetCriteria()) {
                // 符合检测要求，绘制绿框
                paint.setColor(Color.parseColor("#6290FF"));
            }
            mFrameIndex++;
            Log.d("liujinhui", "add face index is:" + mFrameIndex);
            if (mFrameIndex >= 5) {
                final Bitmap face = model.cropFace();


                //  final Bitmap face =ImageUtil.bitmapFromArgb(model.getImageFrame());
                if (face != null) {
//                    int size = mList.size();
//                    // 释放一些，以防止太多
//                    if (size >= 6) {
//                        Bitmap bmp = mList.get(size - 6);
//                        if (bmp != null) {
//                            bmp.recycle();
//                            Log.d("liujinhui", "recycle size is:" + size);
//                            bmp = null;
//                        }
//                    }
//                    mList.add(face);
                    Log.d("liujinhui", "add face ok");
//                    mHandler.postDelayed(scrollRunnable, 100);
                    mFrameIndex = -99;
                    showToast("识别成功");
                    String headFile = ImageSaveUtil.saveCameraBitmap(ct,face, SystemClock.uptimeMillis()+"");
                    goUserInfo(headFile);

                }
            }
        }
        mTextureView.unlockCanvasAndPost(canvas);
    }

    private void goUserInfo(String headFile) {

        UserInfoDomain user = new UserInfoDomain();
        String headUrl = null;

        user.id = "1";
        user.company = "世纪阳天";
        user.signStatus = false;
        user.post = "CEO";
        user.name = "张三";
        user.phoneNum = "13000000000";
        user.head = "https://img3.doubanio.com/view/photo/s_ratio_poster/public/p480747492.webp";
        user.tempSignName = "春熙路C路口";

        SignUserInfoActivity.goActivity(ct,user,headFile);

    }


//    Runnable scrollRunnable = new Runnable() {
//        @Override
//        public void run() {
//            int count = mRecyAdapter.getItemCount();
//            int curIndex = count - 1;
//            mRecyclerview.scrollToPosition(curIndex);
//            mRecyAdapter.setDatas(mList);
//            mRecyclerview.invalidate();
//            //  Log.d("liujinhui", "in runnuable data size is:" + mList.size());
//        }
//    };

    /**
     * 初始化recycleView画截图得到的人脸图像
     */
    private void initRecy() {

//        mRecyAdapter = new RecyAdapter(this);
//
//        mLayoutManager = new LinearLayoutManager(DetectActivity.this,
//                LinearLayoutManager.HORIZONTAL, true);
//        mRecyclerview.setLayoutManager(mLayoutManager);
//        mLayoutManager.setStackFromEnd(true);
//        mRecyclerview.setAdapter(mRecyAdapter);
    }

    private void setCameraType(CameraImageSource cameraImageSource) {
        // TODO 选择使用前置摄像头
        cameraImageSource.getCameraControl().setCameraFacing(ICameraControl.CAMERA_FACING_FRONT);

        // TODO 选择使用usb摄像头
        //  cameraImageSource.getCameraControl().setCameraFacing(ICameraControl.CAMERA_USB);
        // 如果不设置，人脸框会镜像，显示不准
        //  previewView.getTextureView().setScaleX(-1);

        // TODO 选择使用后置摄像头
        // cameraImageSource.getCameraControl().setCameraFacing(ICameraControl.CAMERA_FACING_BACK);
        // previewView.getTextureView().setScaleX(-1);
    }
}

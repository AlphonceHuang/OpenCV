package com.example.opencv;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

public class CameraActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2{
    static final String TAG = "Alan";

    private static final int ACTIVITY_CAMERA = 1002;
    private static final int ACTIVITY_READ_STORAGE = 1003;
    private static final int ACTIVITY_WRITE_STORAGE = 1008;


    private CameraBridgeViewBase mOpenCvCameraView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // 全透明status bar及Navi bar
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION // navi bar
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN // status bar
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(option);
        getWindow().setNavigationBarColor(Color.TRANSPARENT);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            Log.w(TAG, "CameraActivity:Get CAMERA permission success.");
        }else{
            Log.w(TAG, "CameraActivity:Get CAMERA permission fail.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, ACTIVITY_CAMERA);
        }

        mOpenCvCameraView = findViewById(R.id.openCVCamera);
        mOpenCvCameraView.setCameraIndex(CameraBridgeViewBase.CAMERA_ID_BACK); // 使用後相機
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);

        mOpenCvCameraView.enableView();
    }

    public CameraActivity() {
        Log.i(TAG, "Instantiated new " + this.getClass());
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }
    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onCameraViewStarted(int width, int height) {

    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        Mat mRgba;
        mRgba = inputFrame.rgba(); // 彩色
        return mRgba;
    }
}

package com.example.opencv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;

public class MainActivity extends AppCompatActivity {

    static final String TAG = "Alan";
    private static final int ACTIVITY_CAMERA = 1002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv=findViewById(R.id.Text);

        if (OpenCVLoader.initDebug()) {
            Log.i(TAG, "load OpenCV Library Successful.");
            tv.setText(getString(R.string.load_success));
        } else {
            Log.i(TAG, "load OpenCV Library Failed.");
            tv.setText(getString(R.string.load_fail));
        }

        Button btn=findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 取得 Camera 權限
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                    Log.w(TAG, "MainActivity:Get CAMERA permission success.");

                    Intent myIntent = new Intent(MainActivity.this, CameraActivity.class);
                    startActivity(myIntent);
                }else{
                    Log.w(TAG, "MainActivity:Get CAMERA permission fail.");
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, ACTIVITY_CAMERA);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == ACTIVITY_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){

                Intent intent4 = new Intent(MainActivity.this, CameraActivity.class);
                startActivity(intent4);
            }else{
                Toast.makeText(getApplicationContext(), "camera permission denied.", Toast.LENGTH_LONG).show();
            }
        }
    }

    protected void onResume() {
        if (!OpenCVLoader.initDebug()) {
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_4_0, this, mLoaderCallback);
        } else {
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
        super.onResume();
    }

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(TAG, "OpenCV loaded successfully");
                }
                break;

                default:
                {
                    Log.i(TAG, "OpenCV loaded fail.");
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };
}

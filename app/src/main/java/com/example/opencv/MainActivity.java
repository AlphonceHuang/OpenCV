package com.example.opencv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;

public class MainActivity extends AppCompatActivity {

    static final String TAG = "Alan";
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
                //Log.w(TAG, "onClick button.");
                Intent myIntent = new Intent(MainActivity.this,CameraActivity.class);
                startActivity(myIntent);
            }
        });
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

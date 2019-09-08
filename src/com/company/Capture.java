package com.company;


import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;
import org.bytedeco.opencv.opencv_videoio.VideoCapture;

public class Capture {

    private Capture.DaemonThread myThread = null;

    VideoCapture webSource = null;
    Mat cameraImage = new Mat();
    CascadeClassifier cascadeClassifier = new CascadeClassifier("");


}

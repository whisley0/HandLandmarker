package com.anonymous.test.handlandmarksframeprocessor

import com.google.mediapipe.framework.image.BitmapImageBuilder
import com.google.mediapipe.framework.image.MPImage

import android.util.Log
import com.mrousavy.camera.frameprocessors.Frame
import com.mrousavy.camera.frameprocessors.FrameProcessorPlugin
import com.mrousavy.camera.frameprocessors.VisionCameraProxy

import com.myproject.HandLandmarkerHolder

class HandLandmarksFrameProcessorPlugin(proxy: VisionCameraProxy, options: Map<String, Any>?): FrameProcessorPlugin() {
   
  override fun callback(frame: Frame, arguments: Map<String, Any>?): Any {
        if (HandLandmarkerHolder.handLandmarker == null) {
            return "HandLandmarker is not initialized" // Return early if initialization failed
        }

        print("get into callback.")

        try {
            // Convert the frame to an MPImage
            val mpImage: MPImage = BitmapImageBuilder(frame.imageProxy.toBitmap()).build()

            // Get the timestamp from the frame
            val timestamp = frame.timestamp ?: System.currentTimeMillis()

            print("timestamp xoxox")


            // Call detectAsync with MPImage and timestamp
            HandLandmarkerHolder.handLandmarker?.detectAsync(mpImage, timestamp)

            return "Frame processed successfully"
        } catch (e: Exception) {
        e.printStackTrace()
        Log.e("HandLandmarksFrameProcessor", "Error processing frame: ${e.message}")
        return "Error processing frame: ${e.message}"
        }
  }
}
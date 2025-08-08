import { useEffect } from 'react';
import { Linking, Platform, Text } from 'react-native';
import {
  Camera,
  useCameraDevice,
  useCameraPermission,
  useSkiaFrameProcessor,
} from 'react-native-vision-camera';

export default function MyCameraScreen() {
  const device = useCameraDevice('back');
  const { hasPermission, requestPermission } = useCameraPermission();

  useEffect(() => {
    if (!hasPermission) {
        requestPermission().then((granted) => {
        if (!granted) {
          // User denied permission, guide them to settings
          Linking.openSettings();
        }
      });
    }
  }, [hasPermission, requestPermission]);

  if (!device) {
    return <Text>No camera device found.</Text>;
  }

  const frameProcessor = useSkiaFrameProcessor(frame => {
    'worklet';
    frame.render();
  }, []);

  const pixelFormat = Platform.OS === 'ios' ? 'rgb' : 'yuv';

  return (
    <Camera
      device={device}
      isActive={true}
      style={{ flex: 1 }}
      frameProcessor={frameProcessor}
      pixelFormat={pixelFormat}
    />
  );
}


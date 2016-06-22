package urils.ecaray.com.ecarutils.Util2;

import android.graphics.ImageFormat;
import android.graphics.YuvImage;
import android.hardware.Camera;

public class YUV420spRotate {
    public static YuvImage rotateNV21(byte[] input, byte[] output, int width,
                                      int height, int rotation) {
        boolean swap = (rotation == 90 || rotation == 270);
        boolean yflip = (rotation == 90 || rotation == 180);
        boolean xflip = (rotation == 270 || rotation == 180);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int xo = x, yo = y;
                int w = width, h = height;
                int xi = xo, yi = yo;
                if (swap) {
                    xi = w * yo / h;
                    yi = h * xo / w;
                }
                if (yflip) {
                    yi = h - yi - 1;
                }
                if (xflip) {
                    xi = w - xi - 1;
                }
                output[w * yo + xo] = input[w * yi + xi];
                int fs = w * h;
                int qs = (fs >> 2);
                xi = (xi >> 1);
                yi = (yi >> 1);
                xo = (xo >> 1);
                yo = (yo >> 1);
                w = (w >> 1);
                h = (h >> 1);
                // adjust for interleave here
                int ui = fs + (w * yi + xi) * 2;
                int uo = fs + (w * yo + xo) * 2;
                // and here
                int vi = ui + 1;
                int vo = uo + 1;
                output[uo] = input[ui];
                output[vo] = input[vi];
            }
        }
        YuvImage image = new YuvImage(input, ImageFormat.NV21, width, height,
                null);
        return image;
    }

    public static void YUV420spRotate90(byte[] dst, byte[] src, int srcWidth,
                                        int srcHeight) {
        int nWidth = 0, nHeight = 0;
        int wh = 0;
        int uvHeight = 0;

        if (srcWidth != nWidth || srcHeight != nHeight) {
            nWidth = srcWidth;
            nHeight = srcHeight;
            wh = srcWidth * srcHeight;
            uvHeight = srcHeight >> 1;// uvHeight = height / 2
        }

        // 旋转Y
        int k = 0;
        for (int i = 0; i < srcWidth; i++) {
            int nPos = 0;
            for (int j = 0; j < srcHeight; j++) {
                dst[k] = src[nPos + i];
                k++;
                nPos += srcWidth;
            }
        }

        for (int i = 0; i < srcWidth; i += 2) {
            int nPos = wh;
            for (int j = 0; j < uvHeight; j++) {
                dst[k] = src[nPos + i];
                dst[k + 1] = src[nPos + i + 1];
                k += 2;
                nPos += srcWidth;
            }
        }
        return;

    }

    public static YuvImage YUV420spRotateNegative90(byte[] dst, byte[] src,
                                                    int srcWidth, int srcHeight, Camera camera) {
        int nWidth = 0, nHeight = 0;
        int wh = 0;
        int uvHeight = 0;

        if (srcWidth != nWidth || srcHeight != nHeight) {
            nWidth = srcWidth;
            nHeight = srcHeight;
            wh = srcWidth * srcHeight;
            uvHeight = srcHeight >> 1;// uvHeight = height / 2
        }

        // 旋转Y
        int k = 0;
        for (int i = 0; i < srcWidth; i++) {
            int nPos = srcWidth * srcHeight;
            for (int j = srcHeight - 1; j >= 0; j--) {
                nPos -= srcWidth;
                dst[k] = src[nPos + i];
                k++;

            }
        }

        for (int i = 0; i < srcWidth; i += 2) {
            int nPos = wh + srcWidth * uvHeight;
            for (int j = uvHeight - 1; j > 0; j--) {
                nPos -= srcWidth;
                dst[k] = src[nPos + i];
                dst[k + 1] = src[nPos + i + 1];
                k += 2;
            }
        }

        YuvImage image = new YuvImage(dst, ImageFormat.NV21, srcWidth,
                srcHeight, null);
        return image;

    }

    public static void YUV420spRotate180(byte[] dst, byte[] src, int srcWidth,
                                         int srcHeight) {
        int nWidth = 0, nHeight = 0;
        int wh = 0;
        int uvHeight = 0;

        if (srcWidth != nWidth || srcHeight != nHeight) {
            nWidth = srcWidth;
            nHeight = srcHeight;
            wh = srcWidth * srcHeight;
            uvHeight = srcHeight >> 1;// uvHeight = height / 2
        }

        // 旋转Y
        int k = 0;
        for (int i = 0; i < srcWidth; i++) {
            int nPos = 0;
            for (int j = 0; j < srcHeight; j++) {
                dst[k] = src[nPos + i];
                k++;
                nPos += srcWidth;
            }
        }

        for (int i = 0; i < srcWidth; i += 2) {
            int nPos = wh;
            for (int j = 0; j < uvHeight; j++) {
                dst[k] = src[nPos + i];
                dst[k + 1] = src[nPos + i + 1];
                k += 2;
                nPos += srcWidth;
            }
        }
        return;
    }

    public static void YUV420spRotate270(byte[] dst, byte[] src, int srcWidth,
                                         int srcHeight) {
        int nWidth = 0, nHeight = 0;
        int wh = 0;
        int uvHeight = 0;
        if (srcWidth != nWidth || srcHeight != nHeight) {
            nWidth = srcWidth;
            nHeight = srcHeight;
            wh = srcWidth * srcHeight;
            uvHeight = srcHeight >> 1;// uvHeight = height / 2
        }

        // 旋转Y
        int k = 0;
        for (int i = 0; i < srcWidth; i++) {
            int nPos = srcWidth - 1;
            for (int j = 0; j < srcHeight; j++) {
                dst[k] = src[nPos - i];
                k++;
                nPos += srcWidth;
            }
        }

        for (int i = 0; i < srcWidth; i += 2) {
            int nPos = wh + srcWidth - 1;
            for (int j = 0; j < uvHeight; j++) {
                dst[k] = src[nPos - i - 1];
                dst[k + 1] = src[nPos - i];
                k += 2;
                nPos += srcWidth;
            }
        }

        return;
    }

    // static void yuv_flip_horizontal(BYTE *des,BYTE *src,int width,int
    // height);

    public static void yuv420sp_flip_vertical(byte[] des, byte[] src,
                                              int width, int height) {
        int wh = width * height;

        int k = 0;
        for (int i = 0; i < height / 2; i++) {
            for (int j = 0; j < width; j++) {
                des[i * width + j] = src[(height - 1 - i) * width + j];
                des[(height - 1 - i) * width + j] = src[i * width + j];
                k += 2;
            }
        }

        int uvHeight = height / 2;
        int uvWidth = width / 2;
        for (int i = 0; i < uvHeight / 2; i++) {
            for (int j = 0; j < width; j += 2) {
                des[wh + (i * width + j)] = src[wh + (uvHeight - 1 - i) * width
                        + j];
                des[wh + ((uvHeight - 1 - i) * uvWidth + j)] = src[wh + i
                        * width + j];

                des[wh + (i * width + j) + 1] = src[wh + (uvHeight - 1 - i)
                        * width + j + 1];
                des[wh + ((uvHeight - 1 - i) * width + j) + 1] = src[wh + i
                        * width + j + 1];
            }
        }
    }
}

package source;

import java.util.Arrays;

// Bitmap that serves as a render target for the software renderer.
public class RenderTarget {
    private final int mWidth;
    private final int mHeight;

    private final byte[] mPixels;

    public RenderTarget(int width, int height) {
        mWidth = width;
        mHeight = height;
        mPixels = new byte[width * height * 4];
    }

    public void clear(byte shade) {
        Arrays.fill(mPixels, shade);
    }

    public void drawPixel(int x, int y, byte r, byte g, byte b) {
        int index = (x + (y * mWidth)) * 3;
        mPixels[index     ] = r;
        mPixels[index + 1 ] = g;
        mPixels[index + 2 ] = b;
    }

    public void toIntArray(int[] dest) {
        for(int i = 0; i < mWidth * mHeight; i++) {
            int r = mPixels[i * 3    ] << 16;
            int g = mPixels[i * 3 + 1] << 8;
            int b = mPixels[i * 3 + 2] << 0;

            dest[i] = r | g | b;
        }
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }
}

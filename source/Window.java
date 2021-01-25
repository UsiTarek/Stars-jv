package source;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.image.BufferStrategy;
import java.awt.image.DataBufferInt;

public class Window extends Canvas {
    private final JFrame mFrame;
    private final BufferedImage mBufferedImage;
    private final RenderTarget mFramebuffer;
    private final int[] mBufferedPixels;
    private final BufferStrategy mBufferStrategy;
    private final Graphics mDrawGraphics;

    public Window(int width, int height, String title) {

        //Window dimensions
        Dimension size = new Dimension(width, height);
        setPreferredSize(size);
        setMaximumSize(size);

        // Initialize rendering buffers
        mFramebuffer = new RenderTarget(width, height);
        mBufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        mBufferedPixels = ((DataBufferInt)(mBufferedImage.getRaster().getDataBuffer())).getData();
        createBufferStrategy(1);
        mBufferStrategy = getBufferStrategy();

        // Initialize Java frame
        mFrame = new JFrame();
        mFrame.add(this);
        mFrame.pack();
        mFrame.setResizable(false);
        mFrame.setTitle(title);
        mFrame.setLocationRelativeTo(null);
        mFrame.setVisible(true);
        mFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        // Prepare draw graphics for current java frame
        mDrawGraphics = mBufferStrategy.getDrawGraphics();
    }

    public void setTitle(String title)
    {
        mFrame.setTitle(title);
    }

    public String getTitle()
    {
        return mFrame.getTitle();
    }

    // Swap current display buffer with the latest framebuffer
    public void swapBuffers()
    {
        mFramebuffer.toIntArray(mBufferedPixels);
        mDrawGraphics.drawImage(mBufferedImage, 0, 0, mFramebuffer.getWidth(), mFramebuffer.getHeight(), null);
        mBufferStrategy.show();
    }

    public RenderTarget getFramebuffer() {
        return mFramebuffer;
    }

}

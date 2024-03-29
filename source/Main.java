package source;

public class Main {
    public static void main(String[] args) {
        Window display = new Window(800, 600, "Star System");
        
        // Target bitmap to render to
        RenderTarget target = display.getFramebuffer();

        Stars3D stars = new Stars3D(
            2048,   // Star number 
            64.f,  // Spread
            20.f, // Speed
            90.f // Field of view angle
        );

        int frameCount = 0;
        long prevTime = System.currentTimeMillis();
        while(true) {
            long currentTime = System.currentTimeMillis();
            float deltaSeconds = (float)((currentTime - prevTime)/1000.0);

            stars.updateAndRender(target, deltaSeconds);

            // Display pixels on screen
            display.swapBuffers();

            // Show FPS
            if(frameCount % 250 == 0) {
                display.setTitle(String.format("%f", 1/deltaSeconds));
            }

            frameCount+= 1;
            prevTime = currentTime;
        }
    }
}

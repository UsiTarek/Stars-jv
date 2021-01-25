package source;

public class Stars3D {
    private final float mSpread;
    private final float mSpeed;

    private final float[] mPosX;
    private final float[] mPosY;
    private final float[] mPosZ;

    private final float mFov;

    // Initialize stars
    public Stars3D(int numStars, float spread, float speed, float fov) {
        mSpread = spread;
        mSpeed = speed;
        mFov = fov;

        mPosX = new float[numStars];
        mPosY = new float[numStars];
        mPosZ = new float[numStars];

        for(int i = 0; i < mPosX.length; i++){
            spawnStar(i);
        }
    }

    // Spawn star in a random position
    private void spawnStar(int index) {
        mPosX[index] = 2 * ((float)Math.random() - 0.5f    ) * mSpread;
        mPosY[index] = 2 * ((float)Math.random() - 0.5f    ) * mSpread;
        mPosZ[index] = 2 * ((float)Math.random() + 0.00001f) * mSpread;
    }

    // Update the position of stars and render them.
    public void updateAndRender(RenderTarget target, float deltaSeconds) {
        target.clear((byte)0x00);

        final float tanHalfFOV = (float)Math.tan(Math.toRadians(mFov/2.f)); 
        final float hWidth = target.getWidth()/2.f;
        final float hHeight = target.getHeight()/2.f;

        for(int i = 0; i < mPosX.length; i++){
            // Move star towards view
            mPosZ[i] -= deltaSeconds * mSpeed;

            // Star position
            final float posX = mPosX[i];
            final float posY = mPosY[i];
            final float posZ = mPosZ[i];

            // Respawn a star if it's behind the camera
            if(mPosZ[i] <= 0) {
                spawnStar(i);
            }

            // Switching star coords to screenspace from -1..1 to 0..ScreenSize
            int x = (int)((posX/(posZ * tanHalfFOV)) * hWidth  + hWidth );
            int y = (int)((posY/(posZ * tanHalfFOV)) * hHeight + hHeight);
            
            // Draw star
            if(x >= target.getWidth()  || x < 0 ||
               y >= target.getHeight() || y < 0) {
                   // Respawn a star in a random position if it's offscreen
                   spawnStar(i);
            }else {
                target.drawPixel(x, y, (byte)0xFF, (byte)0xFF, (byte)0xFF);
            }
        }
    }
}

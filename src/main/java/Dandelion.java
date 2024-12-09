import parameters.Parameters;
import processing.core.PApplet;
import processing.core.PVector;

import static parameters.Parameters.*;
import static save.SaveUtil.saveSketch;

public class Dandelion extends PApplet {
    public static void main(String[] args) {
        PApplet.main(Dandelion.class);
    }

    @Override
    public void settings() {
        size(WIDTH, HEIGHT);
        randomSeed(SEED);
        noiseSeed(floor(random(MAX_INT)));
    }

    @Override
    public void setup() {
        background(BACKGROUND_COLOR.red(), BACKGROUND_COLOR.green(), BACKGROUND_COLOR.blue());
        float red = random(RED_LOW, RED_HIGH);
        float green = random(GREEN_LOW, GREEN_HIGH);
        float blue = COLOR_TOTAL - red - green;
        stroke(red, green, blue, Parameters.ALPHA);
        noFill();
        noLoop();
    }

    @Override
    public void draw() {
        for (int k = 0; k < NUMBER_OF_DANDELIONS; k++) {
            float x, y;
            do {
                x = ORIGIN.x + HORIZONTAL_VARIANCE * randomGaussian();
                y = ORIGIN.y + VERTICAL_VARIANCE * randomGaussian();
            } while (x < 0 || x > width || y < 0 || y > height);
            int cellSize = CELL_SIZE_CONSTANT + CELL_SIZE_FACTOR * floor(dist(x, y, ORIGIN.x, ORIGIN.y) / CELL_SIZE_DIVISOR);
            PVector pVector = new PVector(cellSize * floor(x / cellSize), cellSize * floor(y / cellSize));
            float size = ceil(SHAPE_SIZE_FACTOR * cellSize
                    * noise(SHAPE_SIZE_NOISE_FACTOR * floor(pVector.x / SHAPE_SIZE_NOISE_DIVISOR),
                    SHAPE_SIZE_NOISE_FACTOR * floor(pVector.y / SHAPE_SIZE_NOISE_DIVISOR)));
            pushMatrix();
            translate(pVector.x, pVector.y);
            rotate(noise(ROTATION_NOISE_FACTOR * floor(dist(pVector.x, pVector.y, ORIGIN.x, ORIGIN.y) / cellSize),
                    PVector.sub(pVector, ORIGIN).heading()) * TWO_PI);
            drawShape(size);
            popMatrix();
        }
        saveSketch(this);
    }

    private void drawShape(float size) {
        float range = HALF_PI + RANGE_VARIANCE * randomGaussian();
        for (int i = 0; i < SIZE_FACTOR * size; i++) {
            float angle = floor(ANGLE_FACTOR * random(range)) / ANGLE_FACTOR;
            float length = random(0, LENGTH_FACTOR * size);
            line(-size, -size, -size + length * cos(angle), -size + length * sin(angle));
        }
    }
}

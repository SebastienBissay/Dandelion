package parameters;

import processing.core.PVector;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static processing.core.PConstants.PI;

public final class Parameters {
    public static final long SEED = 11021985;
    public static final int WIDTH = 2000;
    public static final int HEIGHT = 2000;
    public static final PVector ORIGIN = new PVector(WIDTH / 2f, HEIGHT / 2f);
    public static final int NUMBER_OF_DANDELIONS = 100000;
    public static final float HORIZONTAL_VARIANCE = WIDTH / 8f;
    public static final float VERTICAL_VARIANCE = HEIGHT / 8f;
    public static final int CELL_SIZE_CONSTANT = 20;
    public static final int CELL_SIZE_FACTOR = 2;
    public static final float CELL_SIZE_DIVISOR = 50f;
    public static final float SHAPE_SIZE_FACTOR = 1.5f;
    public static final float SHAPE_SIZE_NOISE_FACTOR = 10000f;
    public static final float SHAPE_SIZE_NOISE_DIVISOR = 50f;
    public static final float ROTATION_NOISE_FACTOR = 10f;
    public static final float RANGE_VARIANCE = PI / 16f;
    public static final float SIZE_FACTOR = .5f;
    public static final float ANGLE_FACTOR = 45f;
    public static final float LENGTH_FACTOR = 2f;
    public static final Color BACKGROUND_COLOR = new Color(255);
    public static final float RED_LOW = 0;
    public static final float RED_HIGH = 15;
    public static final float GREEN_LOW = 0;
    public static final float GREEN_HIGH = 15;
    public static final float COLOR_TOTAL = 30;
    public static final float ALPHA = 10;

    /**
     * Helper method to extract the constants in order to save them to a json file
     *
     * @return a Map of the constants (name -> value)
     */
    public static Map<String, ?> toJsonMap() throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();

        Field[] declaredFields = Parameters.class.getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(Parameters.class));
        }

        return Collections.singletonMap(Parameters.class.getSimpleName(), map);
    }

    public record Color(float red, float green, float blue, float alpha) {
        public Color(float red, float green, float blue) {
            this(red, green, blue, 255);
        }

        public Color(float grayscale, float alpha) {
            this(grayscale, grayscale, grayscale, alpha);
        }

        public Color(float grayscale) {
            this(grayscale, 255);
        }

        public Color(String hexCode) {
            this(decode(hexCode));
        }

        public Color(Color color) {
            this(color.red, color.green, color.blue, color.alpha);
        }

        public static Color decode(String hexCode) {
            return switch (hexCode.length()) {
                case 2 -> new Color(Integer.valueOf(hexCode, 16));
                case 4 -> new Color(Integer.valueOf(hexCode.substring(0, 2), 16),
                        Integer.valueOf(hexCode.substring(2, 4), 16));
                case 6 -> new Color(Integer.valueOf(hexCode.substring(0, 2), 16),
                        Integer.valueOf(hexCode.substring(2, 4), 16),
                        Integer.valueOf(hexCode.substring(4, 6), 16));
                case 8 -> new Color(Integer.valueOf(hexCode.substring(0, 2), 16),
                        Integer.valueOf(hexCode.substring(2, 4), 16),
                        Integer.valueOf(hexCode.substring(4, 6), 16),
                        Integer.valueOf(hexCode.substring(6, 8), 16));
                default -> throw new IllegalArgumentException();
            };
        }
    }
}

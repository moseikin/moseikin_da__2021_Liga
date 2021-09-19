package com.bad.code2.volumetric;

public class Qube implements VolumetricShape {
    private final Double side;

    public Qube(Double side) {
        this.side = side;
    }

    @Override
    public Double getVolume() {
        return Math.pow(side, 3);
    }

    /*
    Общий случай для вычисления площади объемной прямоугольной фигуры:

        public class RectParallelepiped implements VolumetricShape {
            private final Double side1;
            private final Double side2;
            private final Double side3;

        public RectParallelepiped(Double side1, Double side2, Double side3) {
            this.side1 = side1;
            this.side2 = side2;
            this.side3 = side3;
        }

        @Override
        public Double getVolume() {
            return side1 * side2 * side3 ;
        }
    }

     */
}

package com.bad.code2.flat;

public class Square implements FlatShape {
    private final Double side;

    public Square(Double side) {
        this.side = side;
    }

    @Override
    public Double getSquare() {
        return Math.pow(side, 2);
    }

    /*
        public class Rectangle implements FlatShape {
            private final Double side1;
            private final Double side2;

            public Rectangle(Double side1, Double side2) {
                this.side1 = side1;
                this.side2 = side2;
            }

            @Override
            public Double getSquare() {
                return side1 * side2;
            }
        }

     */


}

package com.bad.code2;

import com.bad.code2.flat.Square;
import com.bad.code2.volumetric.Qube;

public class BadCode2 {
    public static void main(String... args) {

        Qube qube = new Qube(10d);
        System.out.println("Qube volume: " + qube.getVolume());

        Square square = new Square(5d);
        System.out.println("Square perimeter: " + square.getSquare());

        /*
        можно сделать код более универсальным:
        вместо квадратов и кубов вычислять площадь прямоугольника и объем прамоугольного паралелипипида

        RectParallelepiped rect3D =
            new RectParallelepiped(Double side1, Double side2, Double side3);
        Rectangle rect = new Rectangle(Double side1, Double side2);

        */

    }

}

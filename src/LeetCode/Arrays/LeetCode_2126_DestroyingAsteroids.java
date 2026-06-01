package LeetCode.Arrays;

/*
You are given an integer mass, which represents the original mass of a planet.
You are further given an integer array asteroids, where asteroids[i] is the mass of the ith asteroid.

You can arrange for the planet to collide with the asteroids in any arbitrary order.
If the mass of the planet is greater than or equal to the mass of the asteroid,
the asteroid is destroyed and the planet gains the mass of the asteroid. Otherwise, the planet is destroyed.

Return true if all asteroids can be destroyed. Otherwise, return false.
*/

import java.util.Arrays;

public class LeetCode_2126_DestroyingAsteroids {
    public static void main(String[] args) {
        int mass = 10;
        int[] asteroids = {3,9,19,5,21};
        System.out.println(asteroidsDestroyed(mass , asteroids));
    }
    static boolean asteroidsDestroyed(int mass, int[] asteroids){

        if (asteroids == null || asteroids.length == 0) return true;

        Arrays.sort(asteroids);              // try smallest first
        long curMass = mass;                 // use long to avoid overflow when summing

        for (int a : asteroids) {
            if (curMass < a) return false;   // can't destroy this asteroid
            curMass += a;                    // absorb it
        }
        return true;
    }
}

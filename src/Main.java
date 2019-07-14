import java.util.Random;
import java.util.Scanner;

public class Main {
        public static void main(String[] args) {

            char[][] ocean = new char[10][10];

            cleanOcean(ocean);
            drawOcean(ocean);


            playerDeploy(ocean);
            computerDeploy(ocean);
            drawOcean(ocean);

        }

        public static void cleanOcean(char[][] ocean) {
            System.out.println("Cleaning the battlefield...");
            for (int i = 0; i < ocean.length; i++) {
                for (int j = 0; j < ocean[i].length; j++) {
                    ocean[i][j] = ' ';
                }
            }
            System.out.println("Battlefield ready!");
        }

        public static void drawOcean(char[][] ocean) {
            System.out.println("  0123456789  ");
            for (int i = 0; i < ocean.length; i++) {
                System.out.print(i + "|");
                for (int j = 0; j < ocean[i].length; j++) {
                    System.out.print(ocean[i][j]);
                }
                System.out.println("|" + i);
            }
            System.out.println("  0123456789  ");
        }

        public static void playerDeploy(char[][] ocean) {
            Scanner sc = new Scanner(System.in);
            int x = 0, y = 0;
            boolean taken = false;
            boolean outOfBounds = false;

            System.out.println("Deploy your battleships!");

            for (int i = 0; i < 5; i++) {
                do {
                    do {
                        System.out.print("x[" + (i + 1) + "]: ");
                        x = sc.nextInt();
                        outOfBounds = validateCoordinates(x);
                    } while (outOfBounds);
                    do {
                        System.out.print("y[" + (i + 1) + "]: ");
                        y = sc.nextInt();
                        outOfBounds = validateCoordinates(y);
                    } while (outOfBounds);

                    if (ocean[y][x] != ' ') {
                        System.out.println("Already taken!");
                        taken = true;
                    } else {
                        ocean[y][x] = '1';
                        taken = false;
                    }
                } while (taken);
            }
        }

        public static boolean validateCoordinates(int coordinate) {
            if (coordinate > 9 || coordinate < 0) {
                System.out.println("Out of bounds!");
                return true;
            } else {
                return false;
            }
        }

        public static void computerDeploy(char[][] ocean) {
            int x = 0, y = 0;
            boolean taken = false;
            boolean outOfBounds = false;
            Random rand = new Random();

            System.out.println("System is deploying ships.");

            for(int i = 0; i<5; i++) {
                do {
                    x = rand.nextInt(10);
                    y = rand.nextInt(10);
                    if(ocean[y][x] != ' '){
                        //taken
                        taken = true;
                    }else{
                        //not taken
                        ocean[y][x] = '2';
                        taken = false;
                    }
                }while(taken);
                System.out.println(i+1 + ". ship DEPLOYED");
            }
        }

        public static void attack(char[][] ocean, int player) {

        }
    }


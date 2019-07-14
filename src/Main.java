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

            battle(ocean);

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
                    if(ocean[i][j] == '1'){
                        System.out.print('@');
                    }else{
                        if (ocean[i][j] == '2'){
                            System.out.print(' ');
                        }else{
                            System.out.print(ocean[i][j]);
                        }
                    }
                }
                System.out.println("|" + i);
            }
            System.out.println("  0123456789  ");
        }

        public static void drawScore(int playerShips, int computerShips){
            System.out.println("Your ships: " + playerShips + " | Computer ships: " + computerShips);
            System.out.println("-------------------------------------------------");
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

            System.out.println("Computer is deploying ships.");

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

        public static void battle(char[][] ocean){
            int playerShips = 5;
            int computerShips = 5;
            boolean player = true; //true -> player / false -> computer
            int attackResult = 0;
            int guess = -1;
            int playerGuess = -1;
            Scanner sc = new Scanner(System.in);
            Random rand = new Random();

            //guess to shoot first
            guess = rand.nextInt(2);
            System.out.print("Guess the number to shoot first (0, 1): ");
            playerGuess = sc.nextInt();
            if(guess == playerGuess){
                player = true;
                System.out.println("You go first");
            }else{
                player = false;
                System.out.println("Computer goes first");
            }

            do {
                if (player){
                    System.out.println("YOUR TURN");
                    attackResult = attack(ocean, player);
                    switch (attackResult){
                        case 1:
                            computerShips--;
                            break;
                        case 2:
                            playerShips--;
                            break;
                    }
                    drawScore(playerShips, computerShips);
                }else{
                    System.out.println("COMPUTER'S TURN");
                    attackResult = attack(ocean, player);
                    switch (attackResult){
                        case 1:
                            computerShips--;
                            break;
                        case 2:
                            playerShips--;
                            break;
                    }
                    drawScore(playerShips, computerShips);
                }
                drawOcean(ocean);
                player = !player;
            }while(playerShips != 0 && computerShips != 0);

            if(playerShips == 0){
                System.out.println("Hooray! You win the battle :)");
            }else{
                System.out.println("Sorry! You lose");
            }
        }

        public static int attack(char[][] ocean, boolean player) {
            Scanner sc = new Scanner(System.in);
            Random rand = new Random();
            boolean outOfBounds = false;
            boolean alreadyTried = false;
            int attackResult = -1;
            int x = 0, y = 0;


            if (player){
                do {
                    do {
                        System.out.print("Enter X: ");
                        x = sc.nextInt();
                        outOfBounds = validateCoordinates(x);
                    }while(outOfBounds);
                    do{
                        System.out.print("Enter Y: ");
                        y = sc.nextInt();
                        outOfBounds = validateCoordinates(y);
                    }while(outOfBounds);
                    switch (ocean[y][x]){
                        case ' ':
                            System.out.println("Sorry, you missed!");
                            ocean[y][x] = '-';
                            alreadyTried = false;
                            attackResult = 0;
                            break;
                        case '2':
                            System.out.println("Boom! You sunk the ship!");
                            ocean[y][x] = '!';
                            alreadyTried = false;
                            attackResult = 1;
                            break;
                        case '1':
                            System.out.println("Oh no, you sunk your own ship :(");
                            ocean[y][x] = 'x';
                            alreadyTried = false;
                            attackResult = 2;
                            break;
                        default:
                            System.out.println("You have already tried this target!");
                            alreadyTried = true;
                            break;
                    }
                }while(alreadyTried);
                return attackResult;
            }else{
                //computer's game
                do {
                    x = rand.nextInt(10);
                    y = rand.nextInt(10);
                    switch (ocean[y][x]){
                        case ' ':
                            System.out.println("Computer missed");
                            ocean[y][x] = '*';
                            alreadyTried = false;
                            attackResult = 0;
                        break;
                        case '2':
                            System.out.println("The Computer sunk one of its own ships!");
                            ocean[y][x] = 'x';
                            alreadyTried = false;
                            attackResult = 1;
                        break;
                        case '1':
                            System.out.println("The Computer sunk one of your ships");
                            ocean[y][x] = '!';
                            alreadyTried = false;
                            attackResult = 2;
                        break;
                        default:
                            System.out.println("Computer has already tried this target!");
                            alreadyTried = true;
                            break;
                    }
                }while(alreadyTried);
                return attackResult;
            }
        }
}


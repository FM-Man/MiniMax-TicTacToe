import java.util.Scanner;

public class Main {

    public  static int[][] boardState = new int[3][3];
    public static int turn = -1;
    public static int nodeCount = 0;

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        print();

        while(gameNotEnded()) {
            System.out.println();
            System.out.print("Enter cell: ");
            int input = sc.nextInt();
            if(input<10 && input>0){
                input--;
                if(boardState[input/3][input%3]==0){
                    boardState[input/3][input%3] = turn;
                    turn*=-1;
                }
                else continue;
            }
            else continue;
            print();


            if(gameNotEnded()){
                System.out.print("\nAI Thinking");
//                Thread.sleep(500);
//                System.out.print(".");
//                Thread.sleep(500);
//                System.out.print(".");
//                Thread.sleep(500);
                System.out.print(".\n\n");
                nodeCount=0;
                Node root = new Node(clone(boardState),NodeType.MAX, null);

                Node bestChild = null;
                for (Node c: root.children){
                    if(root.eval== c.eval){
                        bestChild=c;
                        break;
                    }
                }
                int way;
                if(bestChild==null)
                    throw new Exception("child pay na");
                else way = root.getDifference(bestChild);

                if(way<10 && way>0){
                    way--;
                    if(boardState[way/3][way%3]==0){
                        boardState[way/3][way%3] = turn;
                        turn*=-1;
                    }
                    else throw new Exception("AI ager position ei dite chay");
                }
                else throw new Exception("AI baire dite chay");

//                Thread.sleep(500);
                System.out.println(nodeCount+" node created\n");
                print();

            }
        }

        if(win()==0)
            System.out.println("\n\nMatch Drawn");
        else if(win()==-1)
            System.out.println("\n\nX won");
        else
            System.out.println("\n\nO won");
    }

    public static boolean gameNotEnded(){
        if(win()!=0)
            return false;
        for (int i=0;i<3; i++){
            for(int j=0;j<3;j++){
                if(boardState[i][j]==0){
                    return true;
                }
            }
        }
        return false;
    }

    private static int win(){
        for(int i=0; i<3; i++){
            if (boardState[i][0] !=0 && boardState[i][0] == boardState[i][1] && boardState[i][1] == boardState[i][2]) {
                return boardState[i][0];
            }
            if(boardState[0][i] !=0 && boardState[0][i] == boardState[1][i] && boardState[1][i] == boardState[2][i])
                return boardState[0][i];
        }
        if(boardState[0][0] !=0 && boardState[0][0] == boardState[1][1] && boardState[1][1] == boardState[2][2])
            return boardState[0][0];
        if(boardState[0][2] !=0 && boardState[2][0] == boardState[1][1] && boardState[1][1] == boardState[0][2])
            return boardState[2][0];

        return 0;
    }

    private static void print(){
        for(int i=0; i<3;i++){
            for(int j=0; j<3; j++){
                System.out.print(" ");
                if(boardState[i][j]==-1)
                    System.out.print("X");
                else if(boardState[i][j]==1)
                    System.out.print("@");
                else
                    System.out.print(/*(i*3+j+1)*/" ");
                System.out.print(" ");
                if(j!=2)
                    System.out.print("|");
                else System.out.println();
            }
            if(i!=2)
                System.out.println("-----------");
        }
    }

    private static void print(int[][] boardState){
        for(int i=0; i<3;i++){
            for(int j=0; j<3; j++){
                System.out.print(" ");
                if(boardState[i][j]==-1)
                    System.out.print("X");
                else if(boardState[i][j]==1)
                    System.out.print("O");
                else
                    System.out.print((i*3+j+1));
                System.out.print(" ");
                if(j!=2)
                    System.out.print("|");
                else System.out.println();
            }
            if(i!=2)
                System.out.println("-----------");
        }
    }

    public static int[][] clone(int[][] input){
        int[][] clone= new int[input.length][input[0].length];
        for (int i=0;i<input.length;i++){
            clone[i] = input[i].clone();
        }
        return clone;
    }

}

import java.util.ArrayList;
import java.util.Arrays;

public class Node {
    Node parent;
    ArrayList<Node> children = new ArrayList<>();
    int[][] state;
    boolean terminalNode = true;
    int eval;
    NodeType type;

    public Node(int[][] inputState, NodeType nt) {
        this.state = inputState;
        this.type = nt;
        Main.nodeCount++;

        for(int i=0; i<3;i++){
            for(int j=0; j<3; j++){
                if(state[i][j]==0){
                    terminalNode=false;
                    break;
                }
            }
            if(!terminalNode)
                break;
        }
        if(terminalNode)
            eval = win();
        else{
            if(win()!=0){
                terminalNode = true;
                eval = win();
            }
            else {
                for(int i=0; i<3; i++){
                    for(int j=0; j<3; j++){
                        if(state[i][j]==0){
                            int[][] temp = Main.clone(state);
                            temp[i][j]= type.equals(NodeType.MAX) ? 1 : -1;

                            NodeType newType = type.equals(NodeType.MAX)? NodeType.MIN:NodeType.MAX;
                            Node n = new Node(temp,newType);
                            n.parent = this;
                            children.add(n);
                        }
                    }
                }

                eval= evaluate();
            }
        }
    }

    private int win(){
        for(int i=0; i<3; i++){
            if (state[i][0] !=0 && state[i][0] == state[i][1] && state[i][1] == state[i][2]) {
                return state[i][0];
            }
            if(state[0][i] !=0 && state[0][i] == state[1][i] && state[1][i] == state[2][i])
                return state[0][i];
        }
        if(state[0][0] !=0 && state[0][0] == state[1][1] && state[1][1] == state[2][2])
            return state[0][0];
        if(state[0][2] !=0 && state[2][0] == state[1][1] && state[1][1] == state[0][2])
            return state[2][0];

        return 0;
    }

    private int evaluate(){
        if(type.equals(NodeType.MAX))
            return max();
        else return min();
    }

    private int max(){
        int childEval= Integer.MIN_VALUE;
        for(Node n: children){
            childEval = Integer.max(n.eval,childEval);
        }
        return childEval;
    }


    private int min(){
        int childEval= Integer.MAX_VALUE;
        for(Node n: children){
            childEval = Integer.min(n.eval,childEval);
        }
        return childEval;
    }

    public int getDifference(Node child) throws Exception {
        for(int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                if(child.state[i][j]==state[i][j]){
                    continue;
                }
                else if(child.state[i][j]==1 && state[i][j]==0)
                    return i*3+j+1;
                else if(child.state[i][j]==-1 && state[i][j]==0)
                    throw new Exception("ai -1 daan dise kemne jani");
                else if(state[i][j]!=0)
                    throw new Exception("ai ager daan nai koira dese kemne?");
                else throw new Exception("ki hoiche jani na. but check koira dekh");
            }
        }
        throw new Exception("akam chodaiso");
    }

    private void print(int[][] arr){
        for (int[] a:arr) {
            System.out.println(Arrays.toString(a));
        }
        System.out.println("------------------------------------------------");
    }
}

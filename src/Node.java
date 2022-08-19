import java.util.ArrayList;
import java.util.Arrays;

public class Node {
    Node parent;
    ArrayList<Node> children = new ArrayList<>();
    int[][] state;
    boolean terminalNode = true;
    int eval;
    NodeType type;
    int alphaBeta;
    int tempParentAlphaBeta;

    public Node(int[][] inputState, NodeType nt, Node parent) {
        this.state = inputState;
        this.type = nt;
        Main.nodeCount++;
        this.parent = parent;

        if(type.equals(NodeType.MAX))
            alphaBeta = Integer.MIN_VALUE;
        else alphaBeta = Integer.MAX_VALUE;

        if(parent!=null)
            tempParentAlphaBeta = this.parent.alphaBeta;
        else tempParentAlphaBeta = Integer.MAX_VALUE;

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
        if(terminalNode) {
            eval = win();
        }
        else{
            if(win()!=0){
                terminalNode = true;
                eval = win();
            }
            else {
                for(int i=0; i<9; i++){
                    if(state[i/3][i%3]==0){
                        int[][] temp = Main.clone(state);
                        temp[i/3][i%3]= type.equals(NodeType.MAX) ? 1 : -1;
                        NodeType newType = type.equals(NodeType.MAX)? NodeType.MIN:NodeType.MAX;
                        Node n = new Node(temp,newType,this);
                        children.add(n);

                        if (!pushUpwards()) {
                            break;
                        }
                    }
                }
                eval= evaluate();
                tempParentAlphaBeta = eval;
                setParent();
            }
        }

        if(terminalNode){
            alphaBeta = eval;
            if(pushUpwards()){
                setParent();
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

    private boolean pushUpwards(){
        if(type.equals(NodeType.MAX)){
            if(alphaBeta<= tempParentAlphaBeta){
                tempParentAlphaBeta = alphaBeta;
                return true;
            }
            else return false;
        }
        else {
            if(alphaBeta>=tempParentAlphaBeta){
                tempParentAlphaBeta = alphaBeta;
                return true;
            }
            else return false;
        }
    }

    private void setParent(){
        if(parent != null){
            if(type.equals(NodeType.MAX)){
                if(tempParentAlphaBeta<=parent.alphaBeta){
                    parent.alphaBeta = tempParentAlphaBeta;
                }
            } else {
                if(tempParentAlphaBeta >= parent.alphaBeta){
                    parent.alphaBeta = tempParentAlphaBeta;
                }
            }
        }
    }
}

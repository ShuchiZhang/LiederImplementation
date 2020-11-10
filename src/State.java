import java.util.ArrayList;

public class State {
    public int numClass;
    public int delay;
    public Integer[] numEachClass;
    public Integer[][] times;
    public ArrayList<Integer> order;
    public ArrayList<Integer> opTime;

    public State(int numClass){
        this.numClass = numClass;
        this.delay = 0;
        numEachClass = new Integer[numClass];
        for(int i = 0; i < numClass; i ++){
            numEachClass[i] = 0;
        }
        times = new Integer[numClass][2];
        for(int i = 0; i < numClass; i ++){
            for(int j = 0; j < 2; j ++){
                times[i][j] = 0;
            }
        }
        order = new ArrayList<Integer>();
        opTime = new ArrayList<Integer>();
    }

    public State copy(){
        State newState = new State(numClass);
        for(int i = 0 ; i < numClass; i ++) {
            newState.numEachClass[i] = numEachClass[i];
        }
        for(int i = 0; i < numClass; i ++){
            for(int j = 0; j < 2; j ++){
                newState.times[i][j] = times[i][j];
            }
        }
        for(int i = 0; i < order.size(); i ++){
            newState.order.add(order.get(i));
        }
        for(int i = 0; i < opTime.size(); i ++){
            newState.opTime.add(opTime.get(i));
        }
        return newState;
    }

    public boolean compare(State st){
        for(int i = 0; i < numClass; i ++){
            if(numEachClass[i] > st.numEachClass[i]){
                return true;
            }
        }
        for(int i = 0; i < numClass; i ++){
            for(int j = 0; j < 2; j ++){
                if(times[i][j] > st.times[i][j]){
                    return true;
                }
            }
        }
        return false;
    }
}

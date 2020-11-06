import java.util.ArrayList;

public class State {
    public int numClass;
    public Integer[] numEachClass;
    public Integer[][] times;

    public State(int numClass){
        this.numClass = numClass;
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
    }

    public State copy(){
        State newState = new State(numClass);
        newState.numEachClass = numEachClass;
        newState.times = times;
        return newState;
    }

    public boolean compare(State st){
        for(int i = 0; i < numClass; i ++){
            if(numEachClass[i] < st.numEachClass[i]){
                return false;
            }
        }
        for(int i = 0; i < numClass; i ++){
            for(int j = 0; j < numClass; j ++){
                if(times[i][j] < st.times[i][j]){
                    return false;
                }
            }
        }
        return true;
    }
}

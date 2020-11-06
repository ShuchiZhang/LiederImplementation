import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.io.IOException;
import java.nio.charset.Charset;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

public class Main {
    public static int numClass;
    public static ArrayList<Aircraft> aircraft;

    public static ArrayList<State> currentStates;
    public static ArrayList<State> nextStates;

    public static int separationTime(int aircraft1, int aircraft2) {
        return 60;
    }

    public static void addTime(Aircraft aircraft, State state){
        for(int i = 0; i < 2; i ++) {
            State newState = state.copy();
            newState.numEachClass[aircraft.type - 1]++;
            int max = newState.times[0][i] + separationTime(0, aircraft.type);
            for(int j = 1; j < numClass; j ++){
                if(newState.times[j][i] + separationTime(j, aircraft.type) > max){
                    max = newState.times[j][i] + separationTime(j, aircraft.type);
                }
            }
            newState.times[aircraft.type-1][i] = max;
//            for(int j = 0; j < nextStates.size(); j ++) {
//                if(newState.compare(nextStates.get(j))) {
                    nextStates.add(newState);
//                }
//            }
        }
    }

    public static void initialize(){
        aircraft = new ArrayList<>();
        currentStates = new ArrayList<>();
        nextStates = new ArrayList<>();
        numClass = 6;
        String filePath = "resources/lieder2016-N30-RL90-PH1200.csv";

        try {
            CsvReader csvReader = new CsvReader(filePath);
            csvReader.readHeaders();
            while (csvReader.readRecord()){
                int type = Integer.parseInt(csvReader.get(1));
                int target = Integer.parseInt(csvReader.get(2));
                aircraft.add(new Aircraft(type, target));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){

        initialize();
        State current = new State(6);
        currentStates.add(current);
        for(int i = 0; i < aircraft.size(); i ++){
            System.out.println("aaa"+currentStates.size());
            for(int j = 0; j < currentStates.size(); j ++){
                addTime(aircraft.get(i),currentStates.get(j));
            }
            System.out.println("bbb"+nextStates.size());
            currentStates = nextStates;
            nextStates = new ArrayList<>();
        }

        for(int i = 0; i < 5; i ++){
            System.out.println(currentStates.get(0).numEachClass[i]);
        }
    }
}

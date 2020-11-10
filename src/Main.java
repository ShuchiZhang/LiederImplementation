import java.util.ArrayList;
import java.io.IOException;
import com.csvreader.CsvReader;

public class Main {
    public static int numClass;
    public static ArrayList<ArrayList<Aircraft>> aircraft;

    public static ArrayList<State>[][][][][][] stateSpace;

    public static int separationTime(int aircraft1, int aircraft2) {
        if(aircraft1 == 1){
            switch(aircraft2){
                case 1 :
                    return 82;
                case 2 :
                    return 69;
                case 3 :
                    return 60;
                default:
                    return 75;
            }
        }
        if(aircraft1 == 2){
            switch(aircraft2){
                case 1 :
                    return 131;
                case 2 :
                    return 69;
                case 3 :
                    return 60;
                default:
                    return 75;
            }
        }
        if(aircraft1 == 3){
            switch(aircraft2){
                case 1 :
                    return 196;
                case 2 :
                    return 157;
                case 3 :
                    return 96;
                default:
                    return 75;
            }
        }
        if(aircraft1 == 4){
            return 60;
        }
        if(aircraft1 == 5){
            return 60;
        }
        if(aircraft1 == 6){
            switch(aircraft2){
                case 4 :
                    return 120;
                case 5 :
                    return 120;
                case 6 :
                    return 90;
                default:
                    return 60;
            }
        }
        return 0;
    }

    public static void addAC(ArrayList<Aircraft> aircrafts, int numScheduled, int type){
        for(int a = 0; a < 4; a ++){
            for(int b = 0; b < 4; b ++){
                for(int c = 0; c < 4; c ++){
                    for(int d = 0; d < 4; d ++){
                        for(int e = 0; e < 4; e ++){
                            for(int h = 0; h < 4; h ++){
                                if(a + b + c + d + e + h == numScheduled){
                                    if(stateSpace[a][b][c][d][e][h] == null){
                                        continue;
                                    }
                                    for(int i = 0; i < stateSpace[a][b][c][d][e][h].size(); i ++){
                                        State st = stateSpace[a][b][c][d][e][h].get(i);
                                        if(st.numEachClass[type] < aircrafts.size()){
                                            for(int j = 0; j < 2; j ++) {
                                                State newST = st.copy();
                                                int max = aircrafts.get(newST.numEachClass[type]).releaseTime;
                                                for(int k = 0; k < 6; k ++){
                                                    if((newST.times[k][j] + separationTime(type, k) > max) && (newST.numEachClass[k] != 0)){
                                                        max = newST.times[k][j] + separationTime(type, k);
                                                    }
                                                }
                                                newST.times[type][j] = max;
                                                newST.delay += max - aircrafts.get(newST.numEachClass[type]).releaseTime;
                                                newST.numEachClass[type]++;
                                                newST.order.add(type);
                                                newST.opTime.add(max);
                                                if(stateSpace[newST.numEachClass[0]][newST.numEachClass[1]][newST.numEachClass[2]][newST.numEachClass[3]][newST.numEachClass[4]][newST.numEachClass[5]] == null){
                                                    ArrayList<State> sts = new ArrayList<>();
                                                    sts.add(newST);
                                                    stateSpace[newST.numEachClass[0]][newST.numEachClass[1]][newST.numEachClass[2]][newST.numEachClass[3]][newST.numEachClass[4]][newST.numEachClass[5]] = sts;
                                                }
                                                else{
                                                    boolean add = true;
                                                    for(int k = 0; k < stateSpace[newST.numEachClass[0]][newST.numEachClass[1]][newST.numEachClass[2]][newST.numEachClass[3]][newST.numEachClass[4]][newST.numEachClass[5]].size(); k ++) {
                                                        if (newST.compare(stateSpace[newST.numEachClass[0]][newST.numEachClass[1]][newST.numEachClass[2]][newST.numEachClass[3]][newST.numEachClass[4]][newST.numEachClass[5]].get(k))) {
                                                            add = false;
                                                        }
                                                    }
                                                    if(add) {
                                                            stateSpace[newST.numEachClass[0]][newST.numEachClass[1]][newST.numEachClass[2]][newST.numEachClass[3]][newST.numEachClass[4]][newST.numEachClass[5]].add(newST);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void initialize(){
        aircraft = new ArrayList<>();
        numClass = 6;
        for(int i = 0; i < numClass; i++){
            aircraft.add(new ArrayList<Aircraft>());
        }
        State st = new State(6);
        ArrayList<State> sts = new ArrayList<>();
        sts.add(st);
        stateSpace = new ArrayList[4][4][4][4][4][4];
        stateSpace[0][0][0][0][0][0] = sts;
        String filePath = "resources/lieder2016-N30-RL90-PH1200.csv";

        try {
            CsvReader csvReader = new CsvReader(filePath);
            csvReader.readHeaders();
            while (csvReader.readRecord()){
                int type = Integer.parseInt(csvReader.get(1));
                int target = Integer.parseInt(csvReader.get(2));
                Aircraft ac = new Aircraft(type, target);
                aircraft.get(type - 1).add(ac);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        initialize();
        for (int i = 0; i < 10; i++) {
//            System.out.println("aaa");
            for (int j = 0; j < aircraft.size(); j++) {
                addAC(aircraft.get(j), i, j);
            }
        }
        int min = 99999;
        int aa = 0;
        int bb = 0;
        int cc = 0;
        int dd = 0;
        int ee = 0;
        int hh = 0;
        int mm = 0;
        for(int a = 0; a < 4; a ++) {
            for (int b = 0; b < 4; b++) {
                for (int c = 0; c < 4; c++) {
                    for (int d = 0; d < 4; d++) {
                        for (int e = 0; e < 4; e++) {
                            for (int h = 0; h < 4; h++) {
                                if(a + b + c + d + e + h == 10){
                                    if(stateSpace[a][b][c][d][e][h] != null){
                                        for(int m = 0; m < stateSpace[a][b][c][d][e][h].size(); m ++){
                                            if(stateSpace[a][b][c][d][e][h].get(m).delay < min){
                                                min = stateSpace[a][b][c][d][e][h].get(m).delay;
                                                aa = a;
                                                bb = b;
                                                cc = c;
                                                dd = d;
                                                ee = e;
                                                hh = h;
                                                mm = m;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println(min);
        for(int o = 0; o < 10; o ++){
            System.out.println(stateSpace[aa][bb][cc][dd][ee][hh].get(mm).order.get(o) + " " + stateSpace[aa][bb][cc][dd][ee][hh].get(mm).opTime.get(o));
        }
    }
}

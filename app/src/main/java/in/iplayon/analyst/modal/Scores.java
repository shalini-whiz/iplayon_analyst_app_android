package in.iplayon.analyst.modal;

import java.util.ArrayList;

/**
 * Created by shalinibr on 8/17/17.
 */

public class Scores {

    ArrayList<Integer> setScoresA;
    ArrayList<Integer> setScoresB;

    public ArrayList<Integer> getSetScoresA() {

        for(int x=0;x<setScoresA.size();x++)
        {
            setScoresA.set(x,Integer.parseInt(setScoresA.get(x).toString()));
        }
        return setScoresA;
    }

    public void setSetScoresA(ArrayList<Integer> setScoresA) {
        this.setScoresA = setScoresA;
    }

    public ArrayList<Integer> getSetScoresB() {
        for(int x=0;x<setScoresB.size();x++)
        {
            setScoresB.set(x,Integer.parseInt(setScoresB.get(x).toString()));
        }
        return setScoresB;
    }

    public void setSetScoresB(ArrayList<Integer> setScoresB) {


        this.setScoresB = setScoresB;
    }
}

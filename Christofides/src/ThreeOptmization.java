import java.util.Collections;
import java.util.List;

public class ThreeOptmization {

    private int[][] distanceGraph;

    private List<Integer> cities;

    int numberOfCities;

    public List<Integer> doOpt(List<Integer> cities) {

        boolean locallyOptimal = false;
        int i,j,k; // Tour indices
        int x1,x2,y1,y2,z1,z2; //Cities
        numberOfCities = cities.size();
        int gainExpected;
        Move3 bestMove = new Move3(0,0,0,null,0);
        int counter = 0;
        int swaps = 0;
        while(!locallyOptimal && swaps==0) {
            counter++;
            locallyOptimal = true;
            int bestMoveGain = 0;
            outerloop:
            for(int counter_1=0;counter_1<=numberOfCities-1;counter_1++) {

                i = counter_1;
                x1 = cities.get(i);
                x2 = cities.get((i+1) % numberOfCities);

                for(int counter_2=1;counter_2<=numberOfCities-3;counter_2++) {

                    j = (i+counter_2) % numberOfCities;
                    y1 = cities.get(j);
                    y2 = cities.get((j+1) % numberOfCities);

                    for(int counter_3=counter_2+1;counter_3<=numberOfCities-1;counter_3++) {

                        k = (i+counter_3) % numberOfCities;
                        z1 = cities.get(k);
                        z2 = cities.get((k+1) % numberOfCities);

                        for(Reconnection_3_Opt_Case cases:Reconnection_3_Opt_Case.values()) {

                            if(cases==Reconnection_3_Opt_Case.case_3 || cases==Reconnection_3_Opt_Case.case_6 || cases==Reconnection_3_Opt_Case.case_7) {

                                gainExpected = gain_from_3_Opt(x1,x2,y1,y2,z1,z2,cases);

                                if(gainExpected>bestMove.gain) {

                                    locallyOptimal = false;
                                    bestMove = new Move3(i,j,k,cases,gainExpected);
                                }

                            }

                        }
                    }
                }

            }

            if (!locallyOptimal) {
                make_3_opt_move(cities,bestMove.i,bestMove.j,bestMove.k,bestMove.cases);
                swaps++;
            }
        }

        return cities;
    }

    private int gain_from_3_Opt(int x1, int x2, int y1, int y2,int z1, int z2, Reconnection_3_Opt_Case cases) {

        int del_length=0, add_length=0;

        switch(cases) {

            case case_3: {

                del_length = distanceGraph[x1][x2]+distanceGraph[y1][y2];
                add_length = distanceGraph[x1][y1]+distanceGraph[x2][y2];
                break;
            }

            case case_6: {
                del_length = distanceGraph[x1][y2]+distanceGraph[z1][y1]+distanceGraph[x2][z2];
                add_length = distanceGraph[x1][x2]+distanceGraph[y1][y2]+distanceGraph[z1][z2];
                break;

            }

            case case_7: {
                del_length = distanceGraph[x1][y2]+distanceGraph[z1][x2]+distanceGraph[y1][z2];
                add_length = distanceGraph[x1][x2]+distanceGraph[y1][y2]+distanceGraph[z1][z2];
                break;

            }
        }

        return del_length-add_length;
    }

    private void make_3_opt_move(List<Integer> cities,int i, int j, int k, Reconnection_3_Opt_Case cases) {

        switch(cases) {

            case case_3: reverse_segment(cities,(i+1)%numberOfCities,j); break;
            case case_6: {
                reverse_segment(cities, (k + 1) % numberOfCities, i);
                reverse_segment(cities, (j + 1) % numberOfCities, k);
                break;
            }
            case case_7: {
                reverse_segment(cities,(k+1) % numberOfCities,i);
                reverse_segment(cities,(i+1) % numberOfCities,j);
                reverse_segment(cities,(j+1) % numberOfCities,k);
                break;
            }

        }

    }


    private void reverse_segment(List<Integer> cities, int startIndex, int endIndex) {

        int inversion_size = ((numberOfCities+endIndex-startIndex+1) % numberOfCities)/2;
        int left = startIndex;
        int right = endIndex;
        for(int counter=1;counter<=inversion_size;counter++) {
            swap_tour(left,right,cities);
            left = (left+1) % numberOfCities;
            right = (numberOfCities+right-1) % numberOfCities;
        }
    }

    private void swap_tour(int left, int right, List<Integer> cities) {

        Collections.swap(cities,left,right);

    }

    public void setDistanceGraph(int[][] distanceGraph) {
        this.distanceGraph = distanceGraph;
    }

    class Move3 {

        int i,j,k;
        Reconnection_3_Opt_Case cases;
        int gain;

        public Move3(int i, int j, int k, Reconnection_3_Opt_Case cases, int gain) {
            this.i = i;
            this.j = j;
            this.k = k;
            this.cases = cases;
            this.gain = gain;
        }
    }
}

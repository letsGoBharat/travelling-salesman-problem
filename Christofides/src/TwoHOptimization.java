import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TwoHOptimization {

    /*
    TwoH Optimization algorithm to optimize the found tour.
     */

    public int getFitness() {
        return fitness;
    }

    public int[][] getDistanceGraph() {
        return distanceGraph;
    }

    public void setDistanceGraph(int[][] distanceGraph) {
        this.distanceGraph = distanceGraph;
    }

    private int distanceGraph[][];

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    private int fitness;

    private int numberOfCities;

    public Integer[] doOpt(Integer[] cities) {
        numberOfCities = cities.length;
        int x1,x2,y1,y2,v0; //cities
        boolean locallyOptimal =false;
        int i,j; //tour index
        boolean breakMainLoop = false;
        int counter = 0;
        int numberOfSwaps = 0;
        while(!locallyOptimal && counter<300 && numberOfSwaps<10) {
            counter++;
            locallyOptimal = true;
            for(int counter_1=0;counter_1<=numberOfCities-3;counter_1++) {
                    i=counter_1;
                    x1 = cities[i];
                    x2 = cities[(i+1) % numberOfCities];
                    int del2_length;
                    int dx1y1, dx2y2, dx2y1;
                    for(int counter_2=counter_1+2;counter_2<=numberOfCities-1;counter_2++) {

                         j = counter_2;
                        y1 = cities[j];
                        y2 = cities[(j+1) % numberOfCities];
                        del2_length = distanceGraph[x1][x2]+distanceGraph[y1][y2];
                        dx1y1 = distanceGraph[x1][y1];
                        dx2y2 = distanceGraph[x2][y2];
                        if(del2_length-(dx1y1+dx2y2)>0) {
                            make_2_opt_move(cities,i,j);
                            locallyOptimal = false;
                            breakMainLoop = true;
                            numberOfSwaps++;
                            break;
                        } else {
                            dx2y1 = distanceGraph[x2][y1];
                            v0 = cities[(i+2) % numberOfCities];
                            if(v0 != y1) {
                                if((del2_length + distanceGraph[x2][v0])-(dx2y2+dx2y1+distanceGraph[x1][v0])>0) {
                                    make_node_shift_move(cities, (i+1) % numberOfCities, j);
                                    locallyOptimal = false;
                                    breakMainLoop = true;
                                    numberOfSwaps++;
                                    break;
                                } else {
                                    v0 = cities[(numberOfCities+j-1) % numberOfCities];
                                    if (v0!=x2) {

                                        if((del2_length+distanceGraph[y1][v0])-(dx1y1+dx2y1+distanceGraph[y2][v0])>0) {
                                            make_node_shift_move(cities,j,i);
                                            locallyOptimal = false;
                                            breakMainLoop = true;
                                            numberOfSwaps++;
                                            break;
                                        }

                                    }
                                }
                            }
                        }
                    }

                    if(breakMainLoop) {
                        break;
                    }
            }

        }


        return cities;
    }


    private int gain_from_2Opt(int x1,int x2,int y1, int y2) {

        int del_length = distanceGraph[x1][x2]-distanceGraph[y1][y2];
        int add_length = distanceGraph[x1][y1]-distanceGraph[x2][y2];
        return del_length-add_length;
    }

    private void make_2_opt_move(Integer[] cities, int startIndex, int endIndex ) {

        reverse_segment(cities,startIndex,endIndex);
    }

    private void reverse_segment(Integer[] cities, int startIndex, int endIndex) {

        int left;
        int right;

        int inversion_size = ((numberOfCities+endIndex-startIndex+1) % numberOfCities)/2;
        left = startIndex;
        right = endIndex;
        for(int counter=1;counter<=inversion_size;counter++) {
            swap_tour(left,right,cities);
            left = (left+1) % numberOfCities;
            right = (numberOfCities+right-1) % numberOfCities;
        }
    }

    private void swap_tour(int left, int right, Integer[] cities) {

        int temp = cities[left];
        cities[left] = cities[right];
        cities[right] = temp;

    }



    private int gain_from_node_shift(int x1,int x2, int v0, int y1, int y2) {
        int del_length = distanceGraph[x1][x2]+distanceGraph[x2][v0]+distanceGraph[y1][y2];
        int add_length = distanceGraph[x1][v0]+distanceGraph[y1][x2]+distanceGraph[x2][y2];
        return del_length-add_length;
    }

    private void make_node_shift_move(Integer[] cities,int i, int j) {

        int left;
        int right;
        int x0;

        int shiftSize = (j-i+1+numberOfCities) % numberOfCities;

        x0 = cities[i];
        left = i;

        for(int counter=1;counter<shiftSize;counter++) {

            right = (left+1)%numberOfCities;
            cities[left] = cities[right];
            left = right;
        }

        cities[j] = x0;

    }


}

package main.java.schedulerdisk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.abs;

/**
 *
 * @author [name]
 */
public class DiskScheduler {

    private final int cylinders;
    private int currentCylinder;
    private final int previousCylinder;
    private int totalMoves;

    public DiskScheduler(int cylinders, int currentCylinder, int previousCylinder) {
        this.cylinders = cylinders;
        this.currentCylinder = currentCylinder;
        this.previousCylinder = previousCylinder;
        this.totalMoves = 0;
    }

    public int getTotalMoves() {
        return this.totalMoves;
    }

    public ArrayList convertStrListToIntArrList(String[] strList) {
        ArrayList<Integer> intArrayList = new ArrayList();
        for (Object request: strList) {
            intArrayList.add(Integer.parseInt((String) request));
        }
        return intArrayList;
    }

    public void useFCFS(String requestQueue) {
        List<String> requestQueueLst = Arrays.asList(requestQueue.split(","));
        for (String request : requestQueueLst) {
            this.totalMoves += abs(this.currentCylinder-Integer.parseInt(request));
            this.currentCylinder = Integer.parseInt(request);
        }
    }

    public int getShortestDistance(ArrayList<Integer> requestQueue) {
        int shortestDistanceRequest = requestQueue.get(0);
        int lastDiff = abs(this.currentCylinder-shortestDistanceRequest);
        for (int i=1; i<requestQueue.size(); i++) {
            int newDif = abs(this.currentCylinder-requestQueue.get(i));
            if (newDif < lastDiff) {
                shortestDistanceRequest = requestQueue.get(i);
                lastDiff = newDif;
            }
        }
        return shortestDistanceRequest;
    }

    public void useSSTF(String requestQueue) {
        String[] requestQueueArr = requestQueue.split(",");
        ArrayList<Integer> requestQueueArrLst = convertStrListToIntArrList(requestQueueArr);

        while (!requestQueueArrLst.isEmpty()) {
            int shortestDistanceRequest = this.getShortestDistance(requestQueueArrLst);
            this.totalMoves += abs(this.currentCylinder-shortestDistanceRequest);
            this.currentCylinder = shortestDistanceRequest;
            requestQueueArrLst.remove(Integer.valueOf(shortestDistanceRequest));
        }
    }

    public void useLOOK(String requestQueue) {
        String[] requestQueueArr = requestQueue.split(",");
        ArrayList<Integer> requestQueueArrLst = convertStrListToIntArrList(requestQueueArr);
        Collections.sort(requestQueueArrLst, Collections.reverseOrder());

        String direction = "left";
        // get initial direction
        if (this.currentCylinder > this.previousCylinder) {
            direction = "right";
        }
        while (!requestQueueArrLst.isEmpty()) {
            ArrayList<Integer> itemsToRemove = new ArrayList<>();
            for (int request : requestQueueArrLst) {
                if ((direction == "left" && request < this.currentCylinder) | (direction == "right" && request > this.currentCylinder)) {
                   this.totalMoves += abs(this.currentCylinder-request);
                   itemsToRemove.add(request);
                   this.currentCylinder = request;
                }
            }
            // Remove items from list
            for (int request : itemsToRemove) {
                requestQueueArrLst.remove(Integer.valueOf(request));
            }

            if (direction == "left") {
                Collections.sort(requestQueueArrLst);
                direction = "right"; // go right now
            } else {
                Collections.sort(requestQueueArrLst, Collections.reverseOrder());
                direction = "left"; // go left now
            }
        }
    }

    public void useCLOOK(String requestQueue) {
        String[] requestQueueArr = requestQueue.split(",");
        ArrayList<Integer> requestQueueArrLst = convertStrListToIntArrList(requestQueueArr);
        Collections.sort(requestQueueArrLst);

        while (!requestQueueArrLst.isEmpty()) {
            ArrayList<Integer> itemsToRemove = new ArrayList<>();
            for (int request : requestQueueArrLst) {
                if (request > this.currentCylinder) {
                    this.totalMoves += abs(this.currentCylinder-request);
                    itemsToRemove.add(request);
                    this.currentCylinder = request;
                }
            }

            this.totalMoves += abs(this.currentCylinder - requestQueueArrLst.get(0));
            this.currentCylinder = requestQueueArrLst.get(0);
            requestQueueArrLst.remove(0);

            // Remove items from list
            for (int request : itemsToRemove) {
                requestQueueArrLst.remove(Integer.valueOf(request));
            }

        }

    }

}

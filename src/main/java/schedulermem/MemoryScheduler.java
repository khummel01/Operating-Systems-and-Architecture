package main.java.schedulermem;

import java.lang.reflect.Array;
import java.util.*;

/**
 * @author Katie Hummel
 */
public class MemoryScheduler {

    private int pageFaultCount;
    private int frames;

    public MemoryScheduler(int frames) {
        this.frames = frames;
        this.pageFaultCount = 0;
    }

    public int getPageFaultCount() {
        return this.pageFaultCount;
    }

    public void useFIFO(String referenceString) {
        ArrayList<String> pagesInMemory = new ArrayList<>();
        String[] pages = referenceString.split(",");
        for (String page: pages) {
            if (!pagesInMemory.contains(page)) {
                pagesInMemory.add(page);
                if (pagesInMemory.size() > this.frames) {
                    pagesInMemory.remove(0);
                }
                this.pageFaultCount++;
            }
        }
    }

    public String getFurthestPage(ArrayList pageSubList, ArrayList pagesInMemory) {
        HashMap<String,Integer> pageIdxMap = new HashMap<>();
        for (Object page : pagesInMemory) {
            if (!pageSubList.contains(page)) {
                pageIdxMap.put((String) page,Integer.MAX_VALUE);
            } else {
                pageIdxMap.put((String) page, pageSubList.indexOf(page));
            }
        }
        return Collections.max(pageIdxMap.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
    }

    public void useOPT(String referenceString) {
        ArrayList<String> pagesInMemory = new ArrayList<>();
        String[] pages = referenceString.split(",");
        for (int i=0; i<pages.length; i++) {
            if (!pagesInMemory.contains(pages[i])) {
                if (pagesInMemory.size() < this.frames) {
                   pagesInMemory.add(pages[i]);
                } else {
                    ArrayList<String> pageSubList = new ArrayList<String>(Arrays.asList(pages).subList(i+1, pages.length));
                    String pageToReplace = getFurthestPage(pageSubList, pagesInMemory);
                    pagesInMemory.set(pagesInMemory.indexOf(pageToReplace),pages[i]);
                }
                this.pageFaultCount++;
            }
        }

    }

    public void useLRU(String referenceString) {
        throw new UnsupportedOperationException();
    }

}

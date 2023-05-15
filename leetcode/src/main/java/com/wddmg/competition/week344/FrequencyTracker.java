package com.wddmg.competition.week344;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

class FrequencyTracker {
    private Map<Integer,Integer> map = new HashMap<>();
    private Map<Integer, LinkedList<Integer>> freMap = new HashMap<>();
    public FrequencyTracker() {

    }
    
    public void add(int number) {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(number);
        if(map.containsKey(number)){
            int fre = map.get(number) + 1;
            map.put(number,fre);
            if(freMap.get(fre - 1).size() == 1){
                freMap.remove(fre- 1);
                if(freMap.get(fre) == null){
                    freMap.put(fre,list);
                }else{
                    freMap.get(fre).add(number);
                }
            }else{
                if(freMap.get(fre).size() == 0){
                    freMap.put(fre,list);
                }else{
                    freMap.get(fre).add(number);
                }
            }
            freMap.put(fre,list);
        }else{
            map.put(number,1);
            if(freMap.get(1) == null){
                freMap.put(1,list);
            }else{
                freMap.get(1).add(number);
            }

        }
    }
    
    public void deleteOne(int number) {
        if(map.containsKey(number)){
            int fre = map.get(number);
            map.put(number,fre - 1);
            LinkedList<Integer> temp = freMap.get(fre);
            temp.remove(number);
            if(temp.size() == 0){
                freMap.remove(fre);
            }
        }
    }
    
    public boolean hasFrequency(int frequency) {
        if(freMap.containsKey(frequency)){
            return true;
        }
        Iterator<Integer> iter = map.values().iterator();
        while (iter.hasNext()) {
            if(iter.next() == frequency){
                return true;
            };
        }
        return false;
    }
}
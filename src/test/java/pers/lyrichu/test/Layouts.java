package pers.lyrichu.test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 基于类型的集合数据散列化实现
 * ww
 */
public class Layouts {

    private List<Layout> ls;
    private List<Integer> ts;

    private List<Layout> lds;
    private List<String> ids;
    private Map<String, Layout> mps;

    public Layouts() {

    }

    public Layouts(List<Layout> ls) {
        this.ls = ls;
        inits();
    }

    public static void main(String[] args){
        List<Layout> lys = new LinkedList<>();
        lys.add(new Layout("1",1));
        lys.add(new Layout("2",2));
        lys.add(new Layout("3",2));
        lys.add(new Layout("4",3));
        lys.add(new Layout("5",3));
        lys.add(new Layout("6",4));
        lys.add(new Layout("7",5));
        lys.add(new Layout("8",5));
        lys.add(new Layout("9",5));
        lys.add(new Layout("10",1));
        lys.add(new Layout("11",1));
        lys.add(new Layout("12",2));
        lys.add(new Layout("13",2));
        lys.add(new Layout("14",2));
        lys.add(new Layout("15",2));
        lys.add(new Layout("16",4));
        lys.add(new Layout("17",1));
        lys.add(new Layout("18",1));
        lys.add(new Layout("19",5));
        lys.add(new Layout("20",5));
        lys.add(new Layout("21",4));
        lys.add(new Layout("22",1));
        lys.add(new Layout("23",1));
        lys.add(new Layout("24",5));
        lys.add(new Layout("25",2));

        Layouts ly = new Layouts(lys);
        ly.initials();
        ly.simpleLayouts(0);
        ly = new Layouts(lys);
        ly.simpleLayouts1(3);
        ly = new Layouts(lys);
        ly.typeLayouts();
        System.out.println();
        System.out.println();
    }

    //数据初始化
    private void inits(){
        if(null!=ls && ls.size()>0){
            this.ts = new LinkedList<>();
            this.ids= new LinkedList<>();
            this.mps= new HashMap<>();
            this.lds= new LinkedList<>();

            ls.forEach(dto->{ mps.put(dto.getId(), dto); });
            ls.stream().filter(obj-> null!=obj.getType()).map(Layout::getType).distinct().sorted().collect(Collectors.toList()).forEach(type->{
                ts.add(type);
            });
        }
    }

    /*** 最简编排(简单高效、相邻打散、最大化保留顺序) */
    private void simpleLayouts(int type){
        int cate = type;
        for(int i=0; i<ls.size(); i++) {
            if(!ids.contains(ls.get(i).getId()) && ls.get(i).getType()!=cate){
                cate = ls.get(i).getType();
                ids.add(ls.get(i).getId());
                for(int j=0; j<ls.size(); j++) {
                    if(!ids.contains(ls.get(j).getId()) && ls.get(j).getType()!=cate){
                        cate = ls.get(j).getType();
                        ids.add(ls.get(j).getId());
                        break;
                    }
                }
            }
            if(i==ls.size()-1 && ls.size()!=ids.size()){
                //无解化后补
                for(int j=0; j<ls.size(); j++) {
                    if(!ids.contains(ls.get(j).getId())){
                        ids.add(ls.get(j).getId());
                    }
                }
            }
        }
        ids.forEach(id->{ lds.add(mps.get(id)); });
        hashRatio("散列模型：simpleLayouts");
    }

    /**
     * simpleLayouts 的改进版，可以指定 滑动窗口的大小(默认是2,即相邻相同类型打散)
     */
    private void simpleLayouts1(int windowSize){
        List<Integer> windows = new LinkedList<>();
        List<String> restIds = new ArrayList<>();
        for(int i=0; i<ls.size(); i++) {
            if(!ids.contains(ls.get(i).getId()) && !containInWindows(windows,ls.get(i).getType(),windowSize)){
                int cate = ls.get(i).getType();
                updateWindows(windows,cate,windowSize);
                ids.add(ls.get(i).getId());
                for(int j=0; j<ls.size(); j++) {
                    if(!ids.contains(ls.get(j).getId()) && !containInWindows(windows,ls.get(j).getType(),windowSize)) {
                        cate = ls.get(j).getType();
                        updateWindows(windows,cate,windowSize);
                        ids.add(ls.get(j).getId());
                        break;
                    }
                }
            }
            if(i==ls.size()-1 && ls.size()!=ids.size()){
                //无解化后补
                for(int j=0; j<ls.size(); j++) {
                    if(!ids.contains(ls.get(j).getId())){
                        ids.add(ls.get(j).getId());
                        restIds.add(ls.get(j).getId());
                    }
                }
            }
        }
        System.out.println("restIds:" + String.join(",",restIds));
        ids.forEach(id->{ lds.add(mps.get(id)); });
        hashRatio("散列模型：simpleLayouts1",windowSize);
    }

    private boolean containInWindows(List<Integer> windows,int type,int maxWindowSize) {
        if (windows.size() < maxWindowSize) {
            return windows.contains(type);
        } else {
            return windows.subList(windows.size() - maxWindowSize + 1,windows.size()).contains(type);
        }
    }

    private void updateWindows(List<Integer> windows,int type,int maxWindowSize) {
        if (windows.size() < maxWindowSize) {
            windows.add(type);
        } else {
            windows.remove(0);
            windows.add(type);
        }
    }

    /*** 类型编排(强制类型轮询、最大化保留顺序、类型天然散列) */
    private void typeLayouts(){
        int idx = 0;
        while(ids.size()!=ls.size()){
            for(int j=0; j<ls.size();j++){
                if(!ids.contains(ls.get(j).getId()) && ls.get(j).getType()==ts.get(idx)){
                    ids.add(ls.get(j).getId());
                    idx++;
                    break;
                }
                if(j==ls.size()-1){ idx++; }
            }
            if(idx>=ts.size()){ idx=0; }
        }
        ids.forEach(id->{ lds.add(mps.get(id)); });
        hashRatio("散列模型：typeLayouts");
    }

    //散列化程度
    private void hashRatio(String layouts,int windowSize){
        int dif=0; int difx=0;
        for(int i=0; i<ids.size(); i++){
            //System.out.print("["+i+"] ");
            dif = mps.get(ids.get(i)).getType();
            for(int j=i; j< Math.min(i + windowSize, ids.size()); j++){
                if(i!=j){
                    difx += (dif!=mps.get(ids.get(j)).getType()?0:1);
                }
                //System.out.print(ids.get(j)+"("+mps.get(ids.get(j)).getType()+")  ");
            }
            //System.out.println(" #散列差："+difx);
        }
        System.out.println();
        System.out.println("################ "+layouts+" ################");
        System.out.println("#编排数据："+lds.stream().map(Layout::getId).collect(Collectors.toList()));
        System.out.println("#编排类别："+lds.stream().map(Layout::getType).collect(Collectors.toList()));
        System.out.println("#散列程度：["+(ls.size()-Double.valueOf(difx))/ls.size()*100+"] % ");
        System.out.println("################ "+layouts+" ################");
        //System.out.println();
    }

    private void hashRatio(String layouts) {
        hashRatio(layouts,2);
    }

    private void initials(){
        System.out.println("#原始数据："+ls.stream().map(Layout::getId).collect(Collectors.toList()));
        System.out.println("#原始类别："+ls.stream().map(Layout::getType).collect(Collectors.toList()));
        System.out.println("#类别聚合："+ts.stream().collect(Collectors.toList()));
    }

}

class Layout{

    //数据的唯一标识
    private String  id;
    //分类标识（最好兼具权重排序）
    private Integer type;
    //扩展数据体
    private Object  exts;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }
    public void setType(Integer type) {
        this.type = type;
    }

    public Object getExts() {
        return exts;
    }
    public void setExts(Object exts) {
        this.exts = exts;
    }

    public Layout(){}
    public Layout(String id, Integer type){
        this.id = id; this.type = type;
    }

}

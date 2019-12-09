package graph;

public class Pair<V> {

    private V v1, v2;

    public Pair(V v1, V v2){
        this.v1 = v1;
        this.v2 = v2;
    }

    public V getV1(){
        return this.v1;
    }

    public V getV2(){
        return this.v2;
    }

}
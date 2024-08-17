package main.ds.linkedlist.scenarios;

public class HashedKey {
    private Integer key;
    private final Integer hashCode;

    public HashedKey (Integer key) {
        this.key = key;
        this.hashCode = generateHashCode();
    }
    public Integer getKey(){
        return key;
    }

    private int generateHashCode() {
        int hash = 17;
        hash = 31 * hash + key.hashCode();
        return hash;
    }

    public int hashCode(){
        return generateHashCode();
    }

    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null || obj.getClass() != getClass()) return false;
        HashedKey other = (HashedKey) obj;
        return key.equals(other.key);
    }
}

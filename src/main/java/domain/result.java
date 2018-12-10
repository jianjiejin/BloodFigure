package domain;

import java.util.Set;

public class result {
    private String toTable;
    private Set<String> fromTables;

    public String getToTable() {
        return toTable;
    }

    public void setToTable(String toTable) {
        this.toTable = toTable;
    }

    public Set<String> getFromTables() {
        return fromTables;
    }

    public void setFromTables(Set<String> fromTables) {
        this.fromTables = fromTables;
    }

    @Override
    public String toString() {
        return "result{" +
                "toTable='" + toTable + '\'' +
                ", fromTables=" + fromTables +
                '}';
    }
}

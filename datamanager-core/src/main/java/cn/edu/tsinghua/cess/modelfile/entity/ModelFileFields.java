package cn.edu.tsinghua.cess.modelfile.entity;

import java.util.LinkedHashMap;
import java.util.Map;

// model file fields used by model file filters, other fields(e.g. mip_era) are not included in model file query filter, therefore they are not in this class
public enum ModelFileFields {

    institute("institute"),
    model("model"),
    experiment("experiment"),
    frequency("frequency"),
    modelingRealm("modeling_realm"),
    ensembleMember("ensemble_member"),
    variableName("variable_name");

    private static Map<String, ModelFileFields> columnFieldMap = new LinkedHashMap<String, ModelFileFields>();

    static {
        for (ModelFileFields f : values()) {
            columnFieldMap.put(f.column, f);
        }
    }

    private String column;

    private ModelFileFields(String column) {
        this.column = column;
    }

    public static ModelFileFields fromColumn(String column) {
        if (!columnFieldMap.containsKey(column)) {
            throw new IllegalArgumentException("invalid column=" + column);
        }
        return columnFieldMap.get(column);
    }

    public String getField() {
        return this.toString();
    }

    public String getColumn() {
        return this.column;
    }

}

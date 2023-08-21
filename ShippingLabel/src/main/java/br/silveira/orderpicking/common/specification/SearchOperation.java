package br.silveira.orderpicking.common.specification;

public enum SearchOperation {
    LIKE, EQUAL,DIFF, GREATER_THAN, GREATER_THAN_EQUAL, LESS_THAN, LESS_THAN_EQUAL, ANY, ALL;
    public static final String[] SIMPLE_OPERATION_SET = {
            "lk", "eq", "df", "gt", "ge", "lt", "le" };

    public static SearchOperation getDataOption(final String dataOption){
        switch(dataOption){
            case "all": return ALL;
            case "any": return ANY;
            default: return null;
        }
    }

    public static SearchOperation getSimpleOperation(final String input) {
        switch (input.toLowerCase()){
            case "lk": return LIKE;
            case "eq": return EQUAL;
            case "df": return DIFF;
            case "gt": return GREATER_THAN;
            case "ge": return GREATER_THAN_EQUAL;
            case "lt": return LESS_THAN;
            case "le": return LESS_THAN_EQUAL;

            default: return null;
        }
    }
}

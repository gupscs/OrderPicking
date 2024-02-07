package br.silveira.orderpicking.outbound.entity;

public enum PackingStatus {

    PENDING("PENDING"),ONGOING("ONGOING"),COMPLETED("COMPLETED"),COMPLETED_WITH_ISSUE("COMPLETED_WITH_ISSUE");
    private final String desc;

    PackingStatus(String desc){
        this.desc = desc;
    }
    public String getDesc(){
        return desc;
    }
}

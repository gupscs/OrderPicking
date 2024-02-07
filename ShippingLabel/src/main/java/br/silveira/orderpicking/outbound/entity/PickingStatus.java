package br.silveira.orderpicking.outbound.entity;

public enum PickingStatus {

    PENDING("PENDING"),ONGOING("ONGOING"),COMPLETED("COMPLETED"),COMPLETED_WITH_ISSUE("COMPLETED_WITH_ISSUE");
    private final String desc;

    PickingStatus(String desc){
        this.desc = desc;
    }
    public String getDesc(){
        return desc;
    }
}

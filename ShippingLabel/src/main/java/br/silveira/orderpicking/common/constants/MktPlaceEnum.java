package br.silveira.orderpicking.common.constants;

public enum MktPlaceEnum {

    MERCADO_LIVRE("MERCADO_LIVRE"),SHOPEE("SHOPEE"),SHEIN("SHEIN");


    private final String desc;
    MktPlaceEnum(String desc){
        this.desc = desc;
    }
    public String getDesc(){
        return desc;
    }
}

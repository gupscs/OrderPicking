package br.silveira.orderpicking.common.templates;

import lombok.*;

import java.util.List;
@Getter
@Setter
@Builder
public class PdfShippingLabelResultTemplateDto extends CommonTemplate {



    private Long companyId;
    private Integer orderTotalQty;
    private Integer itemTotalQty;
    private Integer skuQty;
    private Integer labelsQty;
    private Double orderTotalAmt;
    private List<Summary> summary;
    private List<Detail> details;


    public PdfShippingLabelResultTemplateDto() {
        super("templates/pdf/pdf-shipping-label-result.html");
    }

    @Data
    public static class Summary{

        private String sku;
        private Integer skuTotalQty;

        public Summary( String sku, Integer skuTotalQty) {
            this.sku = sku;
            this.skuTotalQty = skuTotalQty;
        }
    }

    @Data
    public static class Detail{

        private String mktPlace;
        private String mktPlaceOrderId;
        private String sku;
        private Integer skuTotalQty;

        public Detail() {
        }

        public Detail(String mktPlace, String mktPlaceOrderId, String sku, Integer skuTotalQty) {
            this.mktPlace = mktPlace;
            this.mktPlaceOrderId = mktPlaceOrderId;
            this.sku = sku;
            this.skuTotalQty = skuTotalQty;
        }
    }


}

package com.product.vo;

public class ProductVO {
    private Integer productNo;    //商品編號
    private String prodName;      //商品名稱
    private Integer prodPrice;    //商品價格
    private String prodIntroduce; //商品介紹
    private String prodPic;       //商品圖片


    public Integer getProductNo() {
        return productNo;
    }

    public void setProductNo(Integer productNo) {
        this.productNo = productNo;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public Integer getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(Integer prodPrice) {
        this.prodPrice = prodPrice;
    }

    public String getProdIntroduce() {
        return prodIntroduce;
    }

    public void setProdIntroduce(String prodIntroduce) {
        this.prodIntroduce = prodIntroduce;
    }

    public String getProdPic() {
        return prodPic;
    }

    public void setProdPic(String prodPic) {
        this.prodPic = prodPic;
    }
}

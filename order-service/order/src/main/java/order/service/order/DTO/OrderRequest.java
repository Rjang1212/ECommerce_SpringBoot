package order.service.order.DTO;

public class OrderRequest {
    private String productId;
    private String userId;

    public OrderRequest() {
    }

    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
}

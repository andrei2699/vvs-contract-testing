package provider.contracts;

import java.util.UUID;

public class ProviderProductResponse {
    private UUID id;
    private String name;
    private String description;
    private double price;
    private String storeName;
    private int storeId;

    public ProviderProductResponse(UUID id, String name, String description, double price, String storeName, int storeId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.storeName = storeName;
        this.storeId = storeId;
    }

    public ProviderProductResponse() {
    }

    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public double getPrice() {
        return this.price;
    }

    public String getStoreName() {
        return this.storeName;
    }

    public int getStoreId() {
        return this.storeId;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ProviderProductResponse)) return false;
        final ProviderProductResponse other = (ProviderProductResponse) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$fullName = this.getName();
        final Object other$fullName = other.getName();
        if (this$fullName == null ? other$fullName != null : !this$fullName.equals(other$fullName)) return false;
        final Object this$description = this.getDescription();
        final Object other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description))
            return false;
        if (Double.compare(this.getPrice(), other.getPrice()) != 0) return false;
        final Object this$storeName = this.getStoreName();
        final Object other$storeName = other.getStoreName();
        if (this$storeName == null ? other$storeName != null : !this$storeName.equals(other$storeName)) return false;
        return this.getStoreId() == other.getStoreId();
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ProviderProductResponse;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $fullName = this.getName();
        result = result * PRIME + ($fullName == null ? 43 : $fullName.hashCode());
        final Object $description = this.getDescription();
        result = result * PRIME + ($description == null ? 43 : $description.hashCode());
        final long $price = Double.doubleToLongBits(this.getPrice());
        result = result * PRIME + (int) ($price >>> 32 ^ $price);
        final Object $storeName = this.getStoreName();
        result = result * PRIME + ($storeName == null ? 43 : $storeName.hashCode());
        result = result * PRIME + this.getStoreId();
        return result;
    }

    public String toString() {
        return "ProviderProductResponse(id=" + this.getId() + ", fullName=" + this.getName() + ", description=" + this.getDescription() + ", price=" + this.getPrice() + ", storeName=" + this.getStoreName() + ", storeId=" + this.getStoreId() + ")";
    }
}

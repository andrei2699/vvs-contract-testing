package provider.contracts;

public class CreateProduct {
    private String name;
    private String description;
    private double price;

    public CreateProduct(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public CreateProduct() {
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

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof CreateProduct)) return false;
        final CreateProduct other = (CreateProduct) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$fullName = this.getName();
        final Object other$fullName = other.getName();
        if (this$fullName == null ? other$fullName != null : !this$fullName.equals(other$fullName)) return false;
        final Object this$description = this.getDescription();
        final Object other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description))
            return false;
        return Double.compare(this.getPrice(), other.getPrice()) == 0;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof CreateProduct;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $fullName = this.getName();
        result = result * PRIME + ($fullName == null ? 43 : $fullName.hashCode());
        final Object $description = this.getDescription();
        result = result * PRIME + ($description == null ? 43 : $description.hashCode());
        final long $price = Double.doubleToLongBits(this.getPrice());
        result = result * PRIME + (int) ($price >>> 32 ^ $price);
        return result;
    }

    public String toString() {
        return "CreateProduct(fullName=" + this.getName() + ", description=" + this.getDescription() + ", price=" + this.getPrice() + ")";
    }
}

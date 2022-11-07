package provider.contracts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProviderProductResponse {
    private UUID id;
    private String name;
    private String description;
    private double price;
    private String storeName;
    private int storeId;
}

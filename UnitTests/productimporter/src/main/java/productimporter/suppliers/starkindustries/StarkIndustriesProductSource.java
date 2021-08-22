package productimporter.suppliers.starkindustries;

public interface StarkIndustriesProductSource {

  Iterable<StarkIndustriesProduct> fetchProducts();
}

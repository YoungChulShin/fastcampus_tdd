package productimporter.suppliers.starkindustries;

import java.util.ArrayList;
import productimporter.Product;
import productimporter.ProductImporter;

public class StarkIndustriesProductImporter implements ProductImporter {

  private StarkIndustriesProductSource productSource;

  public StarkIndustriesProductImporter(
      StarkIndustriesProductSource productSource,
      StarkIndustriesProductTranslator translator) {

    this.productSource = productSource;
  }

  @Override
  public Iterable<Product> fetchProducts() {
    var products = new ArrayList<Product>();
    for (StarkIndustriesProduct s : productSource.getAllProducts()) {
      products.add(null);
    }

    return products;
  }
}

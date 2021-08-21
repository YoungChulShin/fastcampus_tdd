package productimporter.suppliers.wayneenterprises;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import productimporter.Product;
import productimporter.ProductImporter;

public final class WayneEnterprisesProductImporter implements ProductImporter {

  private final WayneEnterprisesProductSource datasource;
//  private final WayneEnterprisesProductTranslator translator;

  public WayneEnterprisesProductImporter(WayneEnterprisesProductSource datasource) {
    this.datasource = datasource;
//    this.translator = new WayneEnterprisesProductTranslator();
  }

  @Override
  public Iterable<Product> fetchProducts() {
//    return StreamSupport.stream(datasource.fetchProducts().spliterator(), false)
//        .map(translator::translateProduct)
//        .collect(Collectors.toList());

    ArrayList<WayneEnterprisesProduct> products = new ArrayList<>();
    datasource.fetchProducts().forEach(products::add);
    return Arrays.asList(new Product[products.size()]);
  }
}

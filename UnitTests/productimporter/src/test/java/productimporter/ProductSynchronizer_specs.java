package productimporter;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import productimporter.suppliers.wayneenterprises.WayneEnterprisesProduct;
import productimporter.suppliers.wayneenterprises.WayneEnterprisesProductImporter;
import productimporter.suppliers.wayneenterprises.WayneEnterprisesProductSourceStub;

public class ProductSynchronizer_specs {

  @ParameterizedTest
  @DomainArgumentsSource
  void sut_correctly_saves_products(WayneEnterprisesProduct[] products) {
    var stub = new WayneEnterprisesProductSourceStub(products);
    var importer = new WayneEnterprisesProductImporter(stub);
    var validator = new ListPriceFilter(BigDecimal.ZERO);
    var spy = new ProductInventorySpy();
    var sut = new ProductSynchronizer(importer, validator, spy);

    sut.run();

    Iterable<Product> expected = importer.fetchProducts();
    Assertions.assertThat(spy.getLog()).usingRecursiveFieldByFieldElementComparator().containsAll(expected);
  }
}
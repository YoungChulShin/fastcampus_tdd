package productimporter.suppliers.wayneenterprises;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import productimporter.DomainArgumentsSource;
import productimporter.Product;

public class WayneEnterprisesProductImporte_specs {

  @ParameterizedTest
  @DomainArgumentsSource
  void sut_projects_all_products(WayneEnterprisesProduct[] source) {
    var stub = new WayneEnterprisesProductSourceStub(source);
    var sut = new WayneEnterprisesProductImporter(stub);

    Iterable<Product> actual = sut.fetchProducts();

    Assertions.assertThat(actual).hasSize(source.length);
  }

  @ParameterizedTest
  @DomainArgumentsSource
  void sut_correctly_sets_supplier_name(WayneEnterprisesProduct[] source) {
    var stub = new WayneEnterprisesProductSourceStub(source);
    var sut = new WayneEnterprisesProductImporter(stub);

    Iterable<Product> actual = sut.fetchProducts();

    Assertions.assertThat(actual).allMatch(x -> x.getSupplierName().equals("WAYNE"));
  }
}
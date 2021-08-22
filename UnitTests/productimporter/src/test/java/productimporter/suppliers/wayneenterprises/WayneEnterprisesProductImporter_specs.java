package productimporter.suppliers.wayneenterprises;

import java.util.ArrayList;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import productimporter.DomainArgumentsSource;
import productimporter.Product;

public class WayneEnterprisesProductImporter_specs {

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

  @ParameterizedTest
  @DomainArgumentsSource
  void sut_correctly_projects_source_properties(WayneEnterprisesProduct source) {
    var stub = new WayneEnterprisesProductSourceStub(source);
    var sut = new WayneEnterprisesProductImporter(stub);

    ArrayList<Product> products = new ArrayList<>();
    sut.fetchProducts().forEach(products::add);
    Product actual = products.get(0);

    Assertions.assertThat(actual.getProductCode()).isEqualTo(source.getId());
    Assertions.assertThat(actual.getProductName()).isEqualTo(source.getTitle());
    Assertions.assertThat(actual.getPricing().getListPrice())
        .isEqualByComparingTo(Integer.toString(source.getListPrice()));
    Assertions.assertThat(actual.getPricing().getDiscount())
        .isEqualByComparingTo(Integer.toString(source.getListPrice() - source.getSellingPrice()));
  }
}
package productimporter;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
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

  @ParameterizedTest
  @DomainArgumentsSource
  void sut_does_not_save_invalid_product(WayneEnterprisesProduct product) {
    // given
    var lowerBound = new BigDecimal(product.getListPrice() + 10000);
    var validator = new ListPriceFilter(lowerBound);

    var stub = new WayneEnterprisesProductSourceStub(product);
    var importer = new WayneEnterprisesProductImporter(stub);
    var spy = new ProductInventorySpy();
    var sut = new ProductSynchronizer(importer, validator, spy);

    // when
    sut.run();

    // then
    Assertions.assertThat(spy.getLog()).isEmpty();
  }

  @Test
  void sut_really_does_not_save_invalid_product() {
    // given
    var pricing = new Pricing(BigDecimal.TEN, BigDecimal.ONE);
    var product = new Product("supplierName", "productCode", "productName", pricing);

    ProductImporter importer = mock(ProductImporter.class);
    when(importer.fetchProducts()).thenReturn(Collections.singletonList(product));

    ProductValidator validator = mock(ProductValidator.class);
    when(validator.isValid(product)).thenReturn(false);

    ProductInventory inventory = mock(ProductInventory.class);

    var sut = new ProductSynchronizer(importer, validator, inventory);

    // when
    sut.run();

    // then
    verify(inventory, never()).upsertProduct(product);
  }
}
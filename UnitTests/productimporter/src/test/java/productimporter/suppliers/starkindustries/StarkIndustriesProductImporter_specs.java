package productimporter.suppliers.starkindustries;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import org.junit.jupiter.params.ParameterizedTest;
import productimporter.DomainArgumentsSource;
import productimporter.Product;

class StarkIndustriesProductImporter_specs {

  @ParameterizedTest
  @DomainArgumentsSource
  void sut_projects_all_products(StarkIndustriesProduct[] sourceProducts) {
    // arrange
    var productSource = mock(StarkIndustriesProductSource.class);
    when(productSource.getAllProducts()).thenReturn(Arrays.asList(sourceProducts));

    var translator = mock(StarkIndustriesProductTranslator.class);
    var sut = new StarkIndustriesProductImporter(productSource, translator);

    // act
    Iterable<Product> actual = sut.fetchProducts();

    // assert
    assertThat(actual).hasSize(sourceProducts.length);
  }

}
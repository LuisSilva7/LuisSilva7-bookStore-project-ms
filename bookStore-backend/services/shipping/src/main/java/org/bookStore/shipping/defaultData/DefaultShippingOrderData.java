package org.bookStore.shipping.defaultData;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.bookStore.shipping.shipping.ShippingOrder;
import org.bookStore.shipping.shipping.ShippingOrderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DefaultShippingOrderData implements CommandLineRunner {

    private final ShippingOrderRepository shippingOrderRepository;

    @Override
    @Transactional
    public void run(String... args) {

        if (shippingOrderRepository.count() == 0) {

            List<ShippingOrder> shippingOrders = List.of(
                    ShippingOrder.builder()
                            .firstName("Joana")
                            .lastName("Ribeiro")
                            .address("Rua Central 123")
                            .city("Braga")
                            .email("joana@example.com")
                            .postalCode("4700-000")
                            .build(),

                    ShippingOrder.builder()
                            .firstName("Pedro")
                            .lastName("Gomes")
                            .address("Avenida da Liberdade 45")
                            .city("Porto")
                            .email("pedro@example.com")
                            .postalCode("4000-001")
                            .build(),

                    ShippingOrder.builder()
                            .firstName("Sofia")
                            .lastName("Ferreira")
                            .address("Rua das Flores 78")
                            .city("Lisboa")
                            .email("sofia@example.com")
                            .postalCode("1000-002")
                            .build(),

                    ShippingOrder.builder()
                            .firstName("Ricardo")
                            .lastName("Mendes")
                            .address("Travessa do Parque 10")
                            .city("Coimbra")
                            .email("ricardo@example.com")
                            .postalCode("3000-003")
                            .build(),

                    ShippingOrder.builder()
                            .firstName("Ana")
                            .lastName("Martins")
                            .address("Alameda do Sol 9")
                            .city("Vila Real")
                            .email("ana@example.com")
                            .postalCode("5000-004")
                            .build()
            );

            shippingOrderRepository.saveAll(shippingOrders);

            System.out.println("Default shipping orders were created!");
        } else {
            System.out.println("Default shipping orders already exist!");
        }
    }
}

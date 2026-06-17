package com.servet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.servet.entities.Birim;;

// @Repository // yazmaya gerek yok Spring Data JPA bu interfacein bir repo olduğunu anlıtor
public interface BirimRepository extends JpaRepository<Birim, Long> { // virgülden önceki kısıma hangi sınıf üzerinde
                                                                      // işlem yapacağımızı yazıyoruz. ikinci kısımda
                                                                      // ise o sınıfın primary keyinin tipini yazıyoruz.
                                                                      // JpaRepository bize hazır olarak CRUD
                                                                      // işlemlerini yapabileceğimiz metotları sağlar.
                                                                      // İsterseniz kendi özel sorgularınızı da
                                                                      // ekleyebilirsiniz.

}

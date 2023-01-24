package com.avaliacao.fisica.avp.services;

import com.avaliacao.fisica.avp.model.ClienteModel;
import com.avaliacao.fisica.avp.utils.CreateNewCliente;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DataJpaTest
class ClienteServiceTest {

    @Test
    public void should_be_abe_to_create_cliente(){
        ClienteModel clientToBeSaved = CreateNewCliente.createValidClient();


    }





}
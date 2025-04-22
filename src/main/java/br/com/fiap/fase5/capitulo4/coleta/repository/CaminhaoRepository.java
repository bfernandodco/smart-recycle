package br.com.fiap.fase5.capitulo4.coleta.repository;

import br.com.fiap.fase5.capitulo4.coleta.model.Caminhao;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaminhaoRepository extends MongoRepository<Caminhao, String> {

    Caminhao findCaminhaoByPlaca(String placa);
    void deleteCaminhaoByPlaca(String placa);
}

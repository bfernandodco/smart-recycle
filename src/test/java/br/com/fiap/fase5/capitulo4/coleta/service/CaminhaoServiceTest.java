package br.com.fiap.fase5.capitulo4.coleta.service;

import br.com.fiap.fase5.capitulo4.coleta.dto.CaminhaoDto;
import br.com.fiap.fase5.capitulo4.coleta.model.Caminhao;
import br.com.fiap.fase5.capitulo4.coleta.repository.CaminhaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CaminhaoServiceTest {

    @Mock
    private CaminhaoRepository repository;

    @Mock
    private RotaService rotaService;

    @InjectMocks
    private CaminhaoService caminhaoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExcluir() {
        doNothing().when(repository).deleteCaminhaoByPlaca("ABC1234");

        caminhaoService.excluir("ABC1234");

        verify(repository, times(1)).deleteCaminhaoByPlaca("ABC1234");
    }

    @Test
    void testCadastrarCaminhaoJaCadastrado() {
        CaminhaoDto dto = mock(CaminhaoDto.class);
        when(repository.save(any(Caminhao.class))).thenThrow(new RuntimeException("Erro. Caminhão já cadastrado."));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> caminhaoService.cadastrar(dto));

        assertEquals("Erro. Caminhão já cadastrado.", exception.getMessage());
    }
}
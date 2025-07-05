package com.example.minierp.service;

import com.example.minierp.entity.Cliente;
import com.example.minierp.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente("João Silva", "joao@email.com", "11999999999");
        cliente.setId(1L);
    }

    @Test
    void deveSalvarClienteComSucesso() {
        // Given
        when(clienteRepository.existsByEmail("joao@email.com")).thenReturn(false);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        // When
        Cliente clienteSalvo = clienteService.salvar(cliente);

        // Then
        assertNotNull(clienteSalvo);
        assertEquals("João Silva", clienteSalvo.getNome());
        assertEquals("joao@email.com", clienteSalvo.getEmail());
        verify(clienteRepository).save(cliente);
    }

    @Test
    void deveLancarExcecaoQuandoEmailJaExiste() {
        // Given
        when(clienteRepository.existsByEmail("joao@email.com")).thenReturn(true);

        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> clienteService.salvar(cliente)
        );
        assertEquals("Email já está em uso", exception.getMessage());
        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    @Test
    void deveBuscarClientePorId() {
        // Given
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        // When
        Cliente clienteEncontrado = clienteService.buscarPorId(1L);

        // Then
        assertNotNull(clienteEncontrado);
        assertEquals(1L, clienteEncontrado.getId());
        assertEquals("João Silva", clienteEncontrado.getNome());
    }

    @Test
    void deveLancarExcecaoQuandoClienteNaoEncontrado() {
        // Given
        when(clienteRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(
            RuntimeException.class,
            () -> clienteService.buscarPorId(999L)
        );
        assertEquals("Cliente não encontrado", exception.getMessage());
    }

    @Test
    void deveListarTodosOsClientes() {
        // Given
        Cliente cliente2 = new Cliente("Maria Santos", "maria@email.com", "11888888888");
        List<Cliente> clientes = Arrays.asList(cliente, cliente2);
        when(clienteRepository.findAll()).thenReturn(clientes);

        // When
        List<Cliente> clientesEncontrados = clienteService.listarTodos();

        // Then
        assertEquals(2, clientesEncontrados.size());
        assertTrue(clientesEncontrados.contains(cliente));
        assertTrue(clientesEncontrados.contains(cliente2));
    }

    @Test
    void deveBuscarClientesPorNome() {
        // Given
        List<Cliente> clientes = Arrays.asList(cliente);
        when(clienteRepository.findByNomeContainingIgnoreCase("João")).thenReturn(clientes);

        // When
        List<Cliente> clientesEncontrados = clienteService.buscarPorNome("João");

        // Then
        assertEquals(1, clientesEncontrados.size());
        assertEquals("João Silva", clientesEncontrados.get(0).getNome());
    }

    @Test
    void deveAtualizarClienteComSucesso() {
        // Given
        Cliente clienteAtualizado = new Cliente("João Santos", "joao@email.com", "11777777777");
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteRepository.existsByEmail("joao@email.com")).thenReturn(false);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteAtualizado);

        // When
        Cliente resultado = clienteService.atualizar(1L, clienteAtualizado);

        // Then
        assertNotNull(resultado);
        assertEquals("João Santos", resultado.getNome());
        verify(clienteRepository).save(any(Cliente.class));
    }

    @Test
    void deveDeletarCliente() {
        // Given
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        // When
        clienteService.deletar(1L);

        // Then
        verify(clienteRepository).delete(cliente);
    }
}

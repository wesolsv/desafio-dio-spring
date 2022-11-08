package dio.labpadrao.service;

import dio.labpadrao.model.Cliente;

public interface ClienteService {
    Iterable<Cliente> buscarTodos();
    Cliente buscarPorId(Long id);

    Cliente inserir(Cliente cliente);
    Cliente atualizar(Long id, Cliente cliente);
    void deletar(Long id);
}

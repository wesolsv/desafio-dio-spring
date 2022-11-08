package dio.labpadrao.service.impl;

import dio.labpadrao.model.Cliente;
import dio.labpadrao.model.ClienteRepository;
import dio.labpadrao.service.ClienteService;
import dio.labpadrao.service.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public Iterable<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }
    @Override
    public Cliente buscarPorId(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.get();

        //Incluir regra de retorno
    }
    @Override
    public void inserir(Cliente cliente) {
    }
    @Override
    public void atualizar(Long id, Cliente cliente) {
    }
    @Override
    public void deletar(Long id) {
    }
}

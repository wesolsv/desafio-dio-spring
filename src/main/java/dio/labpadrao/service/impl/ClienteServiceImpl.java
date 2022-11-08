package dio.labpadrao.service.impl;

import dio.labpadrao.exceptions.ResourceBadRequestException;
import dio.labpadrao.exceptions.ResourceObjectNotFoundException;
import dio.labpadrao.model.Cliente;
import dio.labpadrao.model.ClienteRepository;
import dio.labpadrao.model.Endereco;
import dio.labpadrao.model.EnderecoRepository;
import dio.labpadrao.service.ClienteService;
import dio.labpadrao.service.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl implements ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private ViaCepService viaCepService;

    @Override
    public Iterable<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }
    @Override
    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id).orElseThrow(
                () -> new ResourceObjectNotFoundException("Cliente não encontrado com o id = " + id));
    }
    @Override
    public Cliente inserir(Cliente cliente) {
        salvarClienteComCep(cliente);
        return cliente;
    }

    @Override
    public Cliente atualizar(Long id, Cliente cliente) {
        cliente.setId(buscarPorId(id).getId());
        salvarClienteComCep(cliente);
        return cliente;
    }
    @Override
    public void deletar(Long id) {
        buscarPorId(id);
        clienteRepository.deleteById(id);
    }

    private void salvarClienteComCep(Cliente cliente) {
        String cep = cliente.getEndereco().getCep().replaceAll("\\D", "");
        Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
            Endereco novoEndereco = viaCepService.consultarCep(cep);
            enderecoRepository.save(novoEndereco);
            return novoEndereco;
        });

        if(endereco == null){
            throw new ResourceBadRequestException("Endereço não encontrado para o CEP = " + cliente.getEndereco().getCep());
        }

        cliente.setEndereco(endereco);
        clienteRepository.save(cliente);
    }
}

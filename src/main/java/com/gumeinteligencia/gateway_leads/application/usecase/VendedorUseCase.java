package com.gumeinteligencia.gateway_leads.application.usecase;

import com.gumeinteligencia.gateway_leads.application.exceptions.VendedorNaoEncontradoException;
import com.gumeinteligencia.gateway_leads.application.gateways.VendedorGateway;
import com.gumeinteligencia.gateway_leads.application.usecase.dto.RelatorioContatoDto;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.Vendedor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class VendedorUseCase {

    private final VendedorGateway gateway;
    private final ClienteUseCase clienteUseCase;
    private final Random random = new Random();


    public Vendedor escolherVendedor(Cliente cliente) {

        if(cliente.getSegmento().getCodigo() == 1) {
            return this.consultarVendedor("Nilza");
        }

        if(cliente.getSegmento().getCodigo() == 5) {
            return this.consultarVendedor("Mariana");
        }

        if(cliente.getRegiao().getCodigo() == 2) {
            return this.consultarVendedor("Samara");
        }

        String vendedor;

        if(cliente.getSegmento().getCodigo() == 3) {
            vendedor = this.roletaVendedoresNilza();
        } else {
            vendedor = this.roletaVendedores();
        }

        return this.consultarVendedor(vendedor);
    }

    private String roletaVendedores() {
        List<Vendedor> vendedores = gateway.listarSemNilza();

        int limite = vendedores.size();

        return vendedores.get(random.nextInt(limite)).getNome();

    }

    private String roletaVendedoresNilza() {
        List<Vendedor> vendedores = gateway.listar();

        int limite = vendedores.size();

        return vendedores.get(random.nextInt(limite)).getNome();
    }

    public Vendedor consultarVendedor(String nome) {
        Optional<Vendedor> vendedor = gateway.consultarVendedor(nome);

        if(vendedor.isEmpty()) {
            throw new VendedorNaoEncontradoException();
        }

        return vendedor.get();
    }

    public Vendedor cadastrar(Vendedor vendedor) {
        return gateway.salvar(vendedor);
    }

    public void deletar(Long id) {
        gateway.deletar(id);
    }

    public List<RelatorioContatoDto> getRelatorio() {
        return clienteUseCase.getRelatorio();
    }

}

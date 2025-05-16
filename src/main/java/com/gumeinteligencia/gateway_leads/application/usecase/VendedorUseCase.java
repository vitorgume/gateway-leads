package com.gumeinteligencia.gateway_leads.application.usecase;

import com.gumeinteligencia.gateway_leads.application.exceptions.VendedorNaoEncontradoException;
import com.gumeinteligencia.gateway_leads.application.gateways.VendedorGateway;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.Vendedor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class VendedorUseCase {

    private final VendedorGateway gateway;
    private final Random random = new Random();


    public Vendedor escolherVendedor(Cliente cliente) {

        if(cliente.getSegmento().getCodigo() == 1) {
            return this.consultarVendedor("Nilza");
        }

        if(cliente.getSegmento().getCodigo() == 5) {
            return this.consultarVendedor("Mariana");
        }

        if(cliente.getRegiao().getCodigo() == 2 || cliente.getRegiao().getCodigo() == 3) {
            return this.consultarVendedor("Samara");
        }

        if(cliente.getSegmento().getCodigo() == 3) {
            String vendedor = this.roletaVendedoresNilza();
            return this.consultarVendedor(vendedor);
        } else if (cliente.getSegmento().getCodigo() != 6) {
            return this.consultarVendedor("Mariana");
        } else {
            String vendedor = this.roletaVendedores();
            return this.consultarVendedor(vendedor);
        }
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
}

package com.gumeinteligencia.gateway_leads.application.usecase;

import com.gumeinteligencia.gateway_leads.application.exceptions.VendedorNaoEncontradoException;
import com.gumeinteligencia.gateway_leads.application.gateways.VendedorGateway;
import com.gumeinteligencia.gateway_leads.application.usecase.dto.RelatorioContatoDto;
import com.gumeinteligencia.gateway_leads.domain.Cliente;
import com.gumeinteligencia.gateway_leads.domain.Segmento;
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
        if (cliente.getSegmento().getCodigo() == 1) {
            return this.consultarVendedor("Nilza");
        }

        if (cliente.getRegiao().getCodigo() == 2) {
            Vendedor vendedor = consultarVendedor("Samara");

            if (vendedor.getInativo()) {
                vendedor = consultarVendedor(roletaVendedores("Nilza"));
            }

            return vendedor;
        }

        return escolheVendedorSegmento(cliente.getSegmento());
    }

    private Vendedor escolheVendedorSegmento(Segmento segmento) {
        if (segmento.getCodigo() == 5) {
            Vendedor vendedor = consultarVendedor("Mariana");

            if (vendedor.getInativo()) {
                vendedor = consultarVendedor(roletaVendedores("Nilza"));
            }

            return vendedor;
        }

        String vendedor;

        if (segmento.getCodigo() == 3) {
            vendedor = this.roletaVendedores(null);
        } else {
            vendedor = this.roletaVendedores("Nilza");
        }

        return this.consultarVendedor(vendedor);
    }

    public String roletaVendedores(String excecao) {
        List<Vendedor> vendedores;

        if (excecao == null) {
            vendedores = gateway.listar();
        } else {
            vendedores = gateway.listarComExcecao(excecao);
        }

        int limite = vendedores.size();

        Vendedor vendedor;

        do {
            vendedor = vendedores.get(random.nextInt(limite));
        } while (vendedor.getInativo());

        return vendedor.getNome();

    }

    public Vendedor consultarVendedor(String nome) {
        Optional<Vendedor> vendedor = gateway.consultarVendedor(nome);

        if (vendedor.isEmpty()) {
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

    public Optional<Vendedor> consultarPorTelefone(String telefone) {
        return gateway.consultarPorTelefone(telefone);
    }

    public List<RelatorioContatoDto> getRelatorioSegundaFeira() {
        return clienteUseCase.getRelatorioSegundaFeira();
    }

    public Vendedor roletaVendedoresConversaInativa(Cliente cliente) {
        if(cliente.getSegmento() != null) {
            return escolheVendedorSegmento(cliente.getSegmento());
        }

        return this.consultarVendedor(this.roletaVendedores("Nilza"));
    }
}

package com.gumeinteligencia.gateway_leads.application.usecase.vendedor;

import com.gumeinteligencia.gateway_leads.application.exceptions.EscolhaNaoIdentificadoException;
import com.gumeinteligencia.gateway_leads.application.exceptions.VendedorNaoEncontradoException;
import com.gumeinteligencia.gateway_leads.application.gateways.VendedorGateway;
import com.gumeinteligencia.gateway_leads.application.usecase.ClienteUseCase;
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
    private final EscolhaVendedorFactory escolhaVendedorFactory;
    private static String ultimoVendedor = null;

    public Vendedor escolherVendedor(Cliente cliente) {

        try {
            EscolhaVendedorType escolhaVendedorType = escolhaVendedorFactory.escolha(cliente.getSegmento(), cliente.getRegiao());
            EscolhaVendedor escolhaVendedor = escolhaVendedorType.escolher();

            if(escolhaVendedor.isRoleta()) {
                return consultarVendedor(this.roletaVendedores(escolhaVendedor.getVendedor()));
            } else {
                return consultarVendedor(escolhaVendedor.getVendedor());
            }

        } catch (EscolhaNaoIdentificadoException ex) {
            log.warn("Parâmetro de escolha de segmentos de vendedores inválida.");
            return null;
        }
    }

    public String roletaVendedores(String excecao) {
        List<Vendedor> vendedores;

        if (excecao == null) {
            vendedores = gateway.listar();
        } else {
            vendedores = gateway.listarComExcecao(excecao);
        }

        if (vendedores.size() <= 1) return vendedores.get(0).getNome();

        Vendedor vendedor;
        do {
            vendedor = vendedores.get(random.nextInt(vendedores.size()));
        } while (vendedor.getInativo() || vendedor.getNome().equals(ultimoVendedor));

        ultimoVendedor = vendedor.getNome();
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
            return escolherVendedor(cliente);
        }

        return this.consultarVendedor(this.roletaVendedores("Nilza"));
    }

//    private Vendedor escolheVendedorMedicina() {
//        List<Vendedor> vendedoresMedicina = gateway.listar().stream().filter(vendedor -> vendedor.getNome().equals("Marcia") || vendedor.getNome().equals("Cinthya")).toList();
//        Vendedor vendedorEscolhido;
//        do {
//            vendedorEscolhido = vendedoresMedicina.get(random.nextInt(vendedoresMedicina.size()));
//        } while (vendedorEscolhido.getInativo() || vendedorEscolhido.getNome().equals(ultimoVendedorMedicina));
//
//        ultimoVendedorMedicina = vendedorEscolhido.getNome();
//        return vendedorEscolhido;
//    }
}
